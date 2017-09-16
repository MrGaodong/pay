/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.enums;

import lombok.Getter;

/**
 * 支付结果url枚举
 *
 * @author wulonghui
 * @version PayResultUrlEnum.java, v 0.1 2017-01-17 15:47
 */
public enum PayResultUrlEnum {

  PAYFINISH("http://qima-inc.com/pay/finish", "支付结果页面");

  @Getter
  private String code;

  @Getter
  private String desCription;

  PayResultUrlEnum(String code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }

}
