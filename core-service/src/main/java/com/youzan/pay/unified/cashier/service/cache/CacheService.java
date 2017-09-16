/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.cache;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.core.utils.SerializationUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author twb
 * @version CacheService.java, v 0.1 2017-05-18 15:01
 */
@Slf4j
public class CacheService {

  @Setter
  private JedisPool pool;

  public Jedis getResource() {
    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      return jedis;
    } catch (JedisException e) {
      LogUtils.error(e, log, "Could not get a resource from the pool");
      throw e;
    }
  }

  public <T> T get(String key, Class<T> clazz) {
    byte[] bytesOfKey = key.getBytes();
    return get(bytesOfKey, clazz);
  }

  public <T> T get(byte[] key, Class<T> clazz) {
    Jedis jedis = getResource();
    try {
      byte[] bytesOfValue = jedis.get(key);
      return bytesOfValue != null ? SerializationUtils.toObject(bytesOfValue, clazz) : null;
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }

  public <T> void set(String key, T value, Class<T> clazz) {
    byte[] bytesOfKey = key.getBytes();
    byte[] bytesOfValue = SerializationUtils.toBytes(value, clazz);
    set(bytesOfKey, bytesOfValue);
  }

  public void set(byte[] key, byte[] value) {
    Jedis jedis = getResource();
    try {
      jedis.set(key, value);
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }
}
