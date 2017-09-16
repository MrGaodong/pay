/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.exception;

import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;

import lombok.Getter;

/**
 * 创建收单异常类
 *
 * @author wulonghui
 * @version CreateOrderException.java, v 0.1 2017-01-11 20:20
 */

public class CreateOrderException extends RuntimeException {

  @Getter
  private CreateOrderErrorCode createOrderErrorCode;

  public CreateOrderException() {
    super();
  }

  public CreateOrderException(CreateOrderErrorCode createOrderErrorCode) {
    super(createOrderErrorCode.getDesCription());
    this.createOrderErrorCode = createOrderErrorCode;
  }

  public CreateOrderException(CreateOrderErrorCode createOrderErrorCode, Throwable throwable) {
    super(createOrderErrorCode.getDesCription(), throwable);
    this.createOrderErrorCode = createOrderErrorCode;
  }

  public CreateOrderException(CreateOrderErrorCode createOrderErrorCode, String errorMessage,
                              Throwable throwable) {
    super(errorMessage, throwable);
    this.createOrderErrorCode = createOrderErrorCode;
  }
}
