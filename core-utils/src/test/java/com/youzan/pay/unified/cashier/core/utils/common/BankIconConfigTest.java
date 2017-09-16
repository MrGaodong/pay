package com.youzan.pay.unified.cashier.core.utils.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/6/27 Time: 下午4:10
 */
public class BankIconConfigTest {

  @Test
  public void getIconUrl() throws Exception {

    String url = BankIconConfig.getIconUrl("ABC");
    Assert.assertEquals("https://img.yzcdn.cn/public_files/2017/06/21/abbe0a4ee9d868d5520af23e73df70d8.png",url);
  }

}