/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.assetcenter.api.request.RechargePayRequest;
import com.youzan.pay.assetcenter.api.request.model.PayDetailInfo;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargePayResult;
import com.youzan.pay.assetcenter.service.spi.constants.ExtraKeyConstants;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayRequestConvertor;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodePayRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeRechargeRequest;
import com.youzan.pay.unified.cashier.api.result.QRCodeRechargeResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.intergration.client.CashierRechargeServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.resultcode.errorcode.QRCodePayErrorCode;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version QRCodeRechargeHandler.java, v 0.1 2017-06-19 20:01
 */
@Slf4j
@Component
public class QRCodeRechargeHandler extends AbstractHandler<QRCodeRechargeRequest, QRCodeRechargeResult> {

  @Resource
  private CashierRechargeServiceClient cashierRechargeServiceClient;

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;

  @Override
  protected QRCodeRechargeResult execute(QRCodeRechargeRequest request) throws Exception {

    LogUtils.info(log, "二维码充值,充值开始,request={}", request);

    // ======================支付======================
    // 1.构造支付请求体
    RechargePayRequest rechargePayRequest = contractRechargePayRequest(request);

    rechargePayRequest.getPayDetailInfos().get(0).setExtendInfo(getPayDetailInfo(request));
    // 2.
    Response<RechargePayResult> responseOfRechargePay = cashierRechargeServiceClient
        .pay(rechargePayRequest);

    LogUtils.info(log, "二维码充值,充值rpc结果,responseOfRechargePay={}", responseOfRechargePay);

    QRCodeRechargeResult result = null;

    if (responseOfRechargePay.isSuccess()) {
      result = buildSuccessResult(responseOfRechargePay);
    } else {
      throw new BaseException(QRCodePayErrorCode.PAY_FAIL);
    }

    LogUtils.info(log, "二维码充值,充值结束,result={}", result);

    return result;
  }

  private Map<String,String> getPayDetailInfo(QRCodeRechargeRequest request) {
    Map<String,String> map= Maps.newHashMap();
    String userNo=getUserNo(request.getPayerId());
    map.put(ExtraKeyConstants.BUYER_USER_NO, userNo);
    return map;
  }

  private String getUserNo(String userId){
    if(!isNumeric(userId)){
      return "-1";
    }
    UserMerchantSinleRequest userMerchantSinleRequest=new UserMerchantSinleRequest();
    userMerchantSinleRequest.setUserId(Long.valueOf(userId));
    UserMerchantDto
        userMerchantDto=userMerchantServiceClient.queryMchByUserId(userMerchantSinleRequest);
    if(userMerchantDto==null){
      return "-1";
    }
    return String.valueOf(userMerchantDto.getMerchantId());
  }

  private boolean isNumeric(String str) {
    if(str.isEmpty()){
      return false;
    }
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

  private RechargePayRequest contractRechargePayRequest(QRCodeRechargeRequest request) {
    RechargePayRequest rechargePayRequest = PayRequestConvertor.convert(request);
    return rechargePayRequest;
  }

  private QRCodeRechargeResult buildSuccessResult(Response<RechargePayResult> responseOfRechargePay) {

    RechargePayResult rechargePayResult = responseOfRechargePay.getResult();

    QRCodeRechargeResult result = new QRCodeRechargeResult();

    result.setOutBizNo(rechargePayResult.getOutBizNo());
    result.setAcquireNo(rechargePayResult.getAcquireNo());

    Map<String, Object> deepLinkInfo = rechargePayResult.getPayDetailResult().get(0).getDeepLinkInfo();

    result.setDeepLinkInfo(JSON.toJSONString(deepLinkInfo));

    return result;
  }
}
