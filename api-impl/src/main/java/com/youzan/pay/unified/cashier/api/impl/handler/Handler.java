/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler;

import com.youzan.pay.unified.cashier.api.response.Response;

/**
 * @author twb
 * @version Handler.java, v 0.1 2017-01-10 11:16
 */
public interface Handler<T, R> {

  /**
   * T:入参类型 R:返回结果类型
   *
   * @param request 请求对象
   */
  Response<R> handle(T request);
}
