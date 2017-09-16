/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * @author twb
 * @version PageOperationEnum.java, v 0.1 2017-01-19 17:51
 */
public enum PageOperationEnum {

  CALL_CASHIER("1", "唤起三方收银台"), ADJUST_PRICE("2", "是否同意价格调整"), REPEATE_PAY("3", "重复支付");

  @Getter
  private String code;

  @Getter
  private String desc;

  PageOperationEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
