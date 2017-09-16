/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.convertor;

import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.assetcenter.service.spi.constants.ExtraKeyConstants;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.enums.CashierExtEnums;
import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrder;
import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.result.UnifiedAcquireOrderResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建请求转化类
 *
 * @author wulonghui
 * @version UnifiedCashierAcquireConvertor.java, v 0.1 2017-01-10 17:40
 */
public class UnifiedCashierAcquireConvertor {

  public static UnifiedOrderCreatingRequest convertToRequest(UnifiedAcquireOrderRequest request) {

    UnifiedOrderCreatingRequest unifiedOrderCreatingRequest = new UnifiedOrderCreatingRequest();

    Map<String, String> extInfoMap = new HashMap<>();

    if (request.getPayAmount() != 0) {
      unifiedOrderCreatingRequest.setPayAmount(request.getPayAmount());
    }

    unifiedOrderCreatingRequest.setBizAction(request.getBizAction());

    unifiedOrderCreatingRequest.setBizMode(request.getBizMode());

    unifiedOrderCreatingRequest.setBizProd(request.getBizProd());

    unifiedOrderCreatingRequest.setRequestId(TracerContextUtils.getTracerId());

    if (request.getCurrencyCodel() != null) {
      unifiedOrderCreatingRequest.setCurrencyCode(request.getCurrencyCodel());
    }

    if (request.getPartnerId() != null) {
      unifiedOrderCreatingRequest.setPartnerId(request.getPartnerId());
    }

    unifiedOrderCreatingRequest.setMchId(String.valueOf(request.getMchId()));

    if (request.getOutBizNo() != null) {
      unifiedOrderCreatingRequest.setOutBizNo(request.getOutBizNo());
    }

    if (request.getPayerId() != null) {
      unifiedOrderCreatingRequest.setPayerId(request.getPayerId());
    }

    if (request.getPayerNickName() != null) {
      unifiedOrderCreatingRequest.setPayerNickName(request.getPayerNickName());
    }

    if (request.getTradeDesc() != null) {
      unifiedOrderCreatingRequest.setTradeDesc(request.getTradeDesc());
    }

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

    if (request.getFromPlatform() != null) {
      extInfoMap.put(ExtraKeyConstants.FROM_PLATFORM, request.getFromPlatform());
    }

    unifiedOrderCreatingRequest.setExtendInfo(extInfoMap);

    return unifiedOrderCreatingRequest;

  }

  public static UnifiedAcquireOrderResult convertToResult(
      UnifiedOrderCreatingResult unifiedOrderCreatingResult) {
    UnifiedAcquireOrderResult unifiedAcquireOrderResult = new UnifiedAcquireOrderResult();
    if (unifiedOrderCreatingResult.getAcquireNo() != null) {
      unifiedAcquireOrderResult.setUnifiedAquireOrder(unifiedOrderCreatingResult.getAcquireNo());
    }
    return unifiedAcquireOrderResult;
  }

  public static UnifiedAcquireOrder convertToUnifiedAcquireOrder(
      UnifiedAcquireOrderRequest request) {
    UnifiedAcquireOrder unifiedAcquireOrder = new UnifiedAcquireOrder();
    unifiedAcquireOrder.setCurrencyCodel(request.getCurrencyCodel());
    unifiedAcquireOrder.setBizAction(request.getBizAction());
    unifiedAcquireOrder.setBizMode(request.getBizMode());
    unifiedAcquireOrder.setBizProd(request.getBizProd());
    unifiedAcquireOrder.setDiscountableAmount(request.getDiscountableAmount());
    unifiedAcquireOrder.setMchId(request.getMchId());
    unifiedAcquireOrder.setOutBizNo(request.getOutBizNo());
    unifiedAcquireOrder.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    unifiedAcquireOrder.setPartnerReturnUrl(request.getPartnerReturnUrl());
    unifiedAcquireOrder.setPayAmount(request.getPayAmount());
    unifiedAcquireOrder.setPayerId(request.getPayerId());
    unifiedAcquireOrder.setPayerNickName(request.getPayerNickName());
    unifiedAcquireOrder.setTradeDesc(request.getTradeDesc());
    unifiedAcquireOrder.setUndiscountableAmount(request.getUndiscountableAmount());
    return unifiedAcquireOrder;
  }
}
