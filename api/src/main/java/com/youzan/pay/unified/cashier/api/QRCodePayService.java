/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.FetchQRCodeInfoRequest;
import com.youzan.pay.unified.cashier.api.request.GenPayQRCodeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.request.QRCodePayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.api.result.QRCodePayResult;

/**
 * @author twb
 * @version QRCodePayService.java, v 0.1 2017-05-25 18:02
 */
public interface QRCodePayService {

  Response<QRCodeCreateOrderResult> create(QRCodeCreateOrderRequest request);

  Response<QRCodePayResult> pay(QRCodePayRequest request);

  Response<QRCodeInfoResult> genPayQRCode(GenPayQRCodeRequest request);

  public Response<QRCodeInfo> fetchCodeInfo(FetchQRCodeInfoRequest request);
}
