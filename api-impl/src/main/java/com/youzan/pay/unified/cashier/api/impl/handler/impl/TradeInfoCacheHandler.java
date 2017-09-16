/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.TradeInfoRequest;
import com.youzan.pay.unified.cashier.api.result.TradeInfoResult;
import com.youzan.pay.unified.cashier.core.utils.model.CacheTradeInfo;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.springframework.stereotype.Component;

import sun.rmi.runtime.Log;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version TradeInfoCacheHandler.java, v 0.1 2017-06-20 00:09
 */
@Slf4j
@Component
public class TradeInfoCacheHandler extends AbstractHandler<TradeInfoRequest,TradeInfoResult> {

  @Resource
  private CacheService cacheService;

  @Override
  protected TradeInfoResult execute(TradeInfoRequest request) throws Exception {
    LogUtils.info(log,"redis插入数据集合:{}",request);
    CacheTradeInfo cacheTradeInfo=buildCacheTradeInfo(request);
    LogUtils.info(log,"插入redis对象集合:{}",cacheTradeInfo);
    cacheService.set(request.getSign(),cacheTradeInfo,CacheTradeInfo.class);

    TradeInfoResult result=genTradeResult();
    return result;
  }

  private TradeInfoResult genTradeResult() {
    TradeInfoResult tradeInfoResult=new TradeInfoResult();
    tradeInfoResult.setStatus("success");
    return tradeInfoResult;
  }

  private CacheTradeInfo buildCacheTradeInfo(TradeInfoRequest request) {
    CacheTradeInfo cacheTradeInfo=new CacheTradeInfo();
    cacheTradeInfo.setCurrencyCode(request.getCurrencyCode());
    cacheTradeInfo.setAppType(request.getAppType());
    cacheTradeInfo.setBizAction(request.getBizAction());
    cacheTradeInfo.setBizMode(request.getBizMode());
    cacheTradeInfo.setBizProd(request.getBizProd());
    cacheTradeInfo.setDiscountableAmount(request.getDiscountableAmount());
    cacheTradeInfo.setGoodsDesc(request.getGoodsDesc());
    cacheTradeInfo.setIsNeedSuccessJump(request.getIsNeedSuccessJump());
    cacheTradeInfo.setMchId(request.getMchId());
    cacheTradeInfo.setMemo(request.getMemo());
    cacheTradeInfo.setOutBizNo(request.getOutBizNo());
    cacheTradeInfo.setPartnerId(request.getPartnerId());
    cacheTradeInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    cacheTradeInfo.setPayAmount(request.getPayAmount());
    cacheTradeInfo.setReturnUrl(request.getReturnUrl());
    cacheTradeInfo.setSign(request.getSign());
    cacheTradeInfo.setSignType(request.getSignType());
    cacheTradeInfo.setGoodsName(request.getGoodsName());
    cacheTradeInfo.setTel(request.getTel());
    cacheTradeInfo.setMchName(request.getMchName());
    cacheTradeInfo.setTradeDesc(request.getTradeDesc());
    cacheTradeInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    cacheTradeInfo.setIsNeedPopupView(request.getIsNeedPopupView());

    cacheTradeInfo.setCustomerId(request.getCustomerId());
    cacheTradeInfo.setImageUrl(request.getImageUrl());
    cacheTradeInfo.setSlideText(request.getSlideText());
    return cacheTradeInfo;
  }
}
