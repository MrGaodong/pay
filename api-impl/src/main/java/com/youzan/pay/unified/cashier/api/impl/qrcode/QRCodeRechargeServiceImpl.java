/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.qrcode;

import com.youzan.pay.unified.cashier.api.QRCodeRechargeService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.FetchQRCodeInfoHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.GenRechargeQRCodeHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.QRCodeCreateRechargeOrderHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.QRCodeRechargeHandler;
import com.youzan.pay.unified.cashier.api.request.FetchQRCodeInfoRequest;
import com.youzan.pay.unified.cashier.api.request.GenRechargeQRCodeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeRechargeRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeRechargeResult;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 扫码充值服务
 *
 * @author twb
 * @version QRCodeRechargeServiceImpl.java, v 0.1 2017-06-16 10:41
 */
@Path("/qrcode")
@Service(protocol = {"rest", "dubbo"}, registry = {"haunt"})
public class QRCodeRechargeServiceImpl implements QRCodeRechargeService {

  @Resource
  private HandlerFactory handlerFactory;

  @Path("/create/recharge")
  @POST
  @Override
  public Response<QRCodeCreateOrderResult> create(QRCodeCreateOrderRequest request) {
    QRCodeCreateRechargeOrderHandler
        handler = handlerFactory.getHandler(QRCodeCreateRechargeOrderHandler.class);
    return handler.handle(request);
  }

  @Path("/recharge")
  @POST
  @Override
  public Response<QRCodeRechargeResult> recharge(QRCodeRechargeRequest request) {
    QRCodeRechargeHandler handler = handlerFactory.getHandler(QRCodeRechargeHandler.class);
    return handler.handle(request);
  }

  @Path("/gen/recharge")
  @POST
  @Override
  public Response<QRCodeInfoResult> genPayQRCode(GenRechargeQRCodeRequest request) {
    GenRechargeQRCodeHandler handler = handlerFactory.getHandler(GenRechargeQRCodeHandler.class);
    return handler.handle(request);
  }

  @Path("/fetch")
  @POST
  @Override
  public Response<QRCodeInfo> fetchCodeInfo(FetchQRCodeInfoRequest request) {
    FetchQRCodeInfoHandler handler = handlerFactory.getHandler(FetchQRCodeInfoHandler.class);
    return handler.handle(request);
  }
}
