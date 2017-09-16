/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.request.BaseCashierRequest;
import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;
import com.youzan.pay.unified.cashier.intergration.client.CashierRechargeServiceClient;
import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import javax.annotation.Resource;

/**
 * @author twb
 * @version AbstractDispatcherPayHandler.java, v 0.1 2017-01-12 16:37
 */
public abstract class AbstractDispatcherPayHandler<T extends RiskIpInfo, R> extends RiskAbstractHandler<T , R> {

  protected static final String ADIUST_PRICE_CODE = "112202003";

  protected static final String REPEATE_PAY_CODE = "112202002";

  @Resource
  protected UnifiedPayServiceClient unifiedPayServiceClient;

  @Resource
  protected CacheService cacheService;

  @Resource
  protected CashierRechargeServiceClient cashierRechargeServiceClient;

  @Resource
  UserMerchantServiceClient userMerchantServiceClient;

  protected String getUserNo(String userId){
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

  public boolean isNumeric(String str) {
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

}
