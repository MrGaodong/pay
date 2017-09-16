/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

/**
 * @author twb
 * @version QRCodePayResultEnum.java, v 0.1 2017-07-10 16:01
 */
public enum QRCodePayResultEnum {

  INVALID(0, "二维码已经失效"),
  CAN_PAY(1, "可以支付"),
  USED(2, "二维码已经使用");

  int code;
  String desc;

  QRCodePayResultEnum(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
