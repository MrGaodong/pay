package com.youzan.pay.unified.cashier.core.utils.resultcode;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by twb on 17/1/10.
 */
public enum ModuleCode {

  COMMON("00", "通用"),

  CREATE_ORDER("01", "创建收单"),

  DISPATCHER_PAY("02", "支付路由"),

  CHANNEL_CARD("03", "渠道银行卡"),
  QRCODE_PAY("04", "扫码支付");

  @Getter
  @Setter
  private String code;

  @Getter
  @Setter
  private String desc;

  ModuleCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static ModuleCode select(String name) {
    for (ModuleCode value : values()) {
      if (Objects.equals(value.name(), name)) {
        return value;
      }
    }
    return null;
  }
}
