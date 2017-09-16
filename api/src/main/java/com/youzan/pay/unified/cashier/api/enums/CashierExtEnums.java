/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.enums;

import lombok.Getter;

/**
 * 扩展字段enum
 *
 * @author wulonghui
 * @version CashierExtEnums.java, v 0.1 2017-01-10 19:06
 */
public enum CashierExtEnums {

  DCTAMOUNT("discountableAmount", "参与折扣金额"), UDTAMOUNT("undiscountableAmount",
                                                       "不参与折扣金额"), NOTIFYURL("partnerNotifyUrl",
                                                                             "商户通知地址"), RETURNURL(
      "partnerReturnUrl", "结果页面");

  @Getter
  private final String code;

  @Getter
  private final String desCription;

  CashierExtEnums(String code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }

}
