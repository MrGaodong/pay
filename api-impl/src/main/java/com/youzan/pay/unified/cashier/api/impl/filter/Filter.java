/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.filter;

/**
 * @author wulonghui
 * @version Filter.java, v 0.1 2017-06-15 14:24
 */
public interface Filter<T> {

  //是否被过滤
  boolean doFilter(T request);

}
