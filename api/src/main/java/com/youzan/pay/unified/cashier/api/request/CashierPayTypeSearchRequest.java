/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * 支付方式查询请求
 *
 * @author wulonghui
 * @version CashierPayTypeSearchRequest.java, v 0.1 2017-04-12 14:51
 */
@Data
public class CashierPayTypeSearchRequest implements Serializable {

  private static final long serialVersionUID = 8978692770109660229L;

  @NotNull(message = "支付环境不能为空")
  private String payEnviorment;

  /**
   * 合作方ID：在商户平台注册后，分配的一个商户ID
   **/
  @NotNull(message = "合作方ID不能为空")
  private String partnerId;

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
   * 有赞买家账号
   */
  private String payerId;

  /**
   * 有赞买家昵称
   */
  private String payerNickName;

  /**
   * 收款方帐号,传卖家的商户号
   */
  @NotNull(message = "收款方帐号不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String mchId;

  /**
   * 支付金额：此支付工具的支付金额
   */
  @Min(value = 1, message = "支付金额不能小于或等于0")
  private long payAmount = 0;

  /**
   * 币种：目前默认为人民币
   */
  @NotNull(message = "币种不能为空")
  private String currencyCode = "CNY";

  /**
   * 参与折扣金额
   */
  @Min(value = 0, message = "折扣金额不能小于0")
  private long discountableAmount = 0;

  /**
   * 不参与折扣金额
   */
  @Min(value = 0, message = "不打折扣金额不能小于0")
  private long undiscountableAmount = 0;

  /**
   * 资金明细备注信息,此信息将作为支付工具备注
   */
  private String memo;

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
   * app平台类型，如IOS todo 从商户平台获取
   */
  private String appType = "WAP";

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
   * 商家电话号码
   */
  private String tel;

  /**
   * md5签名
   */
  private String sign;
}
