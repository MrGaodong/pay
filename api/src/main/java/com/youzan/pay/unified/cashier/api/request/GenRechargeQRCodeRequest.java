/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.naming.directory.SearchResult;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @author twb
 * @version GenRechargeQRCodeRequest.java, v 0.1 2017-06-16 11:40
 */
@Data
public class GenRechargeQRCodeRequest implements Serializable {

  private static final long serialVersionUID = 8305027044251293105L;
  /**
   * 合作方ID：在商户平台注册后，分配的一个商户ID
   **/
  @NotNull(message = "合作方ID不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String partnerId;

  /**
   * 收款方帐号,传卖家的商户号
   */
  @NotNull(message = "收款方帐号不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String mchId;

  /**
   * 订单号
   */
  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;

  /**
   * 交易描述
   */
  @NotNull(message = "交易描述不能为空")
  private String tradeDesc;

  /**
   * 支付金额：此支付工具的支付金额
   */
  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount = 0;

  /**
   * 币种：目前默认为人民币
   */
  @NotNull(message = "币种不能为空")
  private String currencyCode = "CNY";


  /**
   * 参与折扣金额
   */
  private long discountableAmount = 0;

  /**
   * 不参与折扣金额
   */
  private long undiscountableAmount = 0;

  /**
   * 商品描述:需要透传给渠道（微信等）
   */
  @NotNull(message = "商品描述不能为空")
  private String goodsDesc;

  /**
   * 支付结果异步通知链接,通知合作方
   */
  private String partnerNotifyUrl;

  /**
   * 支付完成后，前端页面跳转合作方的链接
   */
  private String returnUrl;

  // 一码
  @NotNull(message = "产品码不能为空")
//  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String bizProd;

  // 二码
  @NotNull(message = "业务模式码不能为空")
//  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String bizMode;

  // 三码
  @NotNull(message = "业务流向字码不能为空")
//  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String bizAction;

  // 预付卡充值业务

//  @NotNull(message = "客户号不能为空")
  private String customerId;

//  @NotNull(message = "卡号不能为空")
  private String cardNo;

//  @NotNull(message = "充值类型")
  private int rechargePayType;

  // 0 不需要跳转有赞成功页面
  // 大于0 需要跳转有赞成功页面
  private int isNeedSuccessJump;

  private String mchName;
  private String goodsName;
}
