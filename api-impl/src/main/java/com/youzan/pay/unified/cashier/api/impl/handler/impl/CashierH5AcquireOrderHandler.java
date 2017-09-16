/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.unified.cashier.api.constants.UnifiedConstants;
import com.youzan.pay.unified.cashier.api.enums.CashierExtEnums;
import com.youzan.pay.unified.cashier.api.impl.constant.Loggers;
import com.youzan.pay.unified.cashier.api.impl.convertor.UnifiedCashierAcquireConvertor;
import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.result.CashierH5AcquireOrderResult;
import com.youzan.pay.unified.cashier.api.result.UnifiedAcquireOrderResult;
import com.youzan.pay.unified.cashier.core.model.domain.MchBizLogInfo;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierH5AcquireOrderHandler.java, v 0.1 2017-05-16 14:18
 */
@Component
public class CashierH5AcquireOrderHandler extends
                                          AbstractHandler<CashierH5AcquireOrderRequest, CashierH5AcquireOrderResult> {

  //性能摘要日志
  protected Logger monitorLog = Loggers.MCH_LOGGER;

  @Resource
  private UnifiedOrderServiceClient unifiedOrderServiceClient;

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  @Override
  protected CashierH5AcquireOrderResult execute(CashierH5AcquireOrderRequest request)
      throws Exception {

    LogUtils.info(log, "[前端传递请求数据request]:{}", request);

    checkSign(request);

    Response<UnifiedOrderCreatingResult> unifiedOrderCreatingResult=createAcquireOrder(request);


    CashierH5AcquireOrderResult unifiedAcquireOrderResult = buildResult(unifiedOrderCreatingResult);


    return unifiedAcquireOrderResult;
  }

  private CashierH5AcquireOrderResult buildResult(
      Response<UnifiedOrderCreatingResult> unifiedOrderCreatingResult) {

    CashierH5AcquireOrderResult cashierH5AcquireOrderResult = new CashierH5AcquireOrderResult();

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

    cashierH5AcquireOrderResult
        .setUnifiedAcquireOrder(unifiedOrderCreatingResult.getResult().getAcquireNo());

    LogUtils.info(log, "result:{}", cashierH5AcquireOrderResult);

    return cashierH5AcquireOrderResult;
  }

  private Response<UnifiedOrderCreatingResult> createAcquireOrder(
      CashierH5AcquireOrderRequest request) {

    UnifiedOrderCreatingRequest createOrderReq = convertToRequest(request);

    Response<UnifiedOrderCreatingResult>
        unifiedOrderCreatingResult =
        new Response<UnifiedOrderCreatingResult>();

    try {
      unifiedOrderCreatingResult = unifiedOrderServiceClient.create(createOrderReq);
    } catch (Exception e) {
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);
    }finally {
      logMonitorAcquireOrder(createOrderReq,unifiedOrderCreatingResult);
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
      bizLogInfo.setMchId(createOrder.getMchId()+"");
      bizLogInfo.setPayMethod("");

      String result = resp.isSuccess() ? bizLogInfo.SUCCESS_RESULT : bizLogInfo.FAIL_RESULT;
      if (resp.isSuccess() && resp.getResult().isRepeated()) {
        result = bizLogInfo.IDEMPOTENT_RESULT;
      }
      bizLogInfo.setResult(result);

      monitorLog.info("{}", JSON.toJSONString(bizLogInfo));
    }
  }
  private UnifiedOrderCreatingRequest convertToRequest(CashierH5AcquireOrderRequest request) {

    UnifiedOrderCreatingRequest unifiedOrderCreatingRequest = new UnifiedOrderCreatingRequest();
    unifiedOrderCreatingRequest.setMchId(String.valueOf(request.getMchId()));
    unifiedOrderCreatingRequest.setOutBizNo(request.getOutBizNo());
    unifiedOrderCreatingRequest.setPartnerId(request.getPartnerId());
    unifiedOrderCreatingRequest.setPayAmount(request.getPayAmount());
    unifiedOrderCreatingRequest.setPayerId(request.getPayerId());
    unifiedOrderCreatingRequest.setPayerNickName(request.getPayerNickName());
    unifiedOrderCreatingRequest.setTradeDesc(request.getTradeDesc());

    unifiedOrderCreatingRequest.setBizAction(String.valueOf(getDefaultValue(request,"bizAction")));
    unifiedOrderCreatingRequest.setBizMode((int)getDefaultValue(request,"bizMode"));
    unifiedOrderCreatingRequest.setBizProd((int)getDefaultValue(request,"bizProd"));
    unifiedOrderCreatingRequest.setCurrencyCode(String.valueOf(getDefaultValue(request,"currencyCode")));

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

  private Object getDefaultValue(CashierH5AcquireOrderRequest request, String modeStr) {
    switch (modeStr) {
      case "bizMode":
        return request.getBizMode() != null ? request.getBizMode() : 2;
      case "bizAction":
        return request.getBizAction() != null ? request.getBizAction() : "PAY";
      case "bizProd":
        return request.getBizProd() != null ? request.getBizProd() : 1;
      case "currencyCode":
        return request.getCurrencyCode() != null ? request.getCurrencyCode() : "CNY";
    }
    return "defaultStr";
  }


  private void checkSign(CashierH5AcquireOrderRequest request) {

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
      if (!SignCheckUtils.checkForCashier(request, request.getSign(), key, null)) {
        throw new BaseException(CreateOrderErrorCode.SIGNERROR);
      }
    } catch (NoSuchMethodException e) {
      throw new BaseException(CreateOrderErrorCode.SYSTEM_ERROR);
    } catch (IllegalAccessException e) {
      throw new BaseException(CreateOrderErrorCode.SYSTEM_ERROR);
    } catch (InvocationTargetException e) {
      throw new BaseException(CreateOrderErrorCode.SYSTEM_ERROR);
    }

  }
}
