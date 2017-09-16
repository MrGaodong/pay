/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author wulonghui
 * @version GoodsInfoRequest.java, v 0.1 2017-06-24 15:07
 */
@Data
public class GoodsInfoRequest implements Serializable {
  private String acquireNo;
  private String outBizNo;
  private long mchId;
  private String payerId;
  private String partnerId;
  private String tradeDesc;
  private long payAmount;
  private int discountableAmount;
  private int undiscountableAmount;
  private String partnerNotifyUrl;
  private String partnerReturnUrl;
  private String memo;
  private String userAgentIp;
  private String wxSubOpenId;

  private String cardNo;
  private String customerId;
  private int rechargePayType;


  private String account;
  private String mchName;
  private String goodsName;
  private String tel;
  private int isNeedSuccessJump;
  private String imgUrl;
  private String returnUrl;
  private List<PayChannel> payWays= new ArrayList<>();

  //商户自定义图片
  private String imageUrl;

  //收银台窗口滑动文字
  private String slideText;

  //预付卡卡号
  private String valueCardNo;
}
