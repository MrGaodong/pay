/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.constant.Loggers;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayRequestConvertor;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayResultConvertor;
import com.youzan.pay.unified.cashier.api.impl.enums.PageOperationEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractDispatcherPayHandler;
import com.youzan.pay.unified.cashier.core.model.domain.MchBizLogInfo;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.result.PayResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.DispatcherPayErrorCode;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author twb
 * @version DispatcherPayHandler.java, v 0.1 2017-01-10 19:10
 */
@Component
public class DispatcherPayHandler extends AbstractDispatcherPayHandler<PayRequest, PayResult> {

  //性能摘要日志
  protected Logger monitorLog = Loggers.MCH_LOGGER;

  @Override
  protected void doBefore(PayRequest request) {
    LogUtils.info(log, "开始支付，request={}", request);
    super.doBefore(request);
    String payChannel = request.getPayTool();
    if (PayToolEnum.getByName(payChannel) == null) {
      throw new IllegalArgumentException("支付方式不存在");
    }
  }

  @Override
  protected PayResult execute(PayRequest request) {

    boolean success = false;
    try {
      //校验价格
      checkPrice(request);
      //查询买家的商户号
      String payerMchId = getUserNo(request.getPayerId());
      //转化PayRequest->MultiPayRequest
      MultiPayRequest multiPayRequest = PayRequestConvertor.convert(request, payerMchId);

      // TODO 缓存唤起有赞收银流水
      final String kuahaoKey = new StringBuffer("kuahao").append(multiPayRequest.getAcquireNo()).toString();
      cacheService.set(kuahaoKey, multiPayRequest, MultiPayRequest.class);
      LogUtils.info(log, "支付过程中，缓存有赞收银流水, key={}, value={}", kuahaoKey, multiPayRequest);

      //调用收单服务进行预支付
      Response<MultiPayResult> response = unifiedPayServiceClient.multiPay(multiPayRequest);
      success=response.isSuccess();
      //构造返回结果
      PayResult payResult = buildResult(response, request);

      LogUtils.info(log, "支付结束，result={}", payResult);

      return payResult;
    } finally {
      //支付监控日志
      this.logMonitorPay(request, success);
    }
  }


  private void checkPrice(PayRequest request) {
    // 是否允许调整价格
    // 同意改价后 acceptPrice 为1，且newPrice大于等于0
    if (request.getAcceptPrice() == 1) {
      long newPrice = request.getNewPrice();
      if (newPrice < 0) {
        LogUtils.error(log, "新价格异常，request={}", request);
        throw new BaseException(DispatcherPayErrorCode.ACQUIRE_ORDER_ERROR);
      }
      LogUtils.warn(log, "同意改价，重新获得金额，payAmount={}", request.getNewPrice());
      request.setPayAmount(request.getNewPrice());
    }
  }

  //构造支付结果：成功和失败返回不同结果码
  private PayResult buildResult(Response<MultiPayResult> response,
                                PayRequest request) {
    if (response.isSuccess()) { // 支付成功结果
      return buildSuccessResult(response, request);
    } else { // 支付失败结果
      return buildFailResult(response, request);
    }
  }

  //TODO  返回结果优化下
  private PayResult buildSuccessResult(Response<MultiPayResult> response,
                                       PayRequest request) {

    PayToolEnum payToolEnum = PayToolEnum.getByName(request.getPayTool());
    //有deepLink的支付方式的结果处理。
    if (payToolEnum != null && payToolEnum
        .isIn(PayToolEnum.WX_JS, PayToolEnum.ALIPAY_WAP, PayToolEnum.BAIFUBAO_WAP,
              PayToolEnum.WX_H5)) {
      return genResult(PageOperationEnum.CALL_CASHIER, response);
    }
    //其他支付方式返回空的结果。
    return new PayResult();
  }

  //支付失败的场景
  private PayResult buildFailResult(Response<MultiPayResult> response,
                                    PayRequest request) {
    // "112202003" 支付系统中的错误码：支付金额与收单金额不符
    if (ADIUST_PRICE_CODE.equals(response.getResultCode())) {
      long payAmount = unifiedPayServiceClient.getPayAmount(request.getAcquireNo());
      LogUtils.info(log, "重新获得收单金额，payAmount={}", payAmount);
      if (payAmount < 0) {
        LogUtils.error(log, "收单金额异常，request={}，response={}", request, response);
        throw new BaseException(DispatcherPayErrorCode.ACQUIRE_ORDER_ERROR);
      }
      return new PayResult(PageOperationEnum.ADJUST_PRICE.name(), payAmount);
    } else if (REPEATE_PAY_CODE.equals(response.getResultCode())) {
      return new PayResult(PageOperationEnum.REPEATE_PAY.name());
    } else {
      LogUtils.error(log, "调用支付接口失败，request={}，response={}", request, response);
      throw new BaseException(DispatcherPayErrorCode.SYSTEM_ERROR);
    }
  }

  private PayResult genResult(PageOperationEnum operation, Response<MultiPayResult> response) {
    MultiPayResult multiPayResult = response.getResult();
    PayResult payResult = PayResultConvertor.convert(multiPayResult);
    payResult.setOperation(operation.name());
    return payResult;
  }

  //记录支付监控日志
  private void logMonitorPay(PayRequest request, boolean success) {
    MchBizLogInfo bizLogInfo = new MchBizLogInfo();
    bizLogInfo.setApi(this.getClass().getSimpleName());
    bizLogInfo.setPartnerId(request.getPartnerId());
    bizLogInfo.setMchId(request.getMchId());
    bizLogInfo.setPayMethod(request.getPayTool());
    bizLogInfo.setResult(success ? bizLogInfo.SUCCESS_RESULT : bizLogInfo.FAIL_RESULT);
    monitorLog.info("{}", JSON.toJSONString(bizLogInfo));

  }
}
