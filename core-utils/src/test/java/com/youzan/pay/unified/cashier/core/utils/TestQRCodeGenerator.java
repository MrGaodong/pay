/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author twb
 * @version TestQRCodeGenerator.java, v 0.1 2017-06-01 10:00
 */
public class TestQRCodeGenerator {

  /*@Test
  public void test() throws IOException {
    QRCodeGenerator generator = new QRCodeGenerator();
    String base64 = generator.encode("https://www.baidu.com");
    generator.createImage("/Users/twb", "youzan.png", base64);
  }*/

  @Test
  public void testpackageURI(){
    String domain = "http://localhost:8080";
    Map<String,String> map = new HashedMap();
    map.put("imageurl","www.baidu.com");
    map.put("number","20");
    String result = QRCodeGenerator.packageURI(domain,map);
    Assert.assertEquals(result,"http://localhost:8080?imageurl=www.baidu.com&number=20");

  }

}
