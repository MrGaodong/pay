package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.request.SinglePayRequest;
import com.youzan.pay.assetcenter.api.request.model.PayDetailInfo;
import com.youzan.pay.assetcenter.api.request.model.TradingInfo;
import com.youzan.pay.assetcenter.api.result.SinglePayResult;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.RiskAbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierSinglePayRequest;
import com.youzan.pay.unified.cashier.api.response.CashierSinglePayResponse;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author: clong
 * @date: 2017-04-14
 */
@Component
public class CashierSinglePayHandler extends AbstractHandler<CashierSinglePayRequest, CashierSinglePayResponse>
{
  @Resource
  private UnifiedPayServiceClient unifiedPayServiceClient;

  @Override
  protected CashierSinglePayResponse execute(CashierSinglePayRequest request) {
    Assert.notNull(request, "CashierSinglePayRequest can't be null");

    SinglePayRequest singlePayRequest = toSinglePayRequest(request);

    SinglePayResult singlePayResult = unifiedPayServiceClient.singlePay(singlePayRequest);

    return toCashierSinglePayResponse(singlePayResult);
  }

  private SinglePayRequest toSinglePayRequest(CashierSinglePayRequest request) {
    SinglePayRequest singlePayRequest = new SinglePayRequest();

    singlePayRequest.setAppId("");
    singlePayRequest.setPartnerId(StringUtils.trim(request.getPartnerId()));
    singlePayRequest.setPayDetailInfo(getPayDetailInfo(request));
    singlePayRequest.setTradingInfo(getTradingInfo(request));

    return singlePayRequest;
  }

  private TradingInfo getTradingInfo(CashierSinglePayRequest request) {
    TradingInfo tradingInfo = new TradingInfo();
    tradingInfo.setMchId(StringUtils.trim(request.getMchId()));
    tradingInfo.setOutBizNo(StringUtils.trim(request.getOutBizNo()));
    tradingInfo.setPayerId(request.getPayerId());
    tradingInfo.setPayerNickName(StringUtils.trim(request.getPayerNickName()));
    tradingInfo.setTradeDesc(StringUtils.trim(request.getTradeDesc()));
    return tradingInfo;
  }

  private PayDetailInfo getPayDetailInfo(CashierSinglePayRequest request) {
    PayDetailInfo payDetailInfo = new PayDetailInfo();
    payDetailInfo.setGoodsDesc(StringUtils.trim(request.getGoodsDesc()));
    payDetailInfo.setPartnerNotifyUrl(StringUtils.trim(request.getPartnerNotifyUrl()));
    payDetailInfo.setPartnerReturnUrl(StringUtils.trim(request.getPartnerReturnUrl()));
    payDetailInfo.setCurrencyCode(StringUtils.trim(request.getCurrencyCode()));
    payDetailInfo.setDiscountableAmount(request.getDiscountableAmount());
    payDetailInfo.setPayAmount(request.getPayAmount());
    payDetailInfo.setPayTool(StringUtils.trim(request.getPayTool()));
    payDetailInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    payDetailInfo.setMemo(StringUtils.trim(request.getMemo()));
    payDetailInfo.setOutBizInfo(getOutBizInfo(request));
    payDetailInfo.setYzAuthToken(StringUtils.trim(request.getYzAuthToken()));
    return payDetailInfo;
  }

  private Map<String, String> getOutBizInfo(CashierSinglePayRequest request) {
    if (!PayToolTypeEnum.WX_H5.name().equalsIgnoreCase(request.getPayTool())) {
      return Collections.emptyMap();
    }

    Map<String, String> outBizInfo = new HashMap<>();
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

  private String getAppNameKey(String appType) {
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

  private String getBundleKey(String appType) {
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

  private CashierSinglePayResponse toCashierSinglePayResponse(SinglePayResult response) {
    CashierSinglePayResponse cashierSinglePayResponse = new CashierSinglePayResponse();
    cashierSinglePayResponse.setOutBizNo(response.getOutBizNo());
    cashierSinglePayResponse.setAcquireNo(response.getAcquireNo());
    cashierSinglePayResponse.setDeepLinkInfo(response.getDeepLinkInfo());

    return cashierSinglePayResponse;
  }

}
