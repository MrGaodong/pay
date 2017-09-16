package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.unified.cashier.api.CashierPayService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.DispatcherPayHandler;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.PayResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by twb on 17/1/9.
 */
@Path("/pay")
@Service(protocol = {"rest","dubbo"}, registry = {"haunt"})
@Slf4j
public class CashierPayServiceImpl implements CashierPayService {

  @Resource
  private HandlerFactory handlerFactory;

  @Path("/dispatcher")
  @POST
  @Override
  public Response<PayResult> dispatcherPay(PayRequest request) {

    DispatcherPayHandler
        dispatcherPayHandler =
        handlerFactory.getHandler(DispatcherPayHandler.class);

    Response<PayResult> response = dispatcherPayHandler.handle(request);

    return response;
  }
}
