/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.constant.Constant;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.GenPayQRCodeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.core.utils.QRCodeGenerator;
import com.youzan.pay.unified.cashier.service.cache.CacheService;
import com.youzan.pay.unified.cashier.service.sign.SignService;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version GenPayQRCodeHandler.java, v 0.1 2017-06-05 18:47
 */
@Slf4j
@Component
public class GenPayQRCodeHandler extends AbstractHandler<GenPayQRCodeRequest, QRCodeInfoResult> {

  @Resource
  private CacheService cacheService;

  @Override
  protected QRCodeInfoResult execute(GenPayQRCodeRequest request) throws Exception {
    LogUtils.info(log, "生成充值二维码开始,request={}", request);

    if(request.getPayAmount() <= 0) {
      throw new IllegalArgumentException();
    }

    String key = request.getPartnerId() + request.getOutBizNo();
    QRCodeInfo infoFromCache = cacheService.get(key, QRCodeInfo.class);
    if (infoFromCache == null) {
      LogUtils.info(log, "插入充值二维码信息开始,key={}", key);
      // 生成缓存信息
      QRCodeInfo info = contractQRCodeInfo(request);
      cacheService.set(key, info, QRCodeInfo.class);
      LogUtils.info(log, "插入充值二维码信息结束,info={}", info);
    }

    String url = genUrl(key);
    QRCodeInfoResult result = buildResult(key, url);

    LogUtils.info(log, "生成充值二维码结束,url={},result={}", url, result);
    return result;
  }

  private QRCodeInfo contractQRCodeInfo(GenPayQRCodeRequest request) {
    QRCodeInfo info = new QRCodeInfo();
    info.setPartnerId(request.getPartnerId());
    info.setMchId(request.getMchId());
    info.setOutBizNo(request.getOutBizNo());
    info.setTradeDesc(request.getTradeDesc());
    info.setPayAmount(request.getPayAmount());
    info.setDiscountableAmount(request.getDiscountableAmount());
    info.setUndiscountableAmount(request.getUndiscountableAmount());
    info.setCurrencyCode(request.getCurrencyCode());
    info.setGoodsDesc(request.getGoodsDesc());
    info.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    info.setBizProd(request.getBizProd());
    info.setBizMode(request.getBizMode());
    info.setBizAction(request.getBizAction());

    info.setIsNeedSuccessJump(request.getIsNeedSuccessJump());

    // return url
    info.setReturnUrl(request.getReturnUrl());

    info.setGoodsDesc(request.getGoodsDesc());
    info.setMchName(request.getMchName());

    return info;
  }


  private QRCodeInfoResult buildResult(String key,
                                       String url) {

    QRCodeInfoResult result = new QRCodeInfoResult();

    result.setCodeId(key);
    result.setContent(url);
    result.setEncoding("BASE64");
    result.setCodeFormat("QR_CODE");
    result.setImageFormat("png");
    result.setWidth(300);
    result.setHeight(300);
    return result;
  }

  private String genUrl(String key) {
    Map<String, String> data = new HashMap<>();
    String createTime = String.valueOf(System.currentTimeMillis());
    data.put("imageNo", key);
    data.put("createTime", createTime);

    // 签名
    String sign = SignService.sign(data, Constant.signKey, Constant.signType);

    String url = Constant.PAY_URL_PREFIX + "?" +
                 "imageNo=" + key + "&" +
                 "createTime=" + createTime;
    return url;
  }
}
