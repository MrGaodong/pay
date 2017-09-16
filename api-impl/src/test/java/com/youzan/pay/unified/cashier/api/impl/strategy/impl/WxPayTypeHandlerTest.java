/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wulonghui
 * @version WxPayTypeHandlerTest.java, v 0.1 2017-04-07 13:25
 */
public class WxPayTypeHandlerTest {

  @InjectMocks
  private WxPayTypeHandler wxPayTypeHandler = new WxPayTypeHandler();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void TestgenCashierPayToolAlipay() {
    UnifiedSearchPayTypeRequest unifiedSearchPayTypeRequest = new UnifiedSearchPayTypeRequest();
    unifiedSearchPayTypeRequest.setPartnerId("343");
    unifiedSearchPayTypeRequest.setBuyerId("4343");
    unifiedSearchPayTypeRequest.setPayAmount(19);
    unifiedSearchPayTypeRequest.setPayEnviorment("WX");
    unifiedSearchPayTypeRequest.setMchId("3443343");
    unifiedSearchPayTypeRequest.setPayTools("CASH_ON_DELIVERY,NOW_PAY,PEER_PAY");

    PayToolConfig payToolConfig = new PayToolConfig();
    payToolConfig.setVisible(true);
    payToolConfig.setPayTool("WX_JS");

    List<PayChannel> payChannelList = new ArrayList<>();

    wxPayTypeHandler.genCashierPayTool(unifiedSearchPayTypeRequest, payToolConfig, payChannelList);

    Assert.assertNotNull(payChannelList);
  }
}
