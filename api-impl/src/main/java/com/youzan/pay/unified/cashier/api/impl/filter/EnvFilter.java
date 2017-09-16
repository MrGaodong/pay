/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.filter;

/**
 * @author wulonghui
 * @version EnvFilter.java, v 0.1 2017-06-16 10:39
 */
public interface EnvFilter<T,R> {
  boolean doFilter(T t,R r);
}
