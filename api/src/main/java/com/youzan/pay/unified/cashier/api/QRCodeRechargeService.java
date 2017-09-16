/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.FetchQRCodeInfoRequest;
import com.youzan.pay.unified.cashier.api.request.GenRechargeQRCodeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.request.QRCodeRechargeRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeRechargeResult;

/**
 * @author twb
 * @version QRCodeRechargeService.java, v 0.1 2017-06-16 10:41
 */
public interface QRCodeRechargeService {

  public Response<QRCodeCreateOrderResult> create(QRCodeCreateOrderRequest request);

  public Response<QRCodeRechargeResult> recharge(QRCodeRechargeRequest request);

  public Response<QRCodeInfoResult> genPayQRCode(GenRechargeQRCodeRequest request);

  public Response<QRCodeInfo> fetchCodeInfo(FetchQRCodeInfoRequest request);
}
