/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.cache;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version PayToolsSortingCache.java, v 0.1 2017-06-06 19:59
 */
@Slf4j
@Data
@Component
public class PayToolsSortingCache {

  private Map<String, Integer> cacheMap = new TreeMap<>();

  public Map<String, Integer> getCacheMap() {
    return cacheMap;
  }
}
