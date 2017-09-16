/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.exception;

import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;

import lombok.Getter;

/**
 * @author twb
 * @version BaseException.java, v 0.1 2017-01-10 12:59
 */
public class BaseException extends RuntimeException {

  private static final long serialVersionUID = 2100102464536632280L;

  /**
   * 结果码
   */
  @Getter
  private ResultCode resultCode;

  public BaseException() {
    super();
  }

  public BaseException(ResultCode resultCode) {
    super(resultCode.getDesc());
    this.resultCode = resultCode;
  }

  public BaseException(ResultCode resultCode, Throwable throwable) {
    super(resultCode.getDesc(), throwable);
    this.resultCode = resultCode;
  }

  public BaseException(ResultCode resultCode, String errorMessage, Throwable throwable) {
    super(errorMessage, throwable);
    this.resultCode = resultCode;
  }
}
