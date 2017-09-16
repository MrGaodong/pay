/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.CashierCreateAcquireOrderService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5AcquireOrderHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.DispatcherPayHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.UnifiedCashierAcquierOrderHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierH5AcquireOrderResult;
import com.youzan.pay.unified.cashier.api.result.PayResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierCreateAcquireOrderServiceImpl.java, v 0.1 2017-06-02 15:56
 */
@Path("/H5Order")
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Slf4j
public class CashierCreateAcquireOrderServiceImpl implements CashierCreateAcquireOrderService {

  @Resource
  private HandlerFactory handlerFactory;

  @POST
  @Path("/create")
  @Override
  public Response<CashierH5AcquireOrderResult> create(CashierH5AcquireOrderRequest request) {
    LogUtils.info(log, "[H5创建收单]请求数据,requestData={}", request);

    CashierH5AcquireOrderHandler cashierH5AcquireOrderHandler = handlerFactory.getHandler(
        CashierH5AcquireOrderHandler.class);

    Response<CashierH5AcquireOrderResult> resp = cashierH5AcquireOrderHandler.handle(request);

    LogUtils.info(log, "[H5创建收单]响应数据,resp={}", resp);

    return resp;
  }


}
