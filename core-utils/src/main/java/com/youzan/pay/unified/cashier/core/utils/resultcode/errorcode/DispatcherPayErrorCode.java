package com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode;

import com.youzan.pay.unified.cashier.core.utils.resultcode.ModuleCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.SystemCode;

/**
 * 支付路由模块错误码枚举
 *
 * Created by twb on 17/1/10.
 */
public enum DispatcherPayErrorCode implements ResultCode {

  SYSTEM_ERROR("200", "系统异常"),

  PAY_RESULT_ERROR("201", "支付结果信息错误"),

  ACQUIRE_ORDER_ERROR("202", "查询收单信息异常");

  private String errorCode;

  private String desc;

  DispatcherPayErrorCode(String errorCode, String desc) {
    this.errorCode = errorCode;
    this.desc = desc;
  }

  @Override
  public ModuleCode getModuleCode() {
    return ModuleCode.DISPATCHER_PAY;
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
