/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * @author wulonghui
 * @version PayToolTypeEnum.java, v 0.1 2017-02-19 18:48
 */
public enum PayToolTypeEnum {

  CASH_ON_DELIVERY("CASH", "CASH_ON_DELIVERY", "货到付款"),
  PEERPAY("PEERPAY", "PEERPAY", "找人代付"),
  WX_JS("WX", "WX_JS", "微信支付"),
  WX_H5("WX", "WX_H5", "微信支付"),
  ALIPAY_WAP("ALIPAY", "ALIPAY_WAP", "支付宝支付"),
  ECARD("ECARD", "ECARD", "有赞E卡"),
  BALANCE("BALANCE", "BALANCE", "余额"),
  GIFT_CARD("GIFT_CARD", "GIFT_CARD", "礼品卡"),
  CREDIT_CARD("BAND", "CREDIT_CARD", "信用卡支付"),
  BAIFUBAO_WAP("BAND", "BAIFUBAO_WAP", "储蓄卡支付"),
  PREPAID_PAY("PREPAID_PAY","PREPAID_PAY","储值余额"),
  VALUE_CARD("VALUE_CARD", "VALUE_CARD", "会员余额"),
  ;

  /**
   * 总体渠道名称
   */
  @Getter
  String channelName;

  /**
   * 渠道英文名称
   */
  @Getter
  String code;

  /**
   * 渠道中文名称
   */
  @Getter
  String desc;

  PayToolTypeEnum(String channelName, String code, String desc) {
    this.channelName = channelName;
    this.code = code;
    this.desc = desc;
  }

  /**
   * 获取总体渠道名称
   */
  public static String getChannel(String code) {
    for (PayToolTypeEnum e : values()) {
      if (e.getCode().equals(code)) {
        return e.getChannelName();
      }
    }
    return null;
  }

  /**
   * 获取支付方式中文名称
   */
  public static String getChannelName(String code) {
    for (PayToolTypeEnum e : values()) {
      if (e.getCode().equals(code)) {
        return e.getDesc();
      }
    }
    return null;
  }

}
