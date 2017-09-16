package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.CashierSinglePayService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierSinglePayHandler;
import com.youzan.pay.unified.cashier.api.request.CashierSinglePayRequest;
import com.youzan.pay.unified.cashier.api.response.CashierSinglePayResponse;
import com.youzan.pay.unified.cashier.api.response.Response;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: clong
 * @date: 2017-04-14
 */
@Slf4j
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Path("/cashier")
public class CashierSinglePayServiceImpl implements CashierSinglePayService {

  @Resource
  private HandlerFactory handlerFactory;

  @Override
  @POST
  @Path("/singlePay")
  public Response<CashierSinglePayResponse> singlePay(CashierSinglePayRequest request) {

    LogUtils.info(log, "[单次支付调用开始],request:{}", request);

    CashierSinglePayHandler handler = handlerFactory.getHandler(CashierSinglePayHandler.class);

    return handler.handle(request);

  }
}
