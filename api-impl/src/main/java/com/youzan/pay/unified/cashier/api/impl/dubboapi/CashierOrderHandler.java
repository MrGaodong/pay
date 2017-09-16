package com.youzan.pay.unified.cashier.api.impl.dubboapi;

import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.unified.cashier.api.constants.UnifiedConstants;
import com.youzan.pay.unified.cashier.api.enums.CashierExtEnums;
import com.youzan.pay.unified.cashier.api.impl.constant.Loggers;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierOrderRequest;
import com.youzan.pay.unified.cashier.api.result.CashierOrderResult;
import com.youzan.pay.unified.cashier.core.model.domain.MchBizLogInfo;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;
import com.youzan.pay.unified.cashier.service.sign.SignService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-09-03
 * Time: 下午1:52
 * <p>创建收银台链接，请求收单处理类</p>
 */
@Component
public class CashierOrderHandler extends AbstractHandler<CashierOrderRequest,CashierOrderResult> {

  //性能摘要日志
  protected Logger monitorLog = Loggers.MCH_LOGGER;

  @Resource
  private UnifiedOrderServiceClient unifiedOrderServiceClient;

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  @Override
  protected CashierOrderResult execute(CashierOrderRequest request) throws Exception {
    LogUtils.info(log, "[前端传递请求数据request]:{}", request);

    checkSign(request);

    Response<UnifiedOrderCreatingResult> unifiedOrderCreatingResult = createAcquireOrder(request);

    CashierOrderResult cashierOrderResult = buildResult(unifiedOrderCreatingResult);

    return cashierOrderResult;
  }

  private CashierOrderResult buildResult(
      Response<UnifiedOrderCreatingResult> unifiedOrderCreatingResult) {

    CashierOrderResult cashierOrderResult = new CashierOrderResult();

    /**
     *
     * 请求第三方服务返回结果业务逻辑处理
     */
    if (!unifiedOrderCreatingResult.isSuccess()) {
      LogUtils.error(log, "［收单服务处理失败msg]:{},[resultCode]:{}",
                     unifiedOrderCreatingResult.getMsg(),
                     unifiedOrderCreatingResult.getResultCode());
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);

    }

    /**
     * 返回结果判断为空,创建收单失败
     */
    if (unifiedOrderCreatingResult.getResult() == null ||
        unifiedOrderCreatingResult.getResult().getStatus() == null
        || unifiedOrderCreatingResult.getResult().getAcquireNo() == null) {
      LogUtils.error(log, "［收单返回结果为空,result]:{}", unifiedOrderCreatingResult.getResult());
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);
    }

    switch (unifiedOrderCreatingResult.getResult().getStatus().getCode()) {

      case UnifiedConstants.BUYER_PAIED: {
        LogUtils
            .warn(log, "［此订单支付成功收单号]：{}", unifiedOrderCreatingResult.getResult().getAcquireNo());
        throw new BaseException(CreateOrderErrorCode.PAYFINISH);
      }

      case UnifiedConstants.PAID_TO_SELLER: {
        LogUtils.warn(log, "［此订单成功付款到卖家］，收单号：{}",
                      unifiedOrderCreatingResult.getResult().getAcquireNo());
        throw new BaseException(CreateOrderErrorCode.PAYFINISH);
      }
    }

    cashierOrderResult
        .setUnifiedAcquireOrder(unifiedOrderCreatingResult.getResult().getAcquireNo());

    LogUtils.info(log, "result:{}", cashierOrderResult);

    return cashierOrderResult;
  }

  private Response<UnifiedOrderCreatingResult> createAcquireOrder(
      CashierOrderRequest request) {

    UnifiedOrderCreatingRequest createOrderReq = convertToRequest(request);

    Response<UnifiedOrderCreatingResult>
        unifiedOrderCreatingResult =
        new Response<UnifiedOrderCreatingResult>();

    try {
      unifiedOrderCreatingResult = unifiedOrderServiceClient.create(createOrderReq);
    } catch (Exception e) {
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);
    } finally {
      logMonitorAcquireOrder(createOrderReq, unifiedOrderCreatingResult);
    }

    return unifiedOrderCreatingResult;
  }

  //记录创建收单监控日志
  private void logMonitorAcquireOrder(UnifiedOrderCreatingRequest createOrder,
                                      Response<UnifiedOrderCreatingResult> resp) {
    if (resp != null && resp.getResult() != null) {
      MchBizLogInfo bizLogInfo = new MchBizLogInfo();
      bizLogInfo.setApi("CreateNewAcquireOrder");
      bizLogInfo.setPartnerId(createOrder.getPartnerId());
      bizLogInfo.setMchId(createOrder.getMchId() + "");
      bizLogInfo.setPayMethod("");

      String result = resp.isSuccess() ? bizLogInfo.SUCCESS_RESULT : bizLogInfo.FAIL_RESULT;
      if (resp.isSuccess() && resp.getResult().isRepeated()) {
        result = bizLogInfo.IDEMPOTENT_RESULT;
      }
      bizLogInfo.setResult(result);

      monitorLog.info("{}", JSON.toJSONString(bizLogInfo));
    }
  }

  private UnifiedOrderCreatingRequest convertToRequest(CashierOrderRequest request) {

    UnifiedOrderCreatingRequest unifiedOrderCreatingRequest = new UnifiedOrderCreatingRequest();
    unifiedOrderCreatingRequest.setMchId(String.valueOf(request.getMchId()));
    unifiedOrderCreatingRequest.setOutBizNo(request.getOutBizNo());
    unifiedOrderCreatingRequest.setPartnerId(request.getPartnerId());
    unifiedOrderCreatingRequest.setPayAmount(request.getPayAmount());
    unifiedOrderCreatingRequest.setPayerId(request.getPayerId());
    unifiedOrderCreatingRequest.setPayerNickName(request.getPayerNickName());
    unifiedOrderCreatingRequest.setTradeDesc(request.getTradeDesc());

    unifiedOrderCreatingRequest.setBizAction(request.getBizAction());
    unifiedOrderCreatingRequest.setBizMode(request.getBizMode());
    unifiedOrderCreatingRequest.setBizProd(request.getBizProd());
    unifiedOrderCreatingRequest.setCurrencyCode(request.getCurrencyCode());

    Map<String, String> extInfoMap = new HashMap<>();

    if (request.getDiscountableAmount() != null) {
      extInfoMap.put(CashierExtEnums.DCTAMOUNT.getCode(),
                     String.valueOf(request.getDiscountableAmount()));
    }

    if (request.getUndiscountableAmount() != null) {
      extInfoMap.put(CashierExtEnums.UDTAMOUNT.getCode(),
                     String.valueOf(request.getUndiscountableAmount()));
    }

    if (request.getPartnerNotifyUrl() != null) {
      extInfoMap.put(CashierExtEnums.NOTIFYURL.getCode(), request.getPartnerNotifyUrl());
    }

    if (request.getPartnerReturnUrl() != null) {
      extInfoMap.put(CashierExtEnums.RETURNURL.getCode(), request.getPartnerReturnUrl());
    }

    unifiedOrderCreatingRequest.setExtendInfo(extInfoMap);

    return unifiedOrderCreatingRequest;
  }

  private void checkSign(CashierOrderRequest request) {

    String key = null;

    QuerySignKeyRequest querySignKeyRequest = new QuerySignKeyRequest();

    querySignKeyRequest.setPartnerId(Long.valueOf(request.getPartnerId()));

    querySignKeyRequest.setUserNo(request.getMchId());

    try {
      key = signKeyServiceClient.getSignKey(querySignKeyRequest);
      LogUtils.info(log, "［商户平台获得key]：{}", key);
    } catch (Exception e) {
      throw new BaseException(CreateOrderErrorCode.KEYFAIL);
    }

    try {
      if (!SignService.buildSign(request, key, null).equals(request.getSign())) {
        throw new BaseException(CreateOrderErrorCode.SIGNERROR);
      }
    } catch (Exception e) {
      LogUtils.error(e, log, "[创建收单签名不通过,request={}]", request);
    }
  }
}
