/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.ToString;

/**
 * @author twb
 * @version UnifiedOrderCreatingRequest.java, v 0.1 2017-01-09 21:02
 */
@Data
@ToString(callSuper = true)
public class PayRequest implements RiskIpInfo,Serializable {

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

  private String userAgentIp;

  private String partnerNotifyUrl;

  private String partnerReturnUrl;

  private String authCode;

  private String memo;

  private String wxSubOpenId;

  //强制代销属性
  private Integer wxForceProxy;

  //自有粉丝openid
  private String wxSelfOpenId;
  //大帐号openId
  private String wxYzOpenId;

  //中信openId
  private String wxCiticOpenId;

  // 改价后的价格
  private long newPrice;


  /**
   * app名称 todo 从商户平台获取
   */
  private String appName = "有赞H5";

  /**
   * app平台类型，如IOS todo 从商户平台获取
   */
  private String appType ="WAP";


  /**
   * 发布包名称 todo 从商户平台获取
   */
  private String distributionPackageName ="https://trade.koudaitong.com";

  /**
   *交易扩展字段
   */
  public String bizExt;
  /**
   * 预付卡卡号，暂时只有一张卡
   */
  private String valueCardNo;
}
