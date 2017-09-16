package com.youzan.pay.unified.cashier.core.utils.resultcode;

/**
 * Created by twb on 17/1/10.
 */
public interface ResultCode {

  ModuleCode getModuleCode();

  String getCode();

  String getDesc();

  static String build(SystemCode systemCode, ModuleCode moduleCode, String errorCode) {
    return systemCode.getCode() + moduleCode.getCode() + errorCode;
  }
}
