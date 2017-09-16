/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.unified.cashier.service.CashierSignCheck;
import com.youzan.platform.util.security.MD5;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierSignCheckImpl.java, v 0.1 2017-01-20 11:17
 */
@Slf4j
@Component
public class CashierSignCheckImpl<T> implements CashierSignCheck<T> {

  /**
   * 验签类具体实现
   */
  public String genSign(T data, String sign, String key, String signType)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    Class clazz = data.getClass();

    Field[] fields = clazz.getDeclaredFields();

    List<String> list = getFieldsList(data, fields, clazz);

    String sign_result = getSign(list, key, data, clazz);

    return sign_result;

  }

  private List<String> getFieldsList(T data, Field[] fields, Class<T> clazz)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    List<String> list = new LinkedList<>();

    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getName().equals("serialVersionUID")) {
        continue;
      }
      String name = fields[i].getName();
      name = name.substring(0, 1).toUpperCase() + name.substring(1);
      Method method = clazz.getMethod("get" + name);
      String temp = method.invoke(data) == null ? null : method.invoke(data).toString();

      if (temp != null) {
        list.add(fields[i].getName());
      }
    }

    Collections.sort(list);

    return list;
  }

  private String getSign(List<String> list, String key, T data, Class<T> clazz)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < list.size(); i++) {
      String name = list.get(i);
      name = name.substring(0, 1).toUpperCase() + name.substring(1);
      Method method = clazz.getMethod("get" + name);
      String temp = method.invoke(data) == null ? null : method.invoke(data).toString();
      if (temp != null) {
        if (i != list.size() - 1) {
          sb.append(name).append("=").append(temp).append("&");
        } else {
          sb.append(name).append("=").append(temp);
        }
      }
    }

    sb.append("key=").append(key);

    return MD5.digest(sb.toString()).toUpperCase();

  }

  public static void main(String args[])
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    CashierSignCheckImpl temp = new CashierSignCheckImpl();
        /*
         * UnifiedAcquireOrderRequest req=new UnifiedAcquireOrderRequest(); req.setBizAction("支付");
         * req.setCurrencyCodel("CNY"); Field[] fields=req.getClass().getDeclaredFields(); Class clazz=req.getClass();
         * List<String> list=new LinkedList<>(); for(int i=0;i<fields.length;i++){
         * if(fields[i].getName().equals("serialVersionUID")){ continue; } String name=fields[i].getName();
         * name=name.substring(0,1).toUpperCase()+name.substring(1); Method method=clazz.getMethod("get"+name); String
         * temp=method.invoke(req)==null?null:method.invoke(req).toString(); if(temp!=null){
         * list.add(fields[i].getName()); } } Collections.sort(list); StringBuilder sb=new StringBuilder(); for(int
         * i=0;i<list.size();i++){ String name=list.get(i); name=name.substring(0,1).toUpperCase()+name.substring(1);
         * Method method=clazz.getMethod("get"+name); String
         * temp=method.invoke(req)==null?null:method.invoke(req).toString(); if(temp!=null){ if(i!=list.size()-1){
         * sb.append(name).append("=").append(temp).append("&"); } else { sb.append(name).append("=").append(temp); } }
         * } sb.append("&key=adfdf2343faf"); sb.toString().toUpperCase();
         * System.out.println(MD5.digest(sb.toString()).toUpperCase());
         */

  }

}
