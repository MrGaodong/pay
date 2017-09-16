package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.unified.cashier.api.CashierBankCardService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierBankCardCheckHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierBankCardIndexHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierBankCardListHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.card.CashierBankCardUnbindHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardIndexRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardInfoCheckRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardUnbindRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.BankCardIndexResult;
import com.youzan.pay.unified.cashier.api.result.BankCardInfoCheckResult;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/12 Time: 上午11:55
 */
@Path("/card")
@Service(protocol = {"dubbo", "rest"}, registry = {"haunt"})
@Slf4j
public class CashierBankCardServiceImpl implements CashierBankCardService {

  @Resource
  private HandlerFactory handlerFactory;

  @Path("/index")
  @POST
  @Override
  public Response<BankCardIndexResult> index(BankCardIndexRequest request) {
    CashierBankCardIndexHandler
        handler =
        handlerFactory.getHandler(CashierBankCardIndexHandler.class);
    return handler.handle(request);
  }

  @Path("/list/query")
  @POST
  @Override
  public Response<BankCardListQueryResult> queryBankCardList(BankCardListQueryRequest request) {
    CashierBankCardListHandler
        handler =
        handlerFactory.getHandler(CashierBankCardListHandler.class);
    return handler.handle(request);
  }

  @Path("/info/check")
  @POST
  @Override
  public Response<BankCardInfoCheckResult> queryBankCardCheckInfo(
      BankCardInfoCheckRequest request) {
    CashierBankCardCheckHandler
        handler =
        handlerFactory.getHandler(CashierBankCardCheckHandler.class);
    return handler.handle(request);
  }

  @Path("/unbind")
  @POST
  @Override
  public Response unbindBankCard(BankCardUnbindRequest request) {

    CashierBankCardUnbindHandler
        handler =
        handlerFactory.getHandler(CashierBankCardUnbindHandler.class);
    return handler.handle(request);
  }
}
