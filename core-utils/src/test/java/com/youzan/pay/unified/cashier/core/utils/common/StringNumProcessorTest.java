package com.youzan.pay.unified.cashier.core.utils.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/7/6 Time: 上午5:55
 */
public class StringNumProcessorTest {

  @Test
  public void testConvertToLong() throws Exception {

    Long v = StringNumProcessor.convertToLong("123");
    Assert.assertEquals(123, v.longValue());
  }

  @Test
  public void testConvertToLongForEmpty() throws Exception {

    Long v = StringNumProcessor.convertToLong("    ");
    Assert.assertEquals(0L, v.longValue());
  }

}