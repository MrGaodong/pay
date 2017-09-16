/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * 交易不支持的支付方式枚举
 *
 * @author wulonghui
 * @version TradePayToolTypeExcludeEnum.java, v 0.1 2017-04-24 16:10
 */
public enum TradePayToolTypeExcludeEnum {

  WX("WXPAY", "微信支付"),
  ALIPAY("ALIPAY", "支付宝支付"),
  ECARD("ECARD", "有赞E卡"),
  BALANCE("BALANCE", "余额"),
  GIFT_CARD("GIFT_CARD", "礼品卡"),
  PREPAID_PAY("PREPAID_PAY", "储值卡支付"),
  VALUE_CARD("VALUE_CARD", "会员余额");

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

  TradePayToolTypeExcludeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

}
