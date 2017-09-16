/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.assetcenter.api.UnifiedOrderService;
import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * dubbo调用统一创建收单服务实现
 *
 * @author wulonghui
 * @version UnifiedOrderServiceClientImpl.java, v 0.1 2017-01-13 13:21
 */
@Component
@Slf4j
public class UnifiedOrderServiceClient {

  /**
   * 调用创建收单服务
   */
  @Resource
  private UnifiedOrderService unifiedOrderService;

  public Response<UnifiedOrderCreatingResult> create(
      UnifiedOrderCreatingRequest unifiedOrderCreatingRequest) {

    Response<UnifiedOrderCreatingResult> response = new Response<>();

    try {
      response = unifiedOrderService.create(unifiedOrderCreatingRequest);
      LogUtils.info(log, "[收单服务返回结果response]:{}", response);
    } catch (Exception e) {
      LogUtils.error(log, "[调用收单服务失败]:{}", e);
      throw new RuntimeException("调用收单服务失败");
    }

    if (!response.isSuccess() || response.getResult() == null) {
      LogUtils.error(log, "[收单服务返回结果为空]");
      throw new RuntimeException("[收单服务返回结果为空]");
    }

    return response;
  }
}
