/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.recharge.handler;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.constants.RechargeConstants;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.group.recharge.CashierReChargeGenUrlRequest;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeGenUrlResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.core.utils.model.GenRechargeInfo;
import com.youzan.pay.unified.cashier.service.cache.CacheService;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierReChargeGenUrlHandler.java, v 0.1 2017-06-22 15:33
 */
@Slf4j
@Component
public class CashierReChargeGenUrlHandler extends AbstractHandler<CashierReChargeGenUrlRequest,CashierReChargeGenUrlResult>{

  @Resource
  private CacheService cacheService;

  @Override
  protected CashierReChargeGenUrlResult execute(CashierReChargeGenUrlRequest request)
      throws Exception {
    String key = request.getSign();
    GenRechargeInfo infoFromCache = cacheService.get(key, GenRechargeInfo.class);
    LogUtils.info(log,"[充值链接存储info]:{}",infoFromCache);
    if (infoFromCache == null) {
      // 生成缓存信息
      infoFromCache = buildGenRechargeInfo(request);
      LogUtils.info(log,"[充值链接存储info_pre]:{}",infoFromCache);
      cacheService.set(key, infoFromCache, GenRechargeInfo.class);
    }

    String url = genUrl(key);
    CashierReChargeGenUrlResult result = buildResult(key, url);
    return result;
  }

  private CashierReChargeGenUrlResult buildResult(String key, String url) {
    CashierReChargeGenUrlResult cashierReChargeGenUrlResult=new CashierReChargeGenUrlResult();
    cashierReChargeGenUrlResult.setStatus("success");
    cashierReChargeGenUrlResult.setCashierUrl(url);
    return cashierReChargeGenUrlResult;
  }

  private GenRechargeInfo buildGenRechargeInfo(CashierReChargeGenUrlRequest request) {
    GenRechargeInfo genRechargeInfo=new GenRechargeInfo();
    genRechargeInfo.setCurrencyCode(request.getCurrencyCode());
    genRechargeInfo.setAppType(request.getAppType());
    genRechargeInfo.setBizAction(request.getBizAction());
    genRechargeInfo.setBizMode(request.getBizMode());
    genRechargeInfo.setBizProd(request.getBizProd());
    genRechargeInfo.setDiscountableAmount(request.getDiscountableAmount());
    genRechargeInfo.setGoodsDesc(request.getGoodsDesc());
    genRechargeInfo.setIsNeedSuccessJump(request.getIsNeedSuccessJump());
    genRechargeInfo.setMchId(request.getMchId());
    genRechargeInfo.setMemo(request.getMemo());
    genRechargeInfo.setOutBizNo(request.getOutBizNo());
    genRechargeInfo.setPartnerId(request.getPartnerId());
    genRechargeInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    genRechargeInfo.setPayAmount(request.getPayAmount());
    genRechargeInfo.setReturnUrl(request.getReturnUrl());
    genRechargeInfo.setSign(request.getSign());
    genRechargeInfo.setSignType(request.getSignType());
    genRechargeInfo.setGoodsName(request.getGoodsName());
    genRechargeInfo.setTel(request.getTel());
    genRechargeInfo.setMchName(request.getMchName());
    genRechargeInfo.setTradeDesc(request.getTradeDesc());
    genRechargeInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    genRechargeInfo.setCustomerId(request.getCustomerId());
    genRechargeInfo.setCardNo(request.getCardNo());
    genRechargeInfo.setRechargePayType(request.getRechargePayType());
    return genRechargeInfo;
  }

  private String genUrl(String key) {
    // 签名

    String url = RechargeConstants.GEN_RECHARGE_CASHIER_URL +key;
    return url;
  }

  public static void main(String[] args)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    String key=RechargeConstants.KEY;
    CashierReChargeGenUrlRequest cashierReChargeGenUrlRequest=new CashierReChargeGenUrlRequest();
    cashierReChargeGenUrlRequest.setRechargePayType(0);
    cashierReChargeGenUrlRequest.setTradeDesc("充值支付");
    cashierReChargeGenUrlRequest.setCardNo("310200195819");
    cashierReChargeGenUrlRequest.setCustomerId("6194234");
    cashierReChargeGenUrlRequest.setMchName("新希望集团");
    cashierReChargeGenUrlRequest.setAppType("WAP");
    cashierReChargeGenUrlRequest.setBizAction("RECHARGE");
    cashierReChargeGenUrlRequest.setBizProd(1003);
    cashierReChargeGenUrlRequest.setSignType("MD5");
    cashierReChargeGenUrlRequest.setBizMode(2006);
    cashierReChargeGenUrlRequest.setCurrencyCode("CNY");
    cashierReChargeGenUrlRequest.setMchId(170829104132387702L);
    cashierReChargeGenUrlRequest.setOutBizNo("weeqwe1231231231sdsd");
    cashierReChargeGenUrlRequest.setPartnerId("820000000003");
    cashierReChargeGenUrlRequest.setPartnerNotifyUrl("http://www.baidu.com");
    cashierReChargeGenUrlRequest.setPayAmount(1);
    cashierReChargeGenUrlRequest.setReturnUrl("http://www.baidu.com");
    cashierReChargeGenUrlRequest.setGoodsName("预付卡余额充值");
    cashierReChargeGenUrlRequest.setGoodsDesc("充值支付");
    cashierReChargeGenUrlRequest.setIsNeedSuccessJump(1);
    cashierReChargeGenUrlRequest.setUndiscountableAmount(0);
    cashierReChargeGenUrlRequest.setTel("");

    System.out.println(SignCheckUtils.checkForCashier(cashierReChargeGenUrlRequest,"",key,"MD5"));

  }
}
