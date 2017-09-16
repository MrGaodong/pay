package com.youzan.pay.unified.cashier.core.utils.resultcode;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by twb on 17/1/10.
 */
public enum SystemCode {

  UNIFIED_CASHIER("1177", "统一收银台系统");

  @Getter
  @Setter
  private String code;

  @Getter
  @Setter
  private String desc;

  SystemCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static SystemCode select(String name) {
    for (SystemCode value : values()) {
      if (Objects.equals(value.name(), name)) {
        return value;
      }
    }
    return null;
  }
}
