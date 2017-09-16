/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.request.model.PayDetailInfo;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.service.spi.constants.ExtraKeyConstants;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.validate.ValidationResult;
import com.youzan.pay.core.utils.validate.ValidationUtils;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayRequestConvertor;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.impl.handler.RiskAbstractHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodePayRequest;
import com.youzan.pay.unified.cashier.api.result.QRCodePayResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.resultcode.errorcode.QRCodePayErrorCode;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version QRCodePayHandler.java, v 0.1 2017-06-01 16:10
 */
@Slf4j
@Component
public class QRCodePayHandler extends RiskAbstractHandler<QRCodePayRequest, QRCodePayResult> {

  @Resource
  private UnifiedPayServiceClient unifiedPayServiceClient;

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;


  @Override
  protected QRCodePayResult execute(QRCodePayRequest request) throws Exception {

    LogUtils.info(log, "二维码支付,支付开始,request={}", request);

    // ======================支付======================
    // 1.构造支付请求体
    MultiPayRequest multiPayRequest = contractMultiPayRequest(request);

    multiPayRequest.getPayDetailInfos().get(0).setExtendInfo(getPayDetailInfo(request));
    // 2.
    Response<MultiPayResult> responseOfMultiPay = unifiedPayServiceClient
        .multiPay(multiPayRequest);

    LogUtils.info(log, "二维码支付,支付rpc结果,responseOfMultiPay={}", responseOfMultiPay);

    QRCodePayResult result = null;

    if (responseOfMultiPay.isSuccess()) {
      result = buildSuccessResult(responseOfMultiPay);
    } else {
      throw new BaseException(QRCodePayErrorCode.PAY_FAIL);
    }

    LogUtils.info(log, "二维码支付,支付结束,result={}", result);

    return result;
  }

  private Map<String,String> getPayDetailInfo(QRCodePayRequest request) {
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

  private MultiPayRequest contractMultiPayRequest(QRCodePayRequest request) {
    MultiPayRequest multiPayRequest = PayRequestConvertor.convert(request);
    return multiPayRequest;
  }

  private QRCodePayResult buildSuccessResult(Response<MultiPayResult> responseOfMultiPay) {

    MultiPayResult multiPayResult = responseOfMultiPay.getResult();

    QRCodePayResult result = new QRCodePayResult();

    result.setOutBizNo(multiPayResult.getOutBizNo());
    result.setAcquireNo(multiPayResult.getAcquireNo());

    Map<String, Object> deepLinkInfo = multiPayResult.getPayDetailResult().get(0).getDeepLinkInfo();

    result.setDeepLinkInfo(JSON.toJSONString(deepLinkInfo));

    return result;
  }
}
