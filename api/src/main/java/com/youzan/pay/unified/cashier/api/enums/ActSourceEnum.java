/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.enums;

import lombok.Getter;

/**
 * 调用assetcenter Enum
 *
 * @author wulonghui
 * @version ActSourceEnum.java, v 0.1 2017-01-11 15:52
 */
public enum ActSourceEnum {

  SUCCESS("success", "调用成功"), FAIL("fail", "调用失败");

  @Getter
  private final String code;

  @Getter
  private final String desCription;

  ActSourceEnum(String code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }
}
