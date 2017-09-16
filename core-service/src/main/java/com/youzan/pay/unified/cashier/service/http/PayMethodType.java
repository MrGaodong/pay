package com.youzan.pay.unified.cashier.service.http;

import lombok.Getter;

/**
 * @author tao.ke Date: 2017/6/23 Time: 上午10:46
 */
public enum PayMethodType {

  UMPAY(1,"默认支付"),EEPAY(2, "易宝支付"), BAIDU_WAP(3, "百度支付");

  @Getter
  private final int code;

  @Getter
  private final String desCription;

  PayMethodType(int code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }
}
