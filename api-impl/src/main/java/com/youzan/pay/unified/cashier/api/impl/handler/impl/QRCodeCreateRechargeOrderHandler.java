/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.request.RechargeOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargeOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.enums.CashierExtEnums;
import com.youzan.pay.unified.cashier.api.impl.constant.Constant;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.intergration.client.CashierRechargeServiceClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version QRCodeCreateRechargeOrderHandler.java, v 0.1 2017-06-20 16:55
 */
@Slf4j
@Component
public class QRCodeCreateRechargeOrderHandler extends AbstractHandler<QRCodeCreateOrderRequest,
    QRCodeCreateOrderResult> {

  @Resource
  private CashierRechargeServiceClient cashierRechargeServiceClient;

  @Override
  protected QRCodeCreateOrderResult execute(QRCodeCreateOrderRequest request) throws Exception {

    LogUtils.info(log, "二维码充值,创建收单开始,request={}", request);

    // 校验过期时间
    if (!checkValidity(request)) {
      return buildResult(0, null);
    }

    // ======================收单======================
    // 1.构造收单请求体
    RechargeOrderCreatingRequest rechargeOrderCreatingRequest =
        contractRechargeOrderCreatingRequest(request);
    // 2.
    Response<RechargeOrderCreatingResult> responseOfCreate = cashierRechargeServiceClient
        .create(rechargeOrderCreatingRequest);
    // 3.
    RechargeOrderCreatingResult rechargeOrderCreatingResult = responseOfCreate.getResult();

    if (!checkStatusAndUpdateStatus(rechargeOrderCreatingResult)) {
      return buildResult(0, rechargeOrderCreatingResult.getAcquireNo());
    }

    QRCodeCreateOrderResult result = buildResult(1, rechargeOrderCreatingResult.getAcquireNo());

    LogUtils.info(log, "二维码充值,创建收单结束,result={}", result);

    return result;
  }

  // 构造结果
  private QRCodeCreateOrderResult buildResult(int status, String acquireNo) {
    QRCodeCreateOrderResult result = new QRCodeCreateOrderResult();
    result.setStatus(status);
    result.setAcquireNo(acquireNo);
    return result;
  }

  private boolean checkValidity(QRCodeCreateOrderRequest request) {
    long now = System.currentTimeMillis();
    long expireTime = Long.valueOf(request.getCreateTime()) + Constant.TIME_TO_LIVE;
    // 检查是否失效
    if (expireTime < now) {
      // 二维码已经失效
      LogUtils.info(log, "二维码已经过期,expireTime={},now={}", expireTime, now);
      return false;
    }
    return true;
  }

  private RechargeOrderCreatingRequest contractRechargeOrderCreatingRequest(
      QRCodeCreateOrderRequest request) {
    RechargeOrderCreatingRequest rechargeOrderCreatingRequest = new RechargeOrderCreatingRequest();

    rechargeOrderCreatingRequest.setOutBizNo(request.getOutBizNo());
    rechargeOrderCreatingRequest.setPartnerId(request.getPartnerId());
    rechargeOrderCreatingRequest.setMchId(request.getMchId());
    rechargeOrderCreatingRequest.setPayAmount(request.getPayAmount());
    rechargeOrderCreatingRequest.setCurrencyCode(request.getCurrencyCode());
    rechargeOrderCreatingRequest.setBizProd(request.getBizProd());
    rechargeOrderCreatingRequest.setBizMode(request.getBizMode());
    rechargeOrderCreatingRequest.setBizAction(request.getBizAction());
    rechargeOrderCreatingRequest.setTradeDesc(request.getTradeDesc());

    rechargeOrderCreatingRequest.setPayerId(request.getPayerId());
//    unifiedOrderCreatingRequest.setPayerNickName(request.getPayerNickName());

    // log
    rechargeOrderCreatingRequest.setRequestId(TracerContextUtils.getTracerId());

    rechargeOrderCreatingRequest.setRechargePayType(request.getRechargePayType());
    rechargeOrderCreatingRequest.setCardNo(request.getCardNo());
    rechargeOrderCreatingRequest.setCustomerId(request.getCustomerId());

    Map<String, String> extInfoMap = new HashMap<>();

//    if (request.getDiscountableAmount() != 0) {
//      extInfoMap.put(CashierExtEnums.DCTAMOUNT.getCode(),
//                     String.valueOf(request.getDiscountableAmount()));
//    }

//    if (request.getUndiscountableAmount() != 0) {
//      extInfoMap.put(CashierExtEnums.UDTAMOUNT.getCode(),
//                     String.valueOf(request.getUndiscountableAmount()));
//    }

//    if (request.getPartnerNotifyUrl() != null) {
//      extInfoMap.put(CashierExtEnums.NOTIFYURL.getCode(), request.getPartnerNotifyUrl());
//    }

    if (request.getPartnerReturnUrl() != null) {
      extInfoMap.put(CashierExtEnums.RETURNURL.getCode(), request.getPartnerReturnUrl());
    }

    rechargeOrderCreatingRequest.setExtendInfo(extInfoMap);

    return rechargeOrderCreatingRequest;
  }

  private boolean checkStatusAndUpdateStatus(RechargeOrderCreatingResult rechargeOrderCreatingResult) {
    AcquireQueryStatus status = rechargeOrderCreatingResult.getStatus();
    switch (status) {
      // 以下状态，不可以再支付。
      case BUYER_PAIED:
      case FAIL:
      case SUCCESS:
        LogUtils.warn(log, "收单状态不符合支付条件");
        return false;
      default:
        return true;
    }
  }
}