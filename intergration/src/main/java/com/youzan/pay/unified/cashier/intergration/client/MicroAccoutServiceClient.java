/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.microaccount.prepaidcard.service.AccountService;
import com.youzan.pay.microaccount.prepaidcard.to.AccountPayTO;

import org.springframework.stereotype.Component;

import sun.rmi.runtime.Log;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 卡券平台服务调用
 *
 * @author wulonghui
 * @version MicroAccoutServiceClient.java, v 0.1 2017-08-01 20:58
 */
@Slf4j
@Component
public class MicroAccoutServiceClient {

  @Resource
  private AccountService accountService;

  public AccountPayTO queryAccountTO(long userNo, long mchId) {
    try {
      AccountPayTO accountPayTO = accountService.getPayAccount(userNo, mchId);
      LogUtils.info(log,"[accountPayTO={}]",accountPayTO);
      return accountPayTO;
    } catch (Exception e) {
      LogUtils.warn(log, "[没办法，卡券平台歇菜了,userNo={},mchId={}", userNo, mchId);
      return null;
    }
  }


}
