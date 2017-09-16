/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.assetcenter.api.AcquireOrderService;
import com.youzan.pay.assetcenter.api.request.QueryAcquireRequest;
import com.youzan.pay.assetcenter.api.result.QueryAcquireResult;
import com.youzan.pay.core.utils.log.LogUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 收单状态查询实现类
 *
 * @author wulonghui
 * @version CashierOrderStatusSearchClientImpl.java, v 0.1 2017-04-12 17:04
 */
@Component
@Slf4j
public class CashierOrderStatusSearchClient {

  @Resource
  private AcquireOrderService acquireOrderService;

  public QueryAcquireResult query(QueryAcquireRequest request) {

    LogUtils.info(log, "[收单状态查询请求],request:{}", request);

    QueryAcquireResult queryAcquireResult = new QueryAcquireResult();

    try {
      queryAcquireResult = acquireOrderService.query(request);

      LogUtils.info(log, "[收单查询返回结果],queryAcquireResult:{}", queryAcquireResult);

    } catch (Exception e) {
      LogUtils.error(e, log, "[收单状态查询失败],收单号:{}", request.getAcquireNo());
      throw new RuntimeException("收单状态查询失败");
    }

    if (!queryAcquireResult.isSuccess()) {
      LogUtils.warn(log, "[收单状态返回失败]");
      throw new RuntimeException("收单状态返回失败");
    }

    return queryAcquireResult;
  }
}
