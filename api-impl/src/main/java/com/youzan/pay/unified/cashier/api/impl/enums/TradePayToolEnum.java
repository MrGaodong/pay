/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * 交易支持支付方式enum
 *
 * @author wulonghui
 * @version TradePayToolEnum.java, v 0.1 2017-02-19 18:40
 */
public enum TradePayToolEnum {

  CASH_ON_DELIVERY("CASH_ON_DELIVERY", "货到付款"), NOW_PAY("NOW_PAY", "线付"), PEER_PAY("PEER_PAY",
                                                                                   "代付");

  @Getter
  String code;

  @Getter
  String desc;

  TradePayToolEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
