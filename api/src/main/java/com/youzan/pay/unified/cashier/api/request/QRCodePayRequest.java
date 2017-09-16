/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.annotation.SkipSign;
import com.youzan.pay.unified.cashier.api.request.group.WxJSPayGroup;
import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author twb
 * @version QRCodePayRequest.java, v 0.1 2017-06-01 15:06
 */
@Data
public class QRCodePayRequest implements RiskIpInfo,Serializable {

  private static final long serialVersionUID = -2253301166070699739L;

  @NotNull(message = "pay tool cannot be null")
  private String payTool;

  @NotNull(message = "acquire no cannot be null")
  private String acquireNo;

  @NotNull(message = "wxSubOpenId cannot be null", groups = WxJSPayGroup.class)
  private String wxSubOpenId;

  @NotNull(message = "outBizNo cannot be null")
  private String outBizNo;

  @NotNull(message = "mchId cannot be null")
  private String mchId;

  @NotNull(message = "partnerId cannot be null")
  private String partnerId;

  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount;

  private String userAgentIp;

  private long discountableAmount;

  private long undiscountableAmount;

  private String tradeDesc;

  private String partnerNotifyUrl;

  private String partnerReturnUrl;

  private String payerId;

  //自有粉丝openid
  private String wxSelfOpenId;
  //大帐号openId
  private String wxYzOpenId;

  //中信openId
  private String wxCiticOpenId;


}