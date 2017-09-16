/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.cache;

import com.youzan.platform.cache.CacheTemplate;

import javax.annotation.Resource;

/**
 * @author twb
 * @version SerialidGenerator.java, v 0.1 2017-06-05 19:59
 */
public class SerialidGenerator {

  @Resource
  private CacheTemplate serialCacheTemplate;

  public Long getLongValue() {
    return serialCacheTemplate.serialid();
  }

  public String getStringValue() {
    return String.valueOf(getLongValue());
  }
}
