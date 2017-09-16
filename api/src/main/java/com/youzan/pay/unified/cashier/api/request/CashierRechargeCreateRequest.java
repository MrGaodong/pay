/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierRechargeCreateRequest.java, v 0.1 2017-06-06 15:06
 */
@Data
public class CashierRechargeCreateRequest implements Serializable{

  private static final long serialVersionUID = 89786927701L;


  /*外部订单号*/
  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;


  /*交易描述不能为空*/
  @NotNull(message = "交易描述不能为空")
  private String tradeDesc;

  /*收款方账号不能为空*/
  @NotNull(message = "收款方账号不能为空")
  private long mchId;

  /*支付金额不能为0*/
  @Min(value = 1, message = "支付金额不能小于0")
  private long payAmount;

  /*目前默认为人名币*/
  @NotNull(message = "币种默认为人民币")
  private String currencyCode;

  /*业务标识*/
  @NotNull(message = "业务标识不能为空")
  private int bizProd;


  /*业务模式*/
  @NotNull(message = "业务模式不能为空")
  private int bizMode;

  /*买家id*/
  private String payerId;

  /*付款方昵称*/
  private String payerNickName= "";

  /*参与折扣金额*/
  private long discountableAmount;

  /*不参与折扣金额*/
  private long undiscountableAmount;

  /*支付结果异步通知地址*/
  private String partnerNotifyUrl;

  /*业面跳转地址*/
  private String partnerReturnUrl;

  /**
   * 合作方id
   */
  @NotNull(message = "合作方id不能为空")
  private String partnerId;

  /**
   * 业务操作类型
   */
  @NotNull(message = "业务操作类型不能为空")
  private String bizAction;

  @NotNull(message = "签名不能为空")
  private String sign;

  @NotNull(message = "签名类型不能为空")
  private String signType;


  /**
   * app平台类型，如IOS todo 从商户平台获取
   */
  private String appType ="WAP";

  /**
   * 支付完成同步跳转商户地址
   */
  private String returnUrl;

  /**
   * 卖方商家名称
   */
  private String mchName;

  /**
   * 商品名称
   */
  private String goodsName;

  /**
   * 商品描述
   */
  private String goodsDesc;

  /**
   * 商家电话号码
   */
  private String tel;

  private int isNeedSuccessJump;

  private String cardNo;

  private String customerId;

  private int rechargePayType;
}
