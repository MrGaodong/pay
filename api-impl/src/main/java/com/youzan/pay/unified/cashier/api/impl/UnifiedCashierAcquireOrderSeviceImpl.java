/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.UnifiedCashierAcquireOrderService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.UnifiedCashierAcquierOrderHandler;
import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.UnifiedAcquireOrderResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version UnifiedCashierAcquireOrderSeviceImpl.java, v 0.1 2017-01-10 15:39
 *
 *          统一收银台收单实现类
 */
@Slf4j
@Path("/acquireOrder")
@Service(protocol = {"dubbo", "rest"})
public class UnifiedCashierAcquireOrderSeviceImpl implements UnifiedCashierAcquireOrderService {

  @Resource
  private HandlerFactory handlerFactory;

  /**
   * 统一收银台创建收单
   */
  @Override
  @POST
  @Path("/create")
  public Response<UnifiedAcquireOrderResult> create(UnifiedAcquireOrderRequest request) {

    LogUtils.info(log, "[创建收单]请求数据,requestData={}", request);

    UnifiedCashierAcquierOrderHandler unifiedHandler = handlerFactory
        .getHandler(UnifiedCashierAcquierOrderHandler.class);

    Response<UnifiedAcquireOrderResult> resp = unifiedHandler.handle(request);

    LogUtils.info(log, "[创建收单]响应数据,resp={}", resp);

    return resp;
  }

  @Override
  @GET
  @Path("/heatbeat")
  public String heatbeat() {
    return "OK";
  }
}
