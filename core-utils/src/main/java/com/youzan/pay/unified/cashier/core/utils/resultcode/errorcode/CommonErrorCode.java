package com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode;

import com.youzan.pay.unified.cashier.core.utils.resultcode.ModuleCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.SystemCode;

/**
 * 通用模块错误码枚举
 *
 * Created by twb on 17/1/10.
 */
public enum CommonErrorCode implements ResultCode {

  SUCCESS("000", "处理成功"),

  FAIL("001", "处理失败"),

  UNKNOWN_ERROR("002", "未知异常"),

  SYSTEM_ERROR("003", "系统异常"),

  ILLEGAL_ARGUMENTS("004", "非法参数");

  private String errorCode;

  private String desc;

  CommonErrorCode(String errorCode, String desc) {
    this.errorCode = errorCode;
    this.desc = desc;
  }

  @Override
  public ModuleCode getModuleCode() {
    return ModuleCode.COMMON;
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
