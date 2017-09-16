/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service;

import java.lang.reflect.InvocationTargetException;

/**
 * 收银台统一校验接口
 *
 * @author wulonghui
 * @version CashierSignCheck.java, v 0.1 2017-01-20 10:23
 */
public interface CashierSignCheck<T> {

  /**
   * @Param data 验签数据
   * @Param sign 签名数据
   * @Param signType 验签方法
   */
  public String genSign(T data, String sign, String key, String signType)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

}
