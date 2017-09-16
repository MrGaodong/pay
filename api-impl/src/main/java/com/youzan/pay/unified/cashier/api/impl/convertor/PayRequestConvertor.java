/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.convertor;

import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.request.RechargePayRequest;
import com.youzan.pay.assetcenter.api.request.model.PayDetailInfo;
import com.youzan.pay.assetcenter.service.spi.constants.ExtraKeyConstants;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeRechargeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodePayRequest;
import com.youzan.platform.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author twb
 * @version PayRequestConvertor.java, v 0.1 2017-01-11 11:30
 */
public class PayRequestConvertor {

  //预付卡标志
  private final String valueCardNo="valueCardNo";

  public static MultiPayRequest convert(PayRequest request,String payerMchId) {

    MultiPayRequest multiPayRequest = new MultiPayRequest();

    multiPayRequest.setAcquireNo(request.getAcquireNo());
    multiPayRequest.setOutBizNo(request.getOutBizNo());
    multiPayRequest.setPayerId(request.getPayerId());
    multiPayRequest.setMchId(request.getMchId());
    multiPayRequest.setPartnerId(request.getPartnerId());
    multiPayRequest.setPayAmount(request.getPayAmount());

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
    //买家商户号放在扩展字段里。
    payDetailInfo.getExtendInfo().put(ExtraKeyConstants.BUYER_USER_NO, payerMchId);
    payDetailInfo.setOutBizInfo(getOutBizInfo(request,payerMchId));
    //预付卡卡号字段
    payDetailInfo.setValueCardNo(request.getValueCardNo());
    payDetailInfos.add(payDetailInfo);
    multiPayRequest.setPayDetailInfos(payDetailInfos);

    //支付扩展map
    multiPayRequest.setExtendInfo(getExtendInfo(request));

    multiPayRequest.setRequestId(TracerContextUtils.getTracerId());


    return multiPayRequest;
  }

  /**
   * 获取扩展信息
   * @param request
   * @return
   */
  private static Map<String,String> getExtendInfo(PayRequest request) {
    Map<String,String> map= Maps.newHashMap();
    if(StringUtils.isNotBlank(request.getBizExt())){
      map.put(ExtraKeyConstants.BIZ_EXT_KEY,request.getBizExt());
    }
    return map;
  }

  // 预付卡充值
  public static RechargePayRequest convert(QRCodeRechargeRequest request) {
    RechargePayRequest rechargePayRequest = new RechargePayRequest();

    rechargePayRequest.setAcquireNo(request.getAcquireNo());

    rechargePayRequest.setOutBizNo(request.getOutBizNo());
    rechargePayRequest.setMchId(request.getMchId());
    rechargePayRequest.setPartnerId(request.getPartnerId());
    rechargePayRequest.setPayAmount(request.getPayAmount());
    rechargePayRequest.setPayerId(request.getPayerId());

    PayDetailInfo payDetailInfo = new PayDetailInfo();

    payDetailInfo.setPayTool(request.getPayTool());
    payDetailInfo.setPayAmount(request.getPayAmount());
    payDetailInfo.setDiscountableAmount(request.getDiscountableAmount());
    payDetailInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    payDetailInfo.setGoodsDesc(request.getTradeDesc());
    payDetailInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    payDetailInfo.setPartnerReturnUrl(request.getPartnerReturnUrl());

    payDetailInfo.setWxSubOpenId(request.getWxSubOpenId());

    payDetailInfo.setOutBizInfo(getOutBizInfo(request));

    List<PayDetailInfo> payDetailInfos = new ArrayList<>();
    payDetailInfos.add(payDetailInfo);
    rechargePayRequest.setPayDetailInfos(payDetailInfos);

    rechargePayRequest.setRequestId(TracerContextUtils.getTracerId());

    //=========预付卡
    rechargePayRequest.setRechargePayType(request.getRechargePayType());
    rechargePayRequest.setCardNo(request.getCardNo());
    rechargePayRequest.setCustomerId(request.getCustomerId());

    return rechargePayRequest;
  }

  private static Map<String,String> getOutBizInfo(QRCodeRechargeRequest request) {
    Map<String, String> outBizInfo = new HashMap<>();
    outBizInfo.put("client_ip", StringUtils.trim(request.getUserAgentIp()));

    outBizInfo.put(ExtraKeyConstants.WX_SELF_OPENID,request.getWxSelfOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_YZ_OPENID,request.getWxYzOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_CITIC_OPENID,request.getWxCiticOpenId());

    return outBizInfo;
  }

  public static MultiPayRequest convert(QRCodePayRequest request) {
    MultiPayRequest multiPayRequest = new MultiPayRequest();

    multiPayRequest.setAcquireNo(request.getAcquireNo());

    multiPayRequest.setOutBizNo(request.getOutBizNo());
    multiPayRequest.setMchId(request.getMchId());
    multiPayRequest.setPartnerId(request.getPartnerId());
    multiPayRequest.setPayAmount(request.getPayAmount());
    multiPayRequest.setPayerId(request.getPayerId());

    PayDetailInfo payDetailInfo = new PayDetailInfo();

    payDetailInfo.setPayTool(request.getPayTool());
    payDetailInfo.setPayAmount(request.getPayAmount());
    payDetailInfo.setDiscountableAmount(request.getDiscountableAmount());
    payDetailInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    payDetailInfo.setGoodsDesc(request.getTradeDesc());
    payDetailInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    payDetailInfo.setPartnerReturnUrl(request.getPartnerReturnUrl());

    payDetailInfo.setWxSubOpenId(request.getWxSubOpenId());

    payDetailInfo.setOutBizInfo(getOutBizInfo(request));


    List<PayDetailInfo> payDetailInfos = new ArrayList<>();
    payDetailInfos.add(payDetailInfo);
    multiPayRequest.setPayDetailInfos(payDetailInfos);

    multiPayRequest.setRequestId(TracerContextUtils.getTracerId());

    return multiPayRequest;
  }

  private static Map<String,String> getOutBizInfo(QRCodePayRequest request) {
    Map<String, String> outBizInfo = new HashMap<>();
    outBizInfo.put("client_ip", StringUtils.trim(request.getUserAgentIp()));

    outBizInfo.put(ExtraKeyConstants.WX_SELF_OPENID,request.getWxSelfOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_YZ_OPENID,request.getWxYzOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_CITIC_OPENID,request.getWxCiticOpenId());

    return outBizInfo;
  }

  private static Map<String, String> getOutBizInfo(PayRequest request,String payerMchId) {

    Map<String, String> outBizInfo = new HashMap<>();
    outBizInfo.put(ExtraKeyConstants.BUYER_USER_NO, payerMchId);
    outBizInfo.put("client_ip", StringUtils.trim(request.getUserAgentIp()));

    Map<String, String> h5InfoBoxer = new HashMap<>();
    Map<String, String> h5Info = new HashMap<>();
    String appType = StringUtils.trimToNull(request.getAppType());
    h5Info.put("type", appType);
    h5Info.put(getAppNameKey(appType), StringUtils.trim(request.getAppName()));
    h5Info.put(getBundleKey(appType), StringUtils.trim(request.getDistributionPackageName()));

    h5InfoBoxer.put("h5_info", JSON.toJSONString(h5Info));
    outBizInfo.put("scene_info", JSON.toJSONString(h5InfoBoxer));

    outBizInfo.put(ExtraKeyConstants.WX_SELF_OPENID,request.getWxSelfOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_YZ_OPENID,request.getWxYzOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_CITIC_OPENID,request.getWxCiticOpenId());
    outBizInfo.put(ExtraKeyConstants.WX_FORCE_PROXY,convertToWxForceProxy(request));


    return outBizInfo;
  }

  private static String convertToWxForceProxy(PayRequest request) {

    if(request.getWxForceProxy()==null){
      return "false";
    }
    if(request.getWxForceProxy().equals(0)){
      return "false";
    }
    else if(request.getWxForceProxy().equals(1)){
      return "true";
    }
    return "false";
  }

  private static String getAppNameKey(String appType) {
    String appNameKey = "wap_name";



    return appNameKey;
  }

  private static String getBundleKey(String appType) {
    String appNameKey = "wap_url";


    return appNameKey;
  }

}
