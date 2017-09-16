package com.youzan.pay.unified.cashier.core.utils.common;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import lombok.Data;

import static com.youzan.pay.unified.cashier.core.utils.common.MyCollector.listToMap;
import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/6/28 Time: 上午11:03
 */
public class MyCollectorTest {

  @Test
  public void testListToMap() throws Exception {

    TypeA b1 = new TypeA("jiangxi", 1001);
    TypeA b2 = new TypeA("zhejiang", 1002);
    TypeA b3 = new TypeA("beijing", 1003);
    List<TypeA> list = Lists.newArrayList(b1, b2, b3);

    Map<Integer, String> ret =
        list.stream().collect(listToMap((entry) -> entry.getCode(), (entry) -> entry.getAddress()));
    Assert.assertEquals(3, ret.size());
    Assert.assertEquals("jiangxi", ret.get(1001));
  }

  @Data
  public static class TypeA {

    private int code;
    private String address;

    public TypeA(String address, int code) {
      this.code = code;
      this.address = address;
    }
  }

}