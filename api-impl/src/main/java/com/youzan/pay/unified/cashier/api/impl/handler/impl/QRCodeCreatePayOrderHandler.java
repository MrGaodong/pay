/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.enums.CashierExtEnums;
import com.youzan.pay.unified.cashier.api.impl.constant.Constant;
import com.youzan.pay.unified.cashier.api.impl.enums.QRCodePayResultEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version QRCodeCreateOrderHandler.java, v 0.1 2017-06-20 09:58
 */
@Slf4j
@Component
public class QRCodeCreatePayOrderHandler extends AbstractHandler<QRCodeCreateOrderRequest, QRCodeCreateOrderResult> {

  @Resource
  private UnifiedOrderServiceClient unifiedOrderServiceClient;

  @Override
  protected QRCodeCreateOrderResult execute(QRCodeCreateOrderRequest request) throws Exception {

    LogUtils.info(log, "二维码支付,创建收单开始,request={}", request);

    // 校验过期时间
    if (!checkValidity(request)) {
      return buildResult(QRCodePayResultEnum.INVALID.getCode(), null);
    }

    // ======================收单======================
    // 1.构造收单请求体
    UnifiedOrderCreatingRequest unifiedOrderCreatingRequest =
        contractUnifiedOrderCreatingRequest(request);
    // 2.
    Response<UnifiedOrderCreatingResult> responseOfCreate = unifiedOrderServiceClient
        .create(unifiedOrderCreatingRequest);
    // 3.
    UnifiedOrderCreatingResult unifiedOrderCreatingResult = responseOfCreate.getResult();

    if (!checkStatusAndUpdateStatus(unifiedOrderCreatingResult)) {
      return buildResult(QRCodePayResultEnum.USED.getCode(), unifiedOrderCreatingResult.getAcquireNo());
    }

    QRCodeCreateOrderResult result = buildResult(QRCodePayResultEnum.CAN_PAY.getCode(), unifiedOrderCreatingResult.getAcquireNo());

    LogUtils.info(log, "二维码支付,创建收单结束,result={}", result);

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

  private boolean checkStatusAndUpdateStatus(UnifiedOrderCreatingResult unifiedOrderCreatingResult) {
    AcquireQueryStatus status = unifiedOrderCreatingResult.getStatus();
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

  private UnifiedOrderCreatingRequest contractUnifiedOrderCreatingRequest(
      QRCodeCreateOrderRequest request) {
    UnifiedOrderCreatingRequest unifiedOrderCreatingRequest = new UnifiedOrderCreatingRequest();

    unifiedOrderCreatingRequest.setOutBizNo(request.getOutBizNo());
    unifiedOrderCreatingRequest.setPartnerId(request.getPartnerId());
    unifiedOrderCreatingRequest.setMchId(request.getMchId());
    unifiedOrderCreatingRequest.setPayAmount(request.getPayAmount());
    unifiedOrderCreatingRequest.setCurrencyCode(request.getCurrencyCode());
    unifiedOrderCreatingRequest.setBizProd(request.getBizProd());
    unifiedOrderCreatingRequest.setBizMode(request.getBizMode());
    unifiedOrderCreatingRequest.setBizAction(request.getBizAction());
    unifiedOrderCreatingRequest.setTradeDesc(request.getTradeDesc());

    unifiedOrderCreatingRequest.setPayerId(request.getPayerId());
//    unifiedOrderCreatingRequest.setPayerNickName(request.getPayerNickName());

    // log
    unifiedOrderCreatingRequest.setRequestId(TracerContextUtils.getTracerId());

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

    unifiedOrderCreatingRequest.setExtendInfo(extInfoMap);

    return unifiedOrderCreatingRequest;
  }
}
