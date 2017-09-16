package com.youzan.pay.unified.cashier.core.utils.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/6/28 Time: 下午10:06
 */
public class DataMixProcessorTest {

  @Test
  public void testPhoneNumMix() throws Exception {

    String processedData = DataMixProcessor.phoneNumMix("18667179836");
    Assert.assertEquals("186****9836",processedData);

    String processedData2 = DataMixProcessor.phoneNumMix("1866");
    Assert.assertEquals("1866",processedData2);

    String processedData3 = DataMixProcessor.phoneNumMix("18667");
    Assert.assertEquals("****7",processedData3);

  }

}