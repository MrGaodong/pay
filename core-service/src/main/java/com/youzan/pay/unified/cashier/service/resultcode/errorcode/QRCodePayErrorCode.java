/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.resultcode.errorcode;


import com.youzan.pay.unified.cashier.core.utils.resultcode.ModuleCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.SystemCode;

/**
 * @author twb
 * @version QRCodePayErrorCode.java, v 0.1 2017-06-05 15:20
 */
public enum QRCodePayErrorCode implements ResultCode {

  PAY_FAIL("300", "支付失败"),
  ILLEGAL_STATUS("301", "不合法的支付状态"),
  ERROR_SIGN("302", "签名错误"),
  DISABLED("303", "二维码已经失效"),
  NO_CACHE_ERROR("304", "没有相应的缓存");

  private String errorCode;

  private String desc;

  QRCodePayErrorCode(String errorCode, String desc) {
    this.errorCode = errorCode;
    this.desc = desc;
  }

  @Override
  public ModuleCode getModuleCode() {
    return ModuleCode.QRCODE_PAY;
  }

  @Override
  public String getCode() {
    return ResultCode.build(SystemCode.UNIFIED_CASHIER, getModuleCode(), this.errorCode);
  }

  @Override
  public String getDesc() {
    return this.desc;
  }
}
