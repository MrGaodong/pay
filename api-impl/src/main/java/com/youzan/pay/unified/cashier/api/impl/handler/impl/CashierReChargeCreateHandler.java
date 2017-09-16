/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;
import com.youzan.pay.assetcenter.api.request.RechargeOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.BaseResponse;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargeOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.unified.cashier.api.constants.RechargeConstants;
import com.youzan.pay.unified.cashier.api.constants.UnifiedConstants;
import com.youzan.pay.unified.cashier.api.enums.CashierExtEnums;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeCreateRequest;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeCreateResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.CashierRechargeServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**充值具体实现类
 * @author wulonghui
 * @version CashierReChargeCreateHandler.java, v 0.1 2017-06-06 11:40
 */
@Slf4j
@Component
public class CashierReChargeCreateHandler extends AbstractHandler<CashierRechargeCreateRequest,CashierReChargeCreateResult> {

  @Resource
  private CashierRechargeServiceClient cashierRechargeServiceClient;

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  @Override
  protected CashierReChargeCreateResult execute(CashierRechargeCreateRequest request) throws Exception {
    LogUtils.info(log, "[前端传递请求数据request]:{}", request);

    checkSign(request);

    Response<RechargeOrderCreatingResult>
        rechargeOrderCreatingResultResponse=createAcquireOrder(request);

    CashierReChargeCreateResult cashierReChargeCreateResult = buildResult(rechargeOrderCreatingResultResponse);

    return cashierReChargeCreateResult;

  }

  private CashierReChargeCreateResult buildResult(Response<RechargeOrderCreatingResult> rechargeOrderCreatingResultBaseResponse) {

    CashierReChargeCreateResult cashierReChargeCreateResult=new CashierReChargeCreateResult();

    /**
     *
     * 请求第三方服务返回结果业务逻辑处理
     */
    if (!rechargeOrderCreatingResultBaseResponse.isSuccess()) {
      LogUtils.error(log, "［收单服务处理失败msg]:{},[resultCode]:{}",
                     rechargeOrderCreatingResultBaseResponse.getMsg(),
                     rechargeOrderCreatingResultBaseResponse.getResultCode());
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);

    }

    /**
     * 返回结果判断为空,创建收单失败
     */
    if (rechargeOrderCreatingResultBaseResponse.getResult()== null ||
        rechargeOrderCreatingResultBaseResponse.getResult().getAcquireNo() == null) {
      LogUtils.error(log, "［收单返回结果为空,result]:{}", rechargeOrderCreatingResultBaseResponse.getResult());
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);
    }

    switch (rechargeOrderCreatingResultBaseResponse.getResult().getStatus().getCode()) {

      case UnifiedConstants.BUYER_PAIED: {
        LogUtils
            .warn(log, "［此订单支付成功收单号]：{}", rechargeOrderCreatingResultBaseResponse.getResult().getAcquireNo());
        throw new BaseException(CreateOrderErrorCode.PAYFINISH);
      }

      case UnifiedConstants.PAID_TO_SELLER: {
        LogUtils.warn(log, "［此订单成功付款到卖家］，收单号：{}",
                      rechargeOrderCreatingResultBaseResponse.getResult().getAcquireNo());
        throw new BaseException(CreateOrderErrorCode.PAYFINISH);
      }
    }

    cashierReChargeCreateResult.setUnifiedAcquireOrder(rechargeOrderCreatingResultBaseResponse.getResult().getAcquireNo());

    LogUtils.info(log, "result:{}", cashierReChargeCreateResult);

    return cashierReChargeCreateResult;
  }


  private Response<RechargeOrderCreatingResult> createAcquireOrder(CashierRechargeCreateRequest request) {

    RechargeOrderCreatingRequest rechargeOrderCreatingRequest = convertToRequest(request);

    Response<RechargeOrderCreatingResult> unifiedOrderCreatingResult = new Response<>();

    try {
      unifiedOrderCreatingResult = cashierRechargeServiceClient.create(rechargeOrderCreatingRequest);
    } catch (Exception e) {
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);
    }

    return unifiedOrderCreatingResult;
  }

  private RechargeOrderCreatingRequest convertToRequest(CashierRechargeCreateRequest request) {

    RechargeOrderCreatingRequest rechargeOrderCreatingRequest=new RechargeOrderCreatingRequest();
    rechargeOrderCreatingRequest.setMchId(String.valueOf(request.getMchId()));
    rechargeOrderCreatingRequest.setOutBizNo(request.getOutBizNo());
    rechargeOrderCreatingRequest.setPartnerId(request.getPartnerId());
    rechargeOrderCreatingRequest.setBizAction(request.getBizAction());
    rechargeOrderCreatingRequest.setBizMode(request.getBizMode());
    rechargeOrderCreatingRequest.setBizProd(request.getBizProd());
    rechargeOrderCreatingRequest.setCurrencyCode(request.getCurrencyCode());
    rechargeOrderCreatingRequest.setPayAmount(request.getPayAmount());
    rechargeOrderCreatingRequest.setPayerId(request.getPayerId());
    rechargeOrderCreatingRequest.setPayerNickName(request.getPayerNickName());
    rechargeOrderCreatingRequest.setTradeDesc(request.getTradeDesc());
    rechargeOrderCreatingRequest.setCardNo(request.getCardNo());
    rechargeOrderCreatingRequest.setCustomerId(request.getCustomerId());
    rechargeOrderCreatingRequest.setRechargePayType(request.getRechargePayType());

    Map<String, String> extInfoMap = new HashMap<>();

    if (request.getDiscountableAmount() != 0) {
      extInfoMap.put(CashierExtEnums.DCTAMOUNT.getCode(),
                     String.valueOf(request.getDiscountableAmount()));
    }

    if (request.getUndiscountableAmount() != 0) {
      extInfoMap.put(CashierExtEnums.UDTAMOUNT.getCode(),
                     String.valueOf(request.getUndiscountableAmount()));
    }

    if (request.getPartnerNotifyUrl() != null) {
      extInfoMap.put(CashierExtEnums.NOTIFYURL.getCode(), request.getPartnerNotifyUrl());
    }

    if (request.getPartnerReturnUrl() != null) {
      extInfoMap.put(CashierExtEnums.RETURNURL.getCode(), request.getPartnerReturnUrl());
    }

    rechargeOrderCreatingRequest.setExtendInfo(extInfoMap);

    return rechargeOrderCreatingRequest;
  }

  private void checkSign(CashierRechargeCreateRequest request) {

    String key = null;

    QuerySignKeyRequest querySignKeyRequest = new QuerySignKeyRequest();

    querySignKeyRequest.setPartnerId(Long.valueOf(request.getPartnerId()));

    querySignKeyRequest.setUserNo(request.getMchId());

    try {
      //todo 充值平台key
      key = RechargeConstants.KEY;
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
