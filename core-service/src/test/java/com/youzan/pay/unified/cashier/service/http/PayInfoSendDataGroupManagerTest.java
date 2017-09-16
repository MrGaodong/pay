package com.youzan.pay.unified.cashier.service.http;

import com.youzan.pay.unified.cashier.service.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/6/27 Time: 上午10:40
 */
public class PayInfoSendDataGroupManagerTest extends BaseTest {

  @Resource
  private PayInfoSendDataGroupManager payInfoSendDataGroupManager;

  @Test
  public void testSendInfoToDG() throws Exception {

    payInfoSendDataGroupManager.sendInfoToDG("123","192.168.1.1","18667179836",PayMethodType.EEPAY);

  }

}