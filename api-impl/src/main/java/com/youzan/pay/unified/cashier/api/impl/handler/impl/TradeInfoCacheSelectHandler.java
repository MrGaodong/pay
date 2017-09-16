/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.TradeInfoListRequest;
import com.youzan.pay.unified.cashier.api.result.TradeInfoListResult;
import com.youzan.pay.unified.cashier.core.utils.model.CacheTradeInfo;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.springframework.stereotype.Component;

import sun.rmi.runtime.Log;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version TradeInfoCacheSelectHandler.java, v 0.1 2017-06-20 00:44
 */
@Slf4j
@Component
public class TradeInfoCacheSelectHandler extends AbstractHandler<TradeInfoListRequest,TradeInfoListResult> {

  @Resource
  private CacheService cacheService;

  @Override
  protected TradeInfoListResult execute(TradeInfoListRequest request) throws Exception {
    LogUtils.info(log,"redis获取数据,request:{}",request);
    CacheTradeInfo cacheTradeInfo=cacheService.get(request.getSign(),CacheTradeInfo.class);
    if(cacheTradeInfo==null){
      LogUtils.info(log,"[cacheTradeInfo为空]");
      return new TradeInfoListResult();
    }
    TradeInfoListResult tradeInfoListResult=buildCacheResult(cacheTradeInfo);
    LogUtils.info(log,"获取redis结果结合:{}",tradeInfoListResult);
    return tradeInfoListResult;
  }

  private TradeInfoListResult buildCacheResult(CacheTradeInfo cacheTradeInfo) {
    TradeInfoListResult result=new TradeInfoListResult();
    result.setMchName(cacheTradeInfo.getMchName());
    result.setTel(cacheTradeInfo.getTel());
    result.setCurrencyCode(cacheTradeInfo.getCurrencyCode());
    result.setAppType(cacheTradeInfo.getAppType());
    result.setBizAction(cacheTradeInfo.getBizAction());
    result.setBizMode(cacheTradeInfo.getBizMode());
    result.setBizProd(cacheTradeInfo.getBizProd());
    result.setDiscountableAmount(cacheTradeInfo.getDiscountableAmount());
    result.setGoodsDesc(cacheTradeInfo.getGoodsDesc());
    result.setIsNeedSuccessJump(cacheTradeInfo.getIsNeedSuccessJump());
    result.setMchId(cacheTradeInfo.getMchId());
    result.setMemo(cacheTradeInfo.getMemo());
    result.setOutBizNo(cacheTradeInfo.getOutBizNo());
    result.setPartnerId(cacheTradeInfo.getPartnerId());
    result.setPartnerNotifyUrl(cacheTradeInfo.getPartnerNotifyUrl());
    result.setPayAmount(cacheTradeInfo.getPayAmount());
    result.setReturnUrl(cacheTradeInfo.getReturnUrl());
    result.setSign(cacheTradeInfo.getSign());
    result.setSignType(cacheTradeInfo.getSignType());
    result.setGoodsName(cacheTradeInfo.getGoodsName());
    result.setTradeDesc(cacheTradeInfo.getTradeDesc());
    result.setUndiscountableAmount(cacheTradeInfo.getUndiscountableAmount());
    result.setIsNeedPopupView(cacheTradeInfo.getIsNeedPopupView());
    result.setCustomerId(cacheTradeInfo.getCustomerId());

    result.setImageUrl(cacheTradeInfo.getImageUrl());
    result.setSlideText(cacheTradeInfo.getSlideText());
    return result;
  }

}
