/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wulonghui
 * @version UnifiedAcquireOrder.java, v 0.1 2017-01-20 15:54
 */
@Data
public class UnifiedAcquireOrder {

  /* 外部订单号 */
  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;

  /* 交易描述不能为空 */
  @NotNull(message = "交易描述不能为空")
  private String tradeDesc;

  /* 收款方账号不能为空 */
  @NotNull(message = "收款方账号不能为空")
  private long mchId;

  /* 支付金额不能为0 */
  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount;

  /* 目前默认为人名币 */
  @NotNull(message = "币种默认为人民币")
  private String currencyCodel;

  /* 业务标识 */
  @NotNull(message = "业务标识不能为空")
  private int bizProd;

  /* 业务模式 */
  @NotNull(message = "业务模式不能为空")
  private int bizMode;

  /* 买家id */
  private String payerId;

  /* 付款方昵称 */
  private String payerNickName;

  /* 参与折扣金额 */
  private long discountableAmount;

  /* 不参与折扣金额 */
  private long undiscountableAmount;

  /* 支付结果异步通知地址 */
  private String partnerNotifyUrl;

  /* 业面跳转地址 */
  private String partnerReturnUrl;

  /**
   * 业务操作类型
   */
  @NotNull(message = "业务操作类型不能为空")
  private String bizAction;

}
