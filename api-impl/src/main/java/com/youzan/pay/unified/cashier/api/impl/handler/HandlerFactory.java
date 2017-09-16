/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler;

import com.youzan.pay.core.utils.log.LogUtils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一收银台处理工厂类
 *
 * @author wulonghui
 * @version HandlerFactory.java, v 0.1 2017-01-10 15:43
 */
@Slf4j
@Component
public class HandlerFactory implements BeanPostProcessor {

  /**
   * 处理器容器
   */
  Map<Class, Handler> handlerMap = new ConcurrentHashMap<>();

  public <T> T getHandler(Class<T> clazz) {
    return (T) handlerMap.get(clazz);
  }

  /**
   * 前置处理类
   */
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  /**
   * 将处理器名放入容器内
   */
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof Handler) {
      Class clazz = bean.getClass();
      if (handlerMap.containsKey(clazz)) {
        LogUtils.error(log, "重复的handler定义,name=" + beanName);
      } else {
        handlerMap.put(clazz, (Handler) bean);
        LogUtils.info(log, "Handler实例放入缓存,name={}", beanName);
      }
    }
    return bean;
  }
}
