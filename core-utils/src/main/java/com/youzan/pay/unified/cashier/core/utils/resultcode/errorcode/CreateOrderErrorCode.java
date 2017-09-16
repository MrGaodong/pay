/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode;

import com.youzan.pay.unified.cashier.core.utils.resultcode.ModuleCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.SystemCode;

import lombok.Getter;

/**
 * 创建收单错误码
 *
 * @author wulonghui
 * @version CreateOrderErrorCode.java, v 0.1 2017-01-11 20:16
 */

public enum CreateOrderErrorCode implements ResultCode {

  SYSTEM_ERROR("101", "系统错误"), PAYFINISH("102", "支付已经成功，不要继续操作"), SIGNERROR("103",
                                                                            "签名错误"), CREATEFAIL(
      "104",
      "创建收单失败"), KEYFAIL("105", "商户秘钥为空或查询失败"), SEARCHPAYTOOLSFAIL("106", "商户平台查询支付方式失败");

  @Getter
  private final String code;

  @Getter
  private final String desCription;

  CreateOrderErrorCode(String code, String desCription) {
    this.code = code;
    this.desCription = desCription;
  }

  @Override
  public ModuleCode getModuleCode() {
    return ModuleCode.CREATE_ORDER;
  }

  @Override
  public String getCode() {
    return ResultCode.build(SystemCode.UNIFIED_CASHIER, getModuleCode(), this.code);
  }

  @Override
  public String getDesc() {
    return this.desCription;
  }

}
