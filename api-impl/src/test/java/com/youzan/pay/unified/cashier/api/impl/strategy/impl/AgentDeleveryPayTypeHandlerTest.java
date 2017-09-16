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
 * @version AgentDeleveryPayTypeHandlerTest.java, v 0.1 2017-04-07 13:23
 */
public class AgentDeleveryPayTypeHandlerTest {

  @InjectMocks
  private AgentDeleveryPayTypeHandler
      agentDeleveryPayTypeHandler =
      new AgentDeleveryPayTypeHandler();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testGenCashierPayToolCash() {
    UnifiedSearchPayTypeRequest unifiedSearchPayTypeRequest = new UnifiedSearchPayTypeRequest();
    unifiedSearchPayTypeRequest.setPartnerId("343");
    unifiedSearchPayTypeRequest.setBuyerId("4343");
    unifiedSearchPayTypeRequest.setPayAmount(19);
    unifiedSearchPayTypeRequest.setPayEnviorment("");
    unifiedSearchPayTypeRequest.setMchId("3443343");
    unifiedSearchPayTypeRequest.setPayTools("CASH_ON_DELIVERY,NOW_PAY,PEER_PAY");

    PayToolConfig payToolConfig = new PayToolConfig();
    payToolConfig.setVisible(true);
    payToolConfig.setPayTool("CASH_ON_DELIVERY");

    List<PayChannel> payChannelList = new ArrayList<>();

    agentDeleveryPayTypeHandler
        .genCashierPayTool(unifiedSearchPayTypeRequest, payToolConfig, payChannelList);

    Assert.assertNotNull(payChannelList);
  }

  @Test
  public void testtestGenCashierPayToolPeerPay() {
    UnifiedSearchPayTypeRequest unifiedSearchPayTypeRequest = new UnifiedSearchPayTypeRequest();
    unifiedSearchPayTypeRequest.setPartnerId("343");
    unifiedSearchPayTypeRequest.setBuyerId("4343");
    unifiedSearchPayTypeRequest.setPayAmount(19);
    unifiedSearchPayTypeRequest.setPayEnviorment("");
    unifiedSearchPayTypeRequest.setMchId("3443343");
    unifiedSearchPayTypeRequest.setPayTools("CASH_ON_DELIVERY,NOW_PAY,PEER_PAY");

    PayToolConfig payToolConfig = new PayToolConfig();
    payToolConfig.setPayTool("PEER_PAY");

    List<PayChannel> payChannelList = new ArrayList<>();

    agentDeleveryPayTypeHandler
        .genCashierPayTool(unifiedSearchPayTypeRequest, payToolConfig, payChannelList);

    Assert.assertNotNull(payChannelList);
  }

}
