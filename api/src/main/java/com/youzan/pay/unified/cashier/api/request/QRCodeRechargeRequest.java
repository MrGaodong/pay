/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.request.group.WxJSPayGroup;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author twb
 * @version QRCodeRechargeRequest.java, v 0.1 2017-06-16 11:38
 */
@Data
public class QRCodeRechargeRequest implements Serializable {

  private static final long serialVersionUID = -5014165677387534194L;

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

  private long discountableAmount;

  private long undiscountableAmount;

  private String tradeDesc;

  private String partnerNotifyUrl;

  private String partnerReturnUrl;

  private int rechargePayType;

//  @NotNull(message = "cardNo cannot be null")
  private String cardNo;

//  @NotNull(message = "customerId cannot be null")
  private String customerId;

  private String payerId;

  //自有粉丝openid
  private String wxSelfOpenId;
  //大帐号openId
  private String wxYzOpenId;

  //中信openId
  private String wxCiticOpenId;

  private String userAgentIp;

}
