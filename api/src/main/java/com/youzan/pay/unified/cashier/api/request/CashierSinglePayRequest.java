package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @author: clong
 * @date: 2017-04-14
 */
@Data
public class CashierSinglePayRequest implements Serializable {

  private static final long serialVersionUID = 1288460363857173491L;

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
   * 支付渠道
   *
   * 见: com.youzan.pay.assetcenter.service.enums.PayTool
   */
  @NotNull(message = "支付工具不能为空")
  private String payTool;

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
   * 资金明细备注信息,此信息将作为支付工具备注
   */
  private String memo;

  /**
   * 商品描述:需要透传给渠道（微信等）
   */
  @NotNull(message = "商品描述不能为空")
  private String goodsDesc;

  /**
   * 有赞授权码－包含微信公众号支付用户子标识 todo 从商户平台获取
   */
  private String
      yzAuthToken =
      "e+WLRsarrJiRP79l+YRwOkY9CFLze2uEMIq0Gh4SaBlZcNQqAxLbE2NGfetoroDqmHTLFZ/mgz1yPb5v5hwOSw==";

  /**
   * 支付结果异步通知链接,通知合作方
   */
  private String partnerNotifyUrl;

  /**
   * 支付完成后，前端页面跳转合作方的链接
   */
  private String partnerReturnUrl;

  /**
   * 用户终端ip
   */
  @NotNull
  private String userAgentIp;

  /**
   * app名称 todo 从商户平台获取
   */
  private String appName = "https://trade.koudaitong.com";

  /**
   * app平台类型，如IOS todo 从商户平台获取
   */
  private String appType = "WAP";

  /**
   * 发布包名称 todo 从商户平台获取
   */
  private String distributionPackageName = "有赞H5";

}
