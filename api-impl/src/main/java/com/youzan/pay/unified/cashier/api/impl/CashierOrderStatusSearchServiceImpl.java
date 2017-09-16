/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.CashierOrderStatusSearchService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierOrderStatusSearchHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierSignKeyHandler;
import com.youzan.pay.unified.cashier.api.request.CashierOrderStatusSearchRequest;
import com.youzan.pay.unified.cashier.api.request.CashierSignKeyRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierOrderStatusSearchResult;
import com.youzan.pay.unified.cashier.api.result.CashierSignKeyResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierOrderStatusSearchServiceImpl.java, v 0.1 2017-04-12 16:47
 */
@Path("/pay")
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Slf4j
public class CashierOrderStatusSearchServiceImpl implements CashierOrderStatusSearchService {

  /**
   * 处理器工厂
   */
  @Resource
  private HandlerFactory handlerFactory;

  @Path("/queryOrder")
  @POST
  @Override
  public Response<CashierOrderStatusSearchResult> queryOrder(
      CashierOrderStatusSearchRequest cashierOrderStatusSearchRequest) {
    LogUtils.info(log, "[根据收单号查询支付方式开始],request:{}", cashierOrderStatusSearchRequest);

    CashierOrderStatusSearchHandler
        handler =
        handlerFactory.getHandler(CashierOrderStatusSearchHandler.class);

    return handler.handle(cashierOrderStatusSearchRequest);
  }

  @Path("/querySignKey")
  @POST
  @Override
  public Response<CashierSignKeyResult> querySignKey(CashierSignKeyRequest cashierSignKeyRequest) {
    LogUtils.info(log, "[根据partnerId查询商户秘钥开始],request:{}", cashierSignKeyRequest);

    CashierSignKeyHandler handler = handlerFactory.getHandler(CashierSignKeyHandler.class);

    return handler.handle(cashierSignKeyRequest);
  }

  @GET
  @Path("/heartbeat")
  @Override
  public String heartbeat() {
    return "OK";
  }

}
