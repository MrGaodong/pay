/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.qrcode;

import com.youzan.pay.unified.cashier.api.QRCodePayService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.FetchQRCodeInfoHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.GenPayQRCodeHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.QRCodeCreatePayOrderHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.QRCodePayHandler;
import com.youzan.pay.unified.cashier.api.request.FetchQRCodeInfoRequest;
import com.youzan.pay.unified.cashier.api.request.GenPayQRCodeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.request.QRCodePayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.api.result.QRCodePayResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 扫码支付服务（二维码）
 *
 * @author twb
 * @version QRCodePayServiceImpl.java, v 0.1 2017-06-01 16:04
 */
@Path("/qrcode")
@Service(protocol = {"rest", "dubbo"}, registry = {"haunt"})
public class QRCodePayServiceImpl implements QRCodePayService {

  @Resource
  private HandlerFactory handlerFactory;

  @Path("/create/pay")
  @POST
  @Override
  public Response<QRCodeCreateOrderResult> create(QRCodeCreateOrderRequest request) {
    QRCodeCreatePayOrderHandler handler = handlerFactory.getHandler(QRCodeCreatePayOrderHandler.class);
    return handler.handle(request);
  }

  @Path("/pay")
  @POST
  @Override
  public Response<QRCodePayResult> pay(QRCodePayRequest request) {
    QRCodePayHandler handler = handlerFactory.getHandler(QRCodePayHandler.class);
    return handler.handle(request);
  }

  @Path("/gen/pay")
  @POST
  @Override
  public Response<QRCodeInfoResult> genPayQRCode(GenPayQRCodeRequest request) {
    GenPayQRCodeHandler handler = handlerFactory.getHandler(GenPayQRCodeHandler.class);
    return handler.handle(request);
  }

  @Path("/fetch/pay")
  @POST
  @Override
  public Response<QRCodeInfo> fetchCodeInfo(FetchQRCodeInfoRequest request) {
    FetchQRCodeInfoHandler handler = handlerFactory.getHandler(FetchQRCodeInfoHandler.class);
    return handler.handle(request);
  }
}
