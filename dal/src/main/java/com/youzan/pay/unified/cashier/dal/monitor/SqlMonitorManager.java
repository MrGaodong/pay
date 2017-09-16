/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.dal.monitor;

import com.youzan.pay.core.utils.log.LogUtils;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version SqlMonitorManager.java, v 0.1 2017-07-22 16:24
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
                                                                          Object.class}),
             @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                                                                         Object.class,
                                                                         RowBounds.class,
                                                                         ResultHandler.class}),
             @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                                                                         Object.class,
                                                                         RowBounds.class,
                                                                         ResultHandler.class,
                                                                         CacheKey.class,
                                                                         BoundSql.class})})
public class SqlMonitorManager implements Interceptor {

  private boolean showSql = true;

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    if (!showSql) {
      return invocation.proceed();
    }

    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    if (mappedStatement == null) {
      return invocation.proceed();
    }

    String sqlId = mappedStatement.getId();
    Object returnValue = null;
    int resultCode = 0;
    long start = System.currentTimeMillis();
    try {
      returnValue = invocation.proceed();
    } catch (Exception e) {
      resultCode = 1;
      throw e;
    } finally {
      long end = System.currentTimeMillis();
      long time = end - start;

      if (time >= 300) {
        LogUtils.error(log, "sqlId={},code={},sql消耗时间={}", sqlId, resultCode, time);
      } else if (time > 20) {
        LogUtils.warn(log, "sqlId={},code={},sql消耗时间={}", sqlId, resultCode, time);
      } else {
        LogUtils.info(log, "sqlId={},code={},sql消耗时间={}", sqlId, resultCode, time);
      }
    }
    return returnValue;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
    if (properties == null) {
      return;
    }
    if (properties.containsKey("show_sql")) {
      String value = properties.getProperty("show_sql");
      if (Boolean.TRUE.toString().equals(value)) {
        this.showSql = true;
      }
    }
  }
}
