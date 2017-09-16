/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.impl.model.GoodsInfo;
import com.youzan.pay.unified.cashier.api.request.GoodsInfoRequest;
import com.youzan.pay.unified.cashier.api.result.GoodsInfoResult;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version GoodsInfoHandler.java, v 0.1 2017-06-24 15:12
 */
@Slf4j
@Component
public class GoodsInfoHandler extends AbstractHandler<GoodsInfoRequest,GoodsInfoResult> {

  @Resource
  private CacheService cacheService;

  @Override
  protected GoodsInfoResult execute(GoodsInfoRequest request) throws Exception {
    LogUtils.info(log,"[收银台商品信息列表集合]:{}",request);
    GoodsInfo goodsInfo=cacheService.get(request.getAcquireNo(), GoodsInfo.class);
    if(goodsInfo==null){
      goodsInfo=buildGoodsInfo(request);
      cacheService.set(request.getAcquireNo(),goodsInfo,GoodsInfo.class);
    }
    return buildGoodsInfoResult(goodsInfo);
  }

  private GoodsInfoResult buildGoodsInfoResult(GoodsInfo goodsInfo) {
    GoodsInfoResult goodsInfoResult=new GoodsInfoResult();
    goodsInfoResult.setTel(goodsInfo.getTel());
    goodsInfoResult.setMchName(goodsInfo.getMchName());
    goodsInfoResult.setPayerId(goodsInfo.getPayerId());
    goodsInfoResult.setReturnUrl(goodsInfo.getReturnUrl());
    goodsInfoResult.setUndiscountableAmount(goodsInfo.getUndiscountableAmount());
    goodsInfoResult.setAccount(goodsInfo.getAccount());
    goodsInfoResult.setAcquireNo(goodsInfo.getAcquireNo());
    goodsInfoResult.setGoodsName(goodsInfo.getGoodsName());
    goodsInfoResult.setDiscountableAmount(goodsInfo.getDiscountableAmount());
    goodsInfoResult.setImgUrl(goodsInfo.getImgUrl());
    goodsInfoResult.setIsNeedSuccessJump(goodsInfo.getIsNeedSuccessJump());
    goodsInfoResult.setMemo(goodsInfo.getMemo());
    goodsInfoResult.setOutBizNo(goodsInfo.getOutBizNo());
    goodsInfoResult.setPartnerId(goodsInfo.getPartnerId());
    goodsInfoResult.setPartnerNotifyUrl(goodsInfo.getPartnerNotifyUrl());
    goodsInfoResult.setPartnerReturnUrl(goodsInfo.getPartnerReturnUrl());
    goodsInfoResult.setPayWays(goodsInfo.getPayWays());
    goodsInfoResult.setTradeDesc(goodsInfo.getTradeDesc());
    goodsInfoResult.setUserAgentIp(goodsInfo.getUserAgentIp());
    goodsInfoResult.setMchId(goodsInfo.getMchId());
    goodsInfoResult.setWxSubOpenId(goodsInfo.getWxSubOpenId());
    goodsInfoResult.setPayAmount(goodsInfo.getPayAmount());
    goodsInfoResult.setCardNo(goodsInfo.getCardNo());
    goodsInfoResult.setCustomerId(goodsInfo.getCustomerId());
    goodsInfoResult.setRechargePayType(goodsInfo.getRechargePayType());
    goodsInfoResult.setValueCardNo(goodsInfo.getValueCardNo());
    return goodsInfoResult;
  }

  private GoodsInfo buildGoodsInfo(GoodsInfoRequest request) {
    GoodsInfo goodsInfo=new GoodsInfo();
    goodsInfo.setMchName(request.getMchName());
    goodsInfo.setReturnUrl(request.getReturnUrl());
    goodsInfo.setGoodsName(request.getGoodsName());
    goodsInfo.setAccount(request.getAccount());
    goodsInfo.setAcquireNo(request.getAcquireNo());
    goodsInfo.setDiscountableAmount(request.getDiscountableAmount());
    goodsInfo.setImgUrl(request.getImgUrl());
    goodsInfo.setIsNeedSuccessJump(request.getIsNeedSuccessJump());
    goodsInfo.setMchId(request.getMchId());
    goodsInfo.setMemo(request.getMemo());
    goodsInfo.setOutBizNo(request.getOutBizNo());
    goodsInfo.setPartnerId(request.getPartnerId());
    goodsInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    goodsInfo.setPartnerReturnUrl(request.getPartnerReturnUrl());
    goodsInfo.setPayAmount(request.getPayAmount());
    goodsInfo.setPayerId(request.getPayerId());
    goodsInfo.setPayWays(request.getPayWays());
    goodsInfo.setUserAgentIp(request.getUserAgentIp());
    goodsInfo.setWxSubOpenId(request.getWxSubOpenId());
    goodsInfo.setTradeDesc(request.getTradeDesc());
    goodsInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    goodsInfo.setCustomerId(request.getCustomerId());
    goodsInfo.setRechargePayType(request.getRechargePayType());
    goodsInfo.setCardNo(request.getCardNo());
    goodsInfo.setTel(request.getTel());

    goodsInfo.setCustomerId(request.getCustomerId());
    goodsInfo.setImageUrl(request.getImageUrl());
    goodsInfo.setSlideText(request.getSlideText());
    goodsInfo.setValueCardNo(request.getValueCardNo());
    return goodsInfo;
  }
}
