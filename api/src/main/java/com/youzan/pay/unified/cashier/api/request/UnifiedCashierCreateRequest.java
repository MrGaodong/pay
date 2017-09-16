/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * 请求收单创建类
 *
 * @author wulonghui
 * @version UnifiedCashierCreateRequest.java, v 0.1 2017-01-10 17:42
 */
@Data
public class UnifiedCashierCreateRequest implements Serializable {

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
   * 收款方帐号,传卖家的商户号
   */
  @NotNull(message = "收款方帐号不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private long mchId;

  /**
   * 支付金额
   */
  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount = 0;

  /**
   * 币种：目前默认为人民币
   */
  @NotNull(message = "币种不能为空")
  private String currencyCode = "CNY";

  /**
   * 业务标识
   *
   * @see
   */
  @NotNull(message = "业务标识不能为空")
  private String bizProd;

  /**
   * 业务模式
   *
   * @see
   */
  @NotNull(message = "业务模式不能为空")
  private String bizMode;

  /**
   * 买家id,传递付款方的帐号
   */
  private String payerId;

  /**
   * 付款方昵称
   */
  private String payerNickName;

  /**
   * 扩展上下文字段
   */
  private Map<String, String> extendInfo = new HashMap<String, String>();

}
