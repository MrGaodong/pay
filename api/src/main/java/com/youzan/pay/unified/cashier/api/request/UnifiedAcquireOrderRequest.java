/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wulonghui
 * @version UnifiedAcquireOrderRequest.java, v 0.1 2017-01-10 15:01
 *
 *          创建收银台统一收单请求类
 */
@Data
public class UnifiedAcquireOrderRequest implements Serializable {

  private static final long serialVersionUID = 8978692770109660222L;

  /* 外部订单号 */
  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;

  /**
   * 交易支持支付方式
   */
  @NotNull(message = "交易支持支付方式不能为空")
  private String payTools;

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

  private String fromPlatform;

  /**
   * 新交易排除支付方式字段列表
   */
  private String excludePayTools;

  /**
   * 新交易强制代销属性
   */
  private  Integer forceConsignmentMode;

  /**
   * 交易扩展字段
   */
  private String bizExt;


}
