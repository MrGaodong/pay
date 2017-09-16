package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.unified.cashier.api.CashierBankCardPayService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierBindPayConfirmHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierBindPrepayHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierSigningPayConfirmHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierSigningPrepayHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierSmsSendHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardPaySmsSendRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardPrepayRequest;
import com.youzan.pay.unified.cashier.api.request.SigningConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.SigningPrepayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.api.result.SigningPrepayResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 下午8:00
 */
@Path("/card")
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Slf4j
public class CashierBankCardPayServiceImpl implements CashierBankCardPayService {

  @Resource
  private HandlerFactory handlerFactory;

  @Path("/bind/prepay")
  @POST
  @Override
  public Response<BindCardPrepayResult> prepayForBindCardPay(BindCardPrepayRequest request) {

    CashierBindPrepayHandler handler = handlerFactory.getHandler(CashierBindPrepayHandler.class);
    return handler.handle(request);
  }

  @Path("/bind/confirm")
  @POST
  @Override
  public Response<ConfirmPayResult> confirmForBindCardPay(BindCardConfirmPayRequest request) {

    CashierBindPayConfirmHandler handler = handlerFactory.getHandler(CashierBindPayConfirmHandler.class);
    return handler.handle(request);
  }

  @Path("/sms/send")
  @POST
  @Override
  public Response sendSms(BankCardPaySmsSendRequest request) {

    CashierSmsSendHandler handler = handlerFactory.getHandler(CashierSmsSendHandler.class);
    return handler.handle(request);
  }

  @Path("/signing/prepay")
  @POST
  @Override
  public Response<SigningPrepayResult> prepayForSigningPay(SigningPrepayRequest request) {

    CashierSigningPrepayHandler handler = handlerFactory.getHandler(CashierSigningPrepayHandler.class);
    return handler.handle(request);
  }

  @Path("/signing/confirm")
  @POST
  @Override
  public Response<ConfirmPayResult> confirmForSigningPay(SigningConfirmPayRequest request) {

    CashierSigningPayConfirmHandler handler = handlerFactory.getHandler(CashierSigningPayConfirmHandler.class);
    return handler.handle(request);
  }
}
