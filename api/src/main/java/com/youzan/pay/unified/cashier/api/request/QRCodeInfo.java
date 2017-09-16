/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @author twb
 * @version QRCodeInfo.java, v 0.1 2017-06-01 17:54
 */
@Data
public class QRCodeInfo implements Serializable {

  private static final long serialVersionUID = -932576513222446690L;

  // 合作方ID
  private String partnerId;

  // 付款方账号
  private String mchId;

  // 外部订单号
  private String outBizNo;

  // 交易描述
  private String tradeDesc;

  // ======================================

  // 支付金额
  private long payAmount;

  // 参与折扣金额
  private long discountableAmount = 0;

  // 不参与折扣金额
  private long undiscountableAmount = 0;

  // 币种
  private String currencyCode;

  // 商品描述
  private String goodsDesc;

  // 支付结果异步通知合作方
  private String partnerNotifyUrl;

  // 支付完成结果页
  private String returnUrl;

  // ======================================

  // 一码
  private String bizProd;

  // 二码
  private String bizMode;

  // 三码
  private String bizAction;

  // ======================================

  // 预付卡充值业务
  // 客户号
  private String customerId;
  // 卡号
  private String cardNo;
  // 充值类型
  private int rechargePayType;

  private int isNeedSuccessJump;

  private String mchName;
  private String goodsName;
}
