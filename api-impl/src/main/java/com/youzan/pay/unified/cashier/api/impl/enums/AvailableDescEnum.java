/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * 支付方式是否可用描述
 *
 * @author wulonghui
 * @version AvailableDescEnum.java, v 0.1 2017-02-28 17:25
 */
public enum AvailableDescEnum {

  BALANCE_INSUFFICIENT("BANLANCE_INSUFFICIENT", "余额不足,剩余"), BALANCE("ECARD_BANLANCE", "剩余");

  /**
   * 不可用英文描述
   */
  @Getter
  private String code;

  /**
   * 中文描述
   */
  @Getter
  private String desc;

  AvailableDescEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
