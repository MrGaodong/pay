/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.platform.util.lang.StringUtil;
import com.youzan.platform.util.security.MD5;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 签名检查类
 *
 * @author wulonghui
 * @version SignCheckUtils.java, v 0.1 2017-02-08 18:10
 */
@Slf4j
public class SignCheckUtils<T> {

  public static <T> boolean check(T data, String sign, String key, String signType)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    Class clazz = data.getClass();

    Field[] fields = clazz.getDeclaredFields();

    List<String> list = getFieldsList(data, fields, clazz);

    String sign_result = getSign(list, key, data, clazz);

    if (sign.equals(sign_result)) {
      return true;
    }
    return false;
  }

  private static <T> List<String> getFieldsList(T data, Field[] fields, Class<T> clazz)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    List<String> list = new LinkedList<>();

    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getName().equals("serialVersionUID")) {
        continue;
      }
      if (fields[i].getName().equals("sign")) {
        continue;
      }
      if (fields[i].getName().equals("tradeDesc")) {
        continue;
      }
      if (fields[i].getName().equals("payerNickName")) {
        continue;
      }

      String name = fields[i].getName();
      name = name.substring(0, 1).toUpperCase() + name.substring(1);
      Method method = clazz.getMethod("get" + name);
      String temp = method.invoke(data) == null ? null : method.invoke(data).toString();

      if (StringUtil.isNotBlank(temp)) {
        list.add(fields[i].getName());
      }

    }

    Collections.sort(list);

    return list;
  }

  /**
   * h5收银台反射类
   */
  private static <T> List<String> getFieldsListForH5Cashier(T data, Field[] fields, Class<T> clazz)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    List<String> list = new LinkedList<>();

    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getName().equals("serialVersionUID")) {
        continue;
      }
      if (fields[i].getName().equals("sign")) {
        continue;
      }
      if (fields[i].getName().equals("payEnviorment")) {
        continue;
      }
      if (fields[i].getName().equals("partnerReturnUrl")) {
        continue;
      }
      if (fields[i].getName().equals("payTool")) {
        continue;
      }

      String name = fields[i].getName();
      name = name.substring(0, 1).toUpperCase() + name.substring(1);
      Method method = clazz.getMethod("get" + name);
      String temp = method.invoke(data) == null ? null : method.invoke(data).toString();

      if (StringUtil.isNotBlank(temp)) {
        list.add(fields[i].getName());
      }

    }

    Collections.sort(list);

    return list;
  }

  private static <T> String getSign(List<String> list, String key, T data, Class<T> clazz)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < list.size(); i++) {
      String name = list.get(i);
      name = name.substring(0, 1).toUpperCase() + name.substring(1);
      Method method = clazz.getMethod("get" + name);
      String temp = method.invoke(data) == null ? null : method.invoke(data).toString();

      if (i != list.size() - 1) {
        sb.append(list.get(i)).append("=").append(temp).append("&");
      } else {
        sb.append(list.get(i)).append("=").append(temp).append("&");
      }

    }

    String beforeSign = sb.append("key=").append(key).toString();


    String afterSign = MD5.digest(beforeSign).toUpperCase();

    LogUtils.info(log, "待签名字符串={},签名以后的字符串={}", beforeSign, afterSign);

    return afterSign;
  }

  public static <T> String buildMd5Sign(T data, String key)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    Class clazz = data.getClass();

    Field[] fields = clazz.getDeclaredFields();

    List<String> list = getFieldsListForH5Cashier(data, fields, clazz);

    String sign_result = getSign(list, key, data, clazz);

    return sign_result;
  }

  public static<T> boolean checkForCashier(T data, String sign, String key,
                                     String singType)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Class clazz = data.getClass();

    Field[] fields = clazz.getDeclaredFields();

    List<String> list = getFieldsListForCashier(data, fields, clazz);

    String sign_result = getSign(list, key, data, clazz);

    if (sign.equals(sign_result)) {
      return true;
    }
    return false;
  }

  private static <T> List<String> getFieldsListForCashier(T data, Field[] fields, Class clazz)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    List<String> list = new LinkedList<>();

    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getName().equals("serialVersionUID")) {
        continue;
      }
      if (fields[i].getName().equals("sign")) {
        continue;
      }
      if (fields[i].getName().equals("partnerReturnUrl")) {
        continue;
      }
      if (fields[i].getName().equals("payEnviorment")) {
        continue;
      }
      if (fields[i].getName().equals("payerId")) {
        continue;
      }

      String name = fields[i].getName();
      name = name.substring(0, 1).toUpperCase() + name.substring(1);
      Method method = clazz.getMethod("get" + name);
      String temp = method.invoke(data) == null ? null : method.invoke(data).toString();

      if(StringUtil.isNotEmpty(temp)){
        list.add(fields[i].getName());
      }

    }

    Collections.sort(list);

    return list;
  }
}
