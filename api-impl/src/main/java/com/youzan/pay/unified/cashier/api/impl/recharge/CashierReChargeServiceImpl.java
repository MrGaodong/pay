/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.recharge;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.CashierReChargeService;
import com.youzan.pay.unified.cashier.api.impl.recharge.handler.CashierReChargeGenUrlHandler;
import com.youzan.pay.unified.cashier.api.impl.recharge.handler.CashierRechargeGetDataHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierReChargeCreateHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierReChargePayHandler;
import com.youzan.pay.unified.cashier.api.request.CashierReChargePayRequest;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeCreateRequest;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeGetDataRequest;
import com.youzan.pay.unified.cashier.api.request.group.recharge.CashierReChargeGenUrlRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeCreateResult;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeGenUrlResult;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargePayResult;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierRechargeGetDataResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierReChargeServiceImpl.java, v 0.1 2017-06-06 11:07
 */
@Slf4j
@Path("/reCharge")
@Service(protocol = {"dubbo","rest"},registry = {"haunt"})
public class CashierReChargeServiceImpl implements CashierReChargeService {

  @Resource
  private HandlerFactory handlerFactory;

  @Override
  @POST
  @Path("/gen/cashier")
  public Response<CashierReChargeGenUrlResult> genCashier(CashierReChargeGenUrlRequest cashierRechargeCreateRequest) {
    LogUtils.info(log, "[创建充值收银台链接],request:{}", cashierRechargeCreateRequest);

    CashierReChargeGenUrlHandler handler = handlerFactory.getHandler(CashierReChargeGenUrlHandler.class);

    return handler.handle(cashierRechargeCreateRequest);
  }
  @Override
  @POST
  @Path("/create")
  public Response<CashierReChargeCreateResult> create(CashierRechargeCreateRequest cashierRechargeCreateRequest) {
    LogUtils.info(log, "[预付卡创建充值单],request:{}", cashierRechargeCreateRequest);

    CashierReChargeCreateHandler handler = handlerFactory.getHandler(CashierReChargeCreateHandler.class);

    return handler.handle(cashierRechargeCreateRequest);
  }


  @Override
  @POST
  @Path("/get/rechargeData")
  public Response<CashierRechargeGetDataResult> getRechargeDataFromRedis(
      CashierRechargeGetDataRequest cashierRechargeGetDataRequest) {
    LogUtils.info(log, "[redis获取充值支付数据],request:{}", cashierRechargeGetDataRequest);

    CashierRechargeGetDataHandler handler = handlerFactory.getHandler(CashierRechargeGetDataHandler.class);

    return handler.handle(cashierRechargeGetDataRequest);  }

  @Override
  @POST
  @Path("/pay")
  public Response<CashierReChargePayResult> recharge(CashierReChargePayRequest cashierReChargePayRequest) {
    LogUtils.info(log, "[预付卡充值],request:{}", cashierReChargePayRequest);

    CashierReChargePayHandler handler = handlerFactory.getHandler(CashierReChargePayHandler.class);

    return handler.handle(cashierReChargePayRequest);
  }

}
