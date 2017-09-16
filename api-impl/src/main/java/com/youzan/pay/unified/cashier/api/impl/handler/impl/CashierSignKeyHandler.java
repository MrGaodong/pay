/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierSignKeyRequest;
import com.youzan.pay.unified.cashier.api.result.CashierSignKeyResult;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 商户秘钥查询处理类
 *
 * @author wulonghui
 * @version CashierSignKeyHandler.java, v 0.1 2017-05-04 11:46
 */
@Slf4j
@Component
public class CashierSignKeyHandler
    extends AbstractHandler<CashierSignKeyRequest, CashierSignKeyResult> {

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  @Override
  protected CashierSignKeyResult execute(CashierSignKeyRequest request) throws Exception {
    LogUtils.info(log, "[查询商户秘钥handler开始],request:{}", request);

    String signKey = getSignKeyByPartnerId(request);

    return buildCashierSignKeyResult(signKey);

  }

  private CashierSignKeyResult buildCashierSignKeyResult(String signKey) {
    CashierSignKeyResult cashierSignKeyResult = new CashierSignKeyResult();
    cashierSignKeyResult.setSignKey(signKey);
    return cashierSignKeyResult;
  }

  private String getSignKeyByPartnerId(CashierSignKeyRequest request) throws Exception {
    QuerySignKeyRequest querySignKeyRequest = new QuerySignKeyRequest();
    querySignKeyRequest.setPartnerId(Long.valueOf(request.getPartnerId()));
    return signKeyServiceClient.getSignKey(querySignKeyRequest);
  }
}
