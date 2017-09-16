package com.youzan.pay.unified.cashier.api.impl.enums;

import java.util.Objects;

import lombok.Getter;

/**
 * Created by twb on 17/1/9.
 */
public enum PayToolEnum {

  WX_JS("1", "微信公共号支付"),

  ALIPAY_WAP("2", "支付宝WAP支付"),

  ECARD("3", "e卡支付"),

  BALANCE("4", "余额支付"),

  GIFT_CARD("5", "礼品卡支付"),

  CREDIT_CARD("6", "信用卡支付"),

  DEBIT_CARD("7",  "借记卡支付"),

  BAIFUBAO_WAP("8","银行卡WAP支付"),

  WX_H5("9","微信h5支付"),

  VALUE_CARD("10","会员余额"),

  PREPAID_PAY("11","储值余额");
  ;


  @Getter
  private String code;

  @Getter
  private String desc;

  PayToolEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static PayToolEnum getByName(String name) {
    for (PayToolEnum value : values()) {
      if (Objects.equals(value.name(), name)) {
        return value;
      }
    }
    return null;
  }

  /**
   * 判断status是否在指定的Enum范围中
   */
  public boolean isIn(PayToolEnum... payToolEnums) {
    for (PayToolEnum payToolEnum : payToolEnums) {
      if (this.equals(payToolEnum)) {
        return true;
      }
    }
    return false;
  }
  public boolean isEquals(String name) {
    return this.name().equals(name);
  }
}
