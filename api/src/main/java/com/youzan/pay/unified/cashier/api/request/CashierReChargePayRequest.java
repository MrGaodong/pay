/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierReChargePayRequest.java, v 0.1 2017-06-06 11:55
 */
@Data
public class CashierReChargePayRequest implements RiskIpInfo,Serializable{
  @NotNull(message = "支付方式不能为空")
  private String payTool;

  @NotNull(message = "收单流水号不能为空")
  private String acquireNo;

  private int acceptPrice;

  @NotNull(message = "买家帐号不能为空")
  private String payerId;

  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;

  @NotNull(message = "收款方帐号不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private String mchId;

  @NotNull(message = "合作方ID不能为空")
  private String partnerId;

  @NotNull(message = "商品描述不能为空")
  private String tradeDesc;

  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount = 0;

  @Min(value = 0, message = "参与折扣金额不能小于0")
  private long discountableAmount = 0;

  @Min(value = 0, message = "不参与折扣金额不能小于0")
  private long undiscountableAmount = 0;

  private String partnerNotifyUrl;

  private String partnerReturnUrl;

  private String authCode;

  private String memo;

  private String wxSubOpenId;

  // 改价后的价格
  private long newPrice;

  private String userAgentIp;

  /**
   * app名称 todo 从商户平台获取
   */
  private String appName = "https://trade.koudaitong.com";

  /**
   * app平台类型，如IOS todo 从商户平台获取
   */
  private String appType ="WAP";


  /**
   * 发布包名称 todo 从商户平台获取
   */
  private String distributionPackageName ="有赞H5";

  private String cardNo;

  private String customerId;

  private int rechargePayType;


  //自有粉丝openid
  private String wxSelfOpenId;
  //大帐号openId
  private String wxYzOpenId;

  //中信openId
  private String wxCiticOpenId;


}