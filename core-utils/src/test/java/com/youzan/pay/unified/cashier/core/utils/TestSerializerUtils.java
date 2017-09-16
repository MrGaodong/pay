/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author twb
 * @version TestSerializerUtils.java, v 0.1 2017-05-18 14:48
 */
public class TestSerializerUtils {

  @Test
  public void test1() {
    ObjectForTest objectForTest = new ObjectForTest();
    objectForTest.setProperty1("汤炜标");
    objectForTest.setProperty2("eqwfqcqf");
    objectForTest.setProperty3(241341232);
    objectForTest .setProperty4(18);
    objectForTest.setProperty5(12345321L);
    objectForTest.setProperty6(678943254350L);
    byte[] bytes = SerializationUtils.toBytes(objectForTest, ObjectForTest.class);
    System.out.println(bytes.length);
    System.out.println(Arrays.toString(bytes));
    ObjectForTest obj = SerializationUtils.toObject(bytes, ObjectForTest.class);
  }

  @Test
  public void test2() {
    Object1<ObjectForTest> object1 = new Object1<>();

    ObjectForTest objectForTest = new ObjectForTest();
    objectForTest.setProperty1("汤炜标");
    objectForTest.setProperty2("eqwfqcqf");
    objectForTest.setProperty3(241341232);
    objectForTest.setProperty4(18);
    objectForTest.setProperty5(12345321L);
    objectForTest.setProperty6(678943254350L);

    object1.setData(objectForTest);

    object1.setB1(true);
    object1.setB2(false);

    byte[] bytes = SerializationUtils.toBytes(object1, Object1.class);
    System.out.println(bytes.length);
    System.out.println(Arrays.toString(bytes));

    Object1 o = SerializationUtils.toObject(bytes, Object1.class);

    ObjectForTest oo = (ObjectForTest) o.getData();

    System.out.println(o.toString());
  }
}
