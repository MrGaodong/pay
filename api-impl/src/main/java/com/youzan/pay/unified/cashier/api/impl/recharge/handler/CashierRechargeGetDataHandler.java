/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.recharge.handler;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeGetDataRequest;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierRechargeGetDataResult;
import com.youzan.pay.unified.cashier.core.utils.model.GenRechargeInfo;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierRechargeGetDataHandler.java, v 0.1 2017-06-22 17:57
 */
@Slf4j
@Component
public class CashierRechargeGetDataHandler extends
                                           AbstractHandler<CashierRechargeGetDataRequest,CashierRechargeGetDataResult> {

  @Resource
  private CacheService cacheService;

  @Override
  protected CashierRechargeGetDataResult execute(CashierRechargeGetDataRequest request)
      throws Exception {
    GenRechargeInfo genRechargeInfo = cacheService.get(request.getSign(), GenRechargeInfo.class);
    LogUtils.info(log,"[cashierRechargeGetData＝]：｛｝",genRechargeInfo);
    if(genRechargeInfo==null){
      LogUtils.warn(log,"[没有查到对应充值信息，没办法，就是没有,sign=]{}",request.getSign());
      return null;
    }else {
      return buildCashierRechargeGetDataResult(genRechargeInfo);
    }
  }

  private CashierRechargeGetDataResult buildCashierRechargeGetDataResult(GenRechargeInfo genRechargeInfo) {
    CashierRechargeGetDataResult cashierRechargeGetDataResult=new CashierRechargeGetDataResult();
    cashierRechargeGetDataResult.setCurrencyCode(genRechargeInfo.getCurrencyCode());
    cashierRechargeGetDataResult.setAppType(genRechargeInfo.getAppType());
    cashierRechargeGetDataResult.setBizAction(genRechargeInfo.getBizAction());
    cashierRechargeGetDataResult.setBizMode(genRechargeInfo.getBizMode());
    cashierRechargeGetDataResult.setBizProd(genRechargeInfo.getBizProd());
    cashierRechargeGetDataResult.setDiscountableAmount(genRechargeInfo.getDiscountableAmount());
    cashierRechargeGetDataResult.setGoodsDesc(genRechargeInfo.getGoodsDesc());
    cashierRechargeGetDataResult.setIsNeedSuccessJump(genRechargeInfo.getIsNeedSuccessJump());
    cashierRechargeGetDataResult.setMchId(genRechargeInfo.getMchId());
    cashierRechargeGetDataResult.setMemo(genRechargeInfo.getMemo());
    cashierRechargeGetDataResult.setOutBizNo(genRechargeInfo.getOutBizNo());
    cashierRechargeGetDataResult.setPartnerId(genRechargeInfo.getPartnerId());
    cashierRechargeGetDataResult.setPartnerNotifyUrl(genRechargeInfo.getPartnerNotifyUrl());
    cashierRechargeGetDataResult.setPayAmount(genRechargeInfo.getPayAmount());
    cashierRechargeGetDataResult.setReturnUrl(genRechargeInfo.getReturnUrl());
    cashierRechargeGetDataResult.setSign(genRechargeInfo.getSign());
    cashierRechargeGetDataResult.setSignType(genRechargeInfo.getSignType());
    cashierRechargeGetDataResult.setGoodsName(genRechargeInfo.getGoodsName());
    cashierRechargeGetDataResult.setTel(genRechargeInfo.getTel());
    cashierRechargeGetDataResult.setMchName(genRechargeInfo.getMchName());
    cashierRechargeGetDataResult.setTradeDesc(genRechargeInfo.getTradeDesc());
    cashierRechargeGetDataResult.setUndiscountableAmount(genRechargeInfo.getUndiscountableAmount());
    cashierRechargeGetDataResult.setCustomerId(genRechargeInfo.getCustomerId());
    cashierRechargeGetDataResult.setCardNo(genRechargeInfo.getCardNo());
    cashierRechargeGetDataResult.setRechargePayType(genRechargeInfo.getRechargePayType());
    LogUtils.info(log,"［GenRechargeInfo=］:{}",genRechargeInfo);

    return cashierRechargeGetDataResult;
  }
}
