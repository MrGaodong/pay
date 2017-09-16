/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request.group.recharge;

import com.youzan.pay.unified.cashier.api.request.TradeInfoRequest;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierReChargeGenUrlRequest.java, v 0.1 2017-06-22 15:36
 */
@Data
public class CashierReChargeGenUrlRequest implements Serializable {
  private String bizAction;
  private int bizProd;
  private String signType;
  private String partnerId;
  private String goodsDesc;
  private long payAmount;
  private String outBizNo;
  private String currencyCode;
  private long mchId;
  private int discountableAmount;
  private int undiscountableAmount;
  private String partnerNotifyUrl;
  private String returnUrl;
  private String memo;
  private int bizMode;
  private String mchName;
  private String goodsName;
  private String tel;
  private String appType;
  private int isNeedSuccessJump;
  private String tradeDesc;
  private String sign;

  private String cardNo;
  private String customerId;
  private int rechargePayType;
}
