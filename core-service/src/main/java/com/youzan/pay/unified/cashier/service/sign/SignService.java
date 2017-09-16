/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.sign;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.annotation.SkipSign;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import sun.rmi.runtime.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version SignService.java, v 0.1 2017-06-02 11:34
 */
@Slf4j
public class SignService {

  private final static String METHOD_PREFIX = "get";
  private final static String EQUAL_SYMBOL = "=";
  private final static String AND_SYMBOL = "&";

  public static <T> String buildSign(T data, String signKey, String signType) {
    LogUtils.info(log,"[开始进入签名]");
    try {
      List<String> tempList = new ArrayList<>();
      Class clazz = data.getClass();
      Field[] fields = clazz.getDeclaredFields();

      for (Field field : fields) {
        if (field.isAnnotationPresent(SkipSign.class)) {
          LogUtils.info(log,"skip");
          continue;
        }
        String fieldName = field.getName();
        String methodName = METHOD_PREFIX + upperFirstChar(fieldName);
        Method method = clazz.getMethod(methodName);
        Object value = method.invoke(data);
        if(value!=null && StringUtils.isNotBlank(value.toString())){
          tempList.add(fieldName + EQUAL_SYMBOL + value + AND_SYMBOL);
        }
      }
      Collections.sort(tempList);
      StringBuilder sb = new StringBuilder();
      for (String e : tempList) {
        sb.append(e);
      }
      sb.append("key=" + signKey);
      String temp = sb.toString();
      LogUtils.info(log,"[signService beforStr={}]",sb.toString());
      return DigestUtils.md5DigestAsHex(temp.getBytes()).toUpperCase();
    } catch (Exception e) {
      LogUtils.error(e,log,"签名出错");
      throw new RuntimeException(e.getCause());
    }
  }

  private static String upperFirstChar(String param) {
    return param.substring(0, 1).toUpperCase() + param.substring(1);
  }

  public static String sign(Map<String, String> data, String signKey, String signType) {
    if (CollectionUtils.isEmpty(data)) {
      throw new RuntimeException("data cannot be empty");
    }
    try {
      List<String> tempList = new ArrayList<>();
      for (Map.Entry<String, String> entry : data.entrySet()) {
        tempList.add(entry.getKey() + EQUAL_SYMBOL + entry.getValue() + AND_SYMBOL);
      }
      Collections.sort(tempList);
      StringBuilder sb = new StringBuilder();
      for (String value : tempList) {
        sb.append(value);
      }
      sb.append("key=" + signKey);
      String temp = sb.toString();
      return DigestUtils.md5DigestAsHex(temp.getBytes());
    } catch (Exception e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public static void main(String[] args) {

  }
}
