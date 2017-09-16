/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;
import com.youzan.pay.assetcenter.api.request.RechargePayRequest;
import com.youzan.pay.assetcenter.api.request.model.PayDetailInfo;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargePayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.assetcenter.service.spi.constants.ExtraKeyConstants;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.impl.enums.PageOperationEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractDispatcherPayHandler;
import com.youzan.pay.unified.cashier.api.request.CashierReChargePayRequest;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargePayResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.DispatcherPayErrorCode;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierReChargePayHandler.java, v 0.1 2017-06-06 15:19
 */
@Slf4j
@Component
public class CashierReChargePayHandler
    extends AbstractDispatcherPayHandler<CashierReChargePayRequest,CashierReChargePayResult> {
  @Override
  protected void doBefore(CashierReChargePayRequest request) {
    super.doBefore(request);
    String payChannel = request.getPayTool();
    if (PayToolEnum.getByName(payChannel) == null) {
      throw new IllegalArgumentException("支付方式不存在");
    }
  }
  @Override
  protected CashierReChargePayResult execute(CashierReChargePayRequest request) throws Exception {

    LogUtils.info(log, "开始支付，request={}", request);

    RechargePayRequest rechargeRequest = buildRechargeResult(request);

    Response<RechargePayResult> response = cashierRechargeServiceClient.pay(rechargeRequest);

    CashierReChargePayResult cashierReChargePayResult = null;

    if (response.isSuccess()) {        //支付成功
      cashierReChargePayResult = paySuccess(cashierReChargePayResult, response, request);
    } else {                           //支付失败
      cashierReChargePayResult = payFail(cashierReChargePayResult, response, request);
    }

    LogUtils.info(log, "支付结束，result={}", cashierReChargePayResult);

    return cashierReChargePayResult;
  }

  private CashierReChargePayResult paySuccess(CashierReChargePayResult cashierReChargePayResult, Response<RechargePayResult> response, CashierReChargePayRequest request) {
    if (PayToolEnum.WX_JS.isEquals(request.getPayTool()) || PayToolEnum.ALIPAY_WAP.isEquals(request.getPayTool())
        || PayToolEnum.WX_H5.isEquals(request.getPayTool())) {
      cashierReChargePayResult = genResult(PageOperationEnum.CALL_CASHIER, response);
    }
    if (PayToolEnum.ECARD.isEquals(request.getPayTool()) || PayToolEnum.BALANCE.isEquals(request.getPayTool())||
        PayToolEnum.GIFT_CARD.isEquals(request.getPayTool())) {
      cashierReChargePayResult = new CashierReChargePayResult();
    }
    return cashierReChargePayResult;
  }

  private CashierReChargePayResult genResult(PageOperationEnum operation, Response<RechargePayResult> response) {
    RechargePayResult rechargeResult=response.getResult();
    CashierReChargePayResult cashierReChargePayResult = buildCashierReChargePayResult(rechargeResult);
    cashierReChargePayResult.setOperation(operation.name());
    return cashierReChargePayResult;
  }

  private CashierReChargePayResult buildCashierReChargePayResult(RechargePayResult rechargeResult) {
    List<PayDetailResult> payDetailResults;

    if (rechargeResult != null && CollectionUtils
        .isNotEmpty(payDetailResults = rechargeResult.getPayDetailResult())) {

      PayDetailResult payDetailResult = payDetailResults.get(0);

      CashierReChargePayResult cashierReChargePayResult = new CashierReChargePayResult();
      cashierReChargePayResult.setDeepLinkInfo(JSON.toJSONString(payDetailResult.getDeepLinkInfo()));

      return cashierReChargePayResult;
    } else {
      throw new BaseException(DispatcherPayErrorCode.PAY_RESULT_ERROR);
    }
  }

  private CashierReChargePayResult payFail(CashierReChargePayResult cashierReChargePayResult, Response<RechargePayResult> response,
                                           CashierReChargePayRequest request) {
     if (REPEATE_PAY_CODE.equals(response.getResultCode())) {
      return new CashierReChargePayResult(PageOperationEnum.REPEATE_PAY.name());
    } else {
      LogUtils.error(log, "调用充值支付接口失败，request={}，response={}", request, response);
      throw new BaseException(DispatcherPayErrorCode.SYSTEM_ERROR);
    }
  }


  private RechargePayRequest buildRechargeResult(CashierReChargePayRequest request) {
    RechargePayRequest rechargeRequest = new RechargePayRequest();

    rechargeRequest.setAcquireNo(request.getAcquireNo());
    rechargeRequest.setOutBizNo(request.getOutBizNo());
    rechargeRequest.setPayerId(request.getPayerId());
    rechargeRequest.setMchId(request.getMchId());
    rechargeRequest.setPartnerId(request.getPartnerId());
    rechargeRequest.setPayAmount(request.getPayAmount());
    rechargeRequest.setCardNo(request.getCardNo());
    rechargeRequest.setCustomerId(request.getCustomerId());
    rechargeRequest.setRechargePayType(request.getRechargePayType());

    List<PayDetailInfo> payDetailInfos = new ArrayList<>();
    PayDetailInfo payDetailInfo = new PayDetailInfo();

    payDetailInfo.setPayTool(request.getPayTool());
    payDetailInfo.setPayAmount(request.getPayAmount());
    payDetailInfo.setDiscountableAmount(request.getDiscountableAmount());
    payDetailInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    payDetailInfo.setMemo(request.getMemo());
    payDetailInfo.setGoodsDesc(request.getTradeDesc());
    payDetailInfo.setAuthCode(request.getAuthCode());
    payDetailInfo.setWxSubOpenId(request.getWxSubOpenId());
    payDetailInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    payDetailInfo.setPartnerReturnUrl(request.getPartnerReturnUrl());
    payDetailInfo.setOutBizInfo(getOutBizInfo(request));
    payDetailInfo.setExtendInfo(getExtendInfo(request));
    payDetailInfos.add(payDetailInfo);
    rechargeRequest.setPayDetailInfos(payDetailInfos);

    rechargeRequest.setRequestId(TracerContextUtils.getTracerId());

    return rechargeRequest;
  }

  private Map<String,String> getExtendInfo(CashierReChargePayRequest request) {
    Map<String,String> map= Maps.newHashMap();
    String userNo=getUserNo(request.getPayerId());
    map.put(ExtraKeyConstants.BUYER_USER_NO,userNo);
    return map;
  }

  private static Map<String, String> getOutBizInfo(CashierReChargePayRequest request) {

    Map<String, String> outBizInfo = new HashMap<>();

    if (!PayToolTypeEnum.WX_H5.name().equalsIgnoreCase(request.getPayTool())) {
      outBizInfo.put(ExtraKeyConstants.WX_SELF_OPENID,request.getWxSelfOpenId());
      outBizInfo.put(ExtraKeyConstants.WX_YZ_OPENID,request.getWxYzOpenId());
      outBizInfo.put(ExtraKeyConstants.WX_CITIC_OPENID,request.getWxCiticOpenId());
      return outBizInfo;
    }

    outBizInfo.put("client_ip", StringUtils.trim(request.getUserAgentIp()));

    Map<String, Map<String, String>> map = new HashMap<>();

    Map<String, String> h5InfoBoxer = new HashMap<>();
    Map<String, String> h5Info = new HashMap<>();
    String appType = StringUtils.trimToNull(request.getAppType());
    h5Info.put("type", appType);
    h5Info.put(getAppNameKey(appType), StringUtils.trim(request.getAppName()));
    h5Info.put(getBundleKey(appType), StringUtils.trim(request.getDistributionPackageName()));

    h5InfoBoxer.put("h5_info", JSON.toJSONString(h5Info));

    map.put("scene_info", h5InfoBoxer);

    outBizInfo.put("scene_info", JSON.toJSONString(map));

    return outBizInfo;
  }

  private static String getAppNameKey(String appType) {
    String appNameKey = null;

    if ("IOS".equalsIgnoreCase(appType)) {
      appNameKey = "app_name";
    } else if ("Wap".equalsIgnoreCase(appType)) {
      appNameKey = "wap_name";
    } else if ("Android".equalsIgnoreCase(appType)) {
      appNameKey = "app_name";
    }

    return appNameKey;
  }

  private static String getBundleKey(String appType) {
    String appNameKey = null;

    if ("IOS".equalsIgnoreCase(appType)) {
      appNameKey = "bundle_id";
    } else if ("Wap".equalsIgnoreCase(appType)) {
      appNameKey = "wap_url";
    } else if ("Android".equalsIgnoreCase(appType)) {
      appNameKey = "package_name";
    }

    return appNameKey;
  }

}
