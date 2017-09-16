/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.enums;

import lombok.Getter;

/**
 * @author wulonghui
 * @version CreateStatusEnum.java, v 0.1 2017-01-11 21:03
 */
public enum CreateStatusEnum {

  SUCCESS("SUCCESS", "创建成功"), FAIL("FAIL", "创建失败，不能继续支付"), BUYER_PAIED("BUYER_PAIED", "已经支付完成");

  @Getter
  private final String code;

  @Getter
  private final String desCription;

  CreateStatusEnum(String code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }

  public static CreateStatusEnum getEnumByCode(String code) {
    for (CreateStatusEnum e : values()) {
      if (e.getCode() == code) {
        return e;
      }
    }
    return null;
  }
}
