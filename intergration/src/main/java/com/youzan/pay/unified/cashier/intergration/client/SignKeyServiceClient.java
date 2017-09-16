/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.SignKeyService;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.customer.api.result.QuerySignKeyResult;
import com.youzan.platform.util.lang.StringUtil;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 */
@Component
@Slf4j
public class SignKeyServiceClient {

  @Resource
  private SignKeyService signKeyService;

  public String getSignKey(QuerySignKeyRequest querySignKeyRequest) throws Exception {

    LogUtils.info(log, "querySignKeyRequest:{}", querySignKeyRequest);

    QuerySignKeyResult querySignKeyResult = null;

    try {
      querySignKeyResult = signKeyService.querySignKey(querySignKeyRequest);
    } catch (Exception e) {
      LogUtils.error(log, "调用signKeyService失败", e);
      throw new RuntimeException("调用signKeyService失败", e);
    }

    LogUtils.info(log, "signKeyService调用返回结果集:{}", querySignKeyResult);

    if (StringUtil.isBlank(querySignKeyResult.getSignKey()) || !querySignKeyResult.isSuccess()) {
      LogUtils.error(log, "[signKeyService查询key为空]");
      throw new RuntimeException("[商户秘钥为空]");
    }

    return querySignKeyResult.getSignKey();
  }

}
