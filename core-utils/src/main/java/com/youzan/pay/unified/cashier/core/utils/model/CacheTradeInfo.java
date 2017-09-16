/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CacheTradeInfo.java, v 0.1 2017-06-20 00:24
 */
@Data
public class CacheTradeInfo implements Serializable{

  private static final long serialVersionUID = -6583065441095573778L;

  private String bizAction;
  private Integer bizProd;
  private String signType;
  private String partnerId;
  private String goodsDesc;
  private long payAmount;
  private String outBizNo;
  private String currencyCode;
  private long mchId;
  private Integer discountableAmount;
  private Integer undiscountableAmount;
  private String partnerNotifyUrl;
  private String returnUrl;
  private String memo;
  private Integer bizMode;
  private String mchName;
  private String goodsName;
  private String tel;
  private String appType;
  private Integer isNeedSuccessJump;
  private String tradeDesc;
  private String sign;

  //支付宝或微信一种支付方式的时候路由选择收银台页面
  private Integer isNeedPopupView;

  //预付卡客户号
  private String customerId;

  //商户自定义图片
  private String imageUrl;

  //收银台窗口滑动文字
  private String slideText;

  //预付卡卡号
  private String valueCardNo;

}
