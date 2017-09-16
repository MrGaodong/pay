/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.CashierPayTypeSearchService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5SearchPayToolsHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierPayTypeSearchHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.CashierPayTypeSearchRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierH5SearchPayToolsResult;
import com.youzan.pay.unified.cashier.api.result.CashierPayTypeSearchResult;

import com.alibaba.dubbo.config.annotation.Service;

import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierPayTypeSearchServiceImpl.java, v 0.1 2017-04-12 15:18
 */

@Slf4j
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Path("/paytools")
public class CashierPayTypeSearchServiceImpl implements CashierPayTypeSearchService {

  /**
   * 处理器工厂
   */
  @Resource
  private HandlerFactory handlerFactory;

  /**
   * 根据partnerId查询支付方式
   */
  @POST
  @Path("/search")
  @Override
  public Response<CashierPayTypeSearchResult> payTypeSearch(
      CashierPayTypeSearchRequest cashierPayTypeSearchRequest) {

    LogUtils.info(log, "[根据partnerId查询支付方式开始],request:{}", cashierPayTypeSearchRequest);

    CashierPayTypeSearchHandler
        handler =
        handlerFactory.getHandler(CashierPayTypeSearchHandler.class);

    Response<CashierPayTypeSearchResult> resp = handler.handle(cashierPayTypeSearchRequest);

    return resp;
  }

  @POST
  @Path("/searchForH5")
  @Override
  public Response<CashierH5SearchPayToolsResult> payTypeSearchForH5(
      CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest) {

    LogUtils.info(log, "[根据partnerId查询支付方式开始],request:{}", cashierH5SearchPayToolsRequest);

    CashierH5SearchPayToolsHandler handler = handlerFactory.getHandler(CashierH5SearchPayToolsHandler.class);

    Response<CashierH5SearchPayToolsResult> resp = handler.handle(cashierH5SearchPayToolsRequest);

    LogUtils.info(log,"[h5查询支付方式结果集合],resp:{}",resp);

    return resp;
  }

}
