/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.request.QueryAcquireRequest;
import com.youzan.pay.assetcenter.api.result.QueryAcquireResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.convertor.CashierOrderStatusSearchResultConvertor;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierOrderStatusSearchRequest;
import com.youzan.pay.unified.cashier.api.result.CashierOrderStatusSearchResult;
import com.youzan.pay.unified.cashier.intergration.client.CashierOrderStatusSearchClient;

import org.springframework.stereotype.Component;

import sun.rmi.runtime.Log;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierOrderStatusSearchHandler.java, v 0.1 2017-04-12 16:59
 */

@Component
@Slf4j
public class CashierOrderStatusSearchHandler
    extends AbstractHandler<CashierOrderStatusSearchRequest, CashierOrderStatusSearchResult> {

  @Resource
  private CashierOrderStatusSearchClient cashierOrderStatusSearchClient;

  @Override
  protected CashierOrderStatusSearchResult execute(CashierOrderStatusSearchRequest request) {

    LogUtils.info(log, "[收单状态查询接口开始],request:{}", request);
    /**
     * 1.订单状态查询
     */
    QueryAcquireResult queryAcquireResult = getQueryAcquireResult(request);

    if(queryAcquireResult==null){
      LogUtils.warn(log,"[根据收单号查询订单状态空,AcquireNo=]:{}",request.getAcquireNo());
      return new CashierOrderStatusSearchResult();
    }
    /**
     * 2.返回订单查询结果
     */
    return CashierOrderStatusSearchResultConvertor.convert(queryAcquireResult);

  }

  private QueryAcquireResult getQueryAcquireResult(CashierOrderStatusSearchRequest request) {
    QueryAcquireRequest queryAcquireRequest = new QueryAcquireRequest();
    queryAcquireRequest.setAcquireNo(request.getAcquireNo());
    queryAcquireRequest.setOutBizNo(request.getOutBizNo());
    return cashierOrderStatusSearchClient.query(queryAcquireRequest);
  }
}
