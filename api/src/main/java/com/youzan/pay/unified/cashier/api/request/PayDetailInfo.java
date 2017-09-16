/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * 支付详细信息
 *
 * @author saber
 * @version PayDetailInfo.java, v 0.1 2016-12-21 21:08
 */
@Data
public class PayDetailInfo {

  /**
   * 支付渠道
   *
   * 见: com.youzan.pay.assetcenter.client.enums.PayTool
   */
  @NotNull(message = "支付工具不能为空")
  private String payTool;

  /**
   * 收款方帐号,传卖家的商户号
   */
  @NotNull(message = "收款方帐号不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String mchId;

  /**
   * 支付金额
   */
  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount = 0;

  /**
   * 币种：目前默认为人民币
   */
  // @NotNull(message = "币种不能为空")
  // private String currencyCode = CurrencyCode.CNY.getAlpha();

  /**
   * 参与折扣金额
   */
  @Min(value = 0, message = "参与折扣金额不能小于0")
  private long discountableAmount = 0;

  /**
   * 不参与折扣金额
   */
  @Min(value = 0, message = "不参与折扣金额不能小于0")
  private long undiscountableAmount = 0;

  /**
   * 资金明细备注信息,此信息将作为支付工具备注
   */
  @NotNull(message = "支付备注不能为空")
  private String memo;

  /** -------------外部业务属性------------- */
  /**
   * 支付结果异步通知链接,通知合作方
   */
  private String partnerNotifyUrl;

  /**
   * 支付完成后，前端页面跳转合作方的链接
   */
  private String partnerReturnUrl;

  /**
   * 商品描述:需要透传给渠道（微信等）
   */
  @NotNull(message = "商品描述不能为空")
  private String goodsDesc;

  /**
   * 渠道授权码：微信支付扫码、支付宝扫码 必传。
   */
  private String authCode;

  /**
   * 微信WAP支付：用户子标识
   */
  private String wxSubOpenId;

  /**
   * 支付宝是否安装
   */
  private boolean isAlipayInstalled;

  /**
   * 扩展上下文字段
   */
  private Map<String, String> extendInfo = new HashMap<String, String>();

  /**
   * 外部业务信息:透传给渠道层
   */
  private Map<String, String> outBizInfo = new HashMap<String, String>();

}
