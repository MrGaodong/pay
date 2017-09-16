/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.enums;

import lombok.Getter;

/**
 * 支付环境enum类
 *
 * @author wulonghui
 * @version PayEviormentEnum.java, v 0.1 2017-01-12 11:52
 */

public enum PayEviormentEnum {

  WX_JS("WX_JS", "微信JS支付环境"),

  WX_H5("WX_H5", "微信H5支付环境"),

  BROWSER("BROWSER", "普通浏览器环境"),

  ALIPAYWAP("ALIPAYWAP", "支付宝WAP支付环境"),

  QQWAP("QQWAP", "QQWAP支付环境");

  @Getter
  private final String code;

  @Getter
  private final String desCription;

  PayEviormentEnum(String code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }
}
