/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.annotation.SkipSign;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author twb
 * @version QRCodeCreateOrderRequest.java, v 0.1 2017-06-19 20:51
 */
@Data
public class QRCodeCreateOrderRequest implements Serializable {

  @SkipSign
  private static final long serialVersionUID = 8105758650634806414L;

  private String createTime;
  private String sign;

  private String outBizNo;
  private String partnerId;
  private String mchId;
  private long payAmount;
  private String currencyCode;
  private int bizProd;
  private int bizMode;
  private String bizAction;
  private String tradeDesc;

  private int rechargePayType;
  private String cardNo;
  private String customerId;

  private String partnerReturnUrl;
  private String returnUrl;

  private String payerId;
}
