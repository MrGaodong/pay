/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * 币种枚举
 *
 * @author wulonghui
 * @version CurrencyEnum.java, v 0.1 2017-02-28 17:52
 */
public enum CurrencyEnum {

  /**
   * 元
   */
  RMB_YUAN("YUAN", "￥");

  /**
   * 英文描述
   */
  @Getter
  private String code;

  /**
   * 币种中文描述
   */
  @Getter
  private String desc;

  CurrencyEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

}
