package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.unified.cashier.api.PayAccountService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.PayAccountQueryMchHandler;
import com.youzan.pay.unified.cashier.api.response.Response;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/20 Time: 下午3:54
 */
@Path("/account")
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Slf4j
public class PayAccountServiceImpl implements PayAccountService {

  @Resource
  private HandlerFactory handlerFactory;

  @Path("/mchid/query")
  @POST
  @Override
  public Response<Long> queryMchIdByBuyerId(Long buyerId) {
    PayAccountQueryMchHandler handler = handlerFactory.getHandler(PayAccountQueryMchHandler.class);
    return handler.handle(buyerId);
  }
}
