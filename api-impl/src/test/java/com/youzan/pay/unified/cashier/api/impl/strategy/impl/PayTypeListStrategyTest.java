/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.factory.TradePayTypeFactory;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @author wulonghui
 * @version PayTypeListStrategyTest.java, v 0.1 2017-04-07 13:25
 */
public class PayTypeListStrategyTest {
    @InjectMocks
    private PayTypeListStrategy payTypeListStrategy = new PayTypeListStrategy();

    @Mock
    private TradePayTypeFactory tradePayTypeFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void TestCashierPayTool(){
        UnifiedSearchPayTypeRequest unifiedSearchPayTypeRequest = new UnifiedSearchPayTypeRequest();
        unifiedSearchPayTypeRequest.setPartnerId("343");
        unifiedSearchPayTypeRequest.setBuyerId("4343");
        unifiedSearchPayTypeRequest.setPayAmount(19);
        unifiedSearchPayTypeRequest.setPayEnviorment("ALIPAY");
        unifiedSearchPayTypeRequest.setMchId("3443343");
        unifiedSearchPayTypeRequest.setPayTools("CASH_ON_DELIVERY,NOW_PAY,PEER_PAY");

        PayToolConfig payToolConfig = new PayToolConfig();
        payToolConfig.setVisible(true);
        payToolConfig.setPayTool("ALIPAY_WAP");
        payToolConfig.setBalance(14);

        List<PayChannel> payChannelList = new ArrayList<>();
        AlipayTypeHandler alipayTypeHandler = new AlipayTypeHandler();
        when(tradePayTypeFactory.getTradePayType(anyObject())).thenReturn(alipayTypeHandler);

        payTypeListStrategy.genCashierPayTool(unifiedSearchPayTypeRequest,payToolConfig,payChannelList);
        Assert.assertEquals(1,payChannelList.size());
    }
    @Test
    public void TestCashierPayToolError(){
        UnifiedSearchPayTypeRequest unifiedSearchPayTypeRequest = new UnifiedSearchPayTypeRequest();
        PayToolConfig payToolConfig = new PayToolConfig();
        List<PayChannel> payChannelList = new ArrayList<>();
        AlipayTypeHandler alipayTypeHandler = new AlipayTypeHandler();
        when(tradePayTypeFactory.getTradePayType(anyObject())).thenReturn(null);

        payTypeListStrategy.genCashierPayTool(unifiedSearchPayTypeRequest,payToolConfig,payChannelList);
        Assert.assertEquals(0,payChannelList.size());
    }

    @Test
    public void TestCashierH5PayTool(){
        CashierH5SearchPayToolsRequest  cashierH5SearchPayToolsRequest = new CashierH5SearchPayToolsRequest();
        cashierH5SearchPayToolsRequest.setPartnerId("343");
        cashierH5SearchPayToolsRequest.setBuyerId("4343");
        cashierH5SearchPayToolsRequest.setPayAmount(18);
        cashierH5SearchPayToolsRequest.setPayEnviorment("WX_H5");
        cashierH5SearchPayToolsRequest.setMchId("343434");

        PayToolConfig payToolConfig = new PayToolConfig();
        payToolConfig.setVisible(true);
        payToolConfig.setPayTool("WX_H5");
        payToolConfig.setBalance(14);

        List<PayChannel> payChannelList = new ArrayList<>();
        WxPayTypeHandler wxPayTypeHandler = new WxPayTypeHandler();
        when(tradePayTypeFactory.getTradePayType(anyObject())).thenReturn(wxPayTypeHandler);

        payTypeListStrategy.genCashierH5PayTool(cashierH5SearchPayToolsRequest,payToolConfig,payChannelList);

        Assert.assertEquals(1,payChannelList.size());
    }
    @Test
    public void TestCashierH5PayToolError(){
        UnifiedSearchPayTypeRequest unifiedSearchPayTypeRequest = new UnifiedSearchPayTypeRequest();
        PayToolConfig payToolConfig = new PayToolConfig();
        List<PayChannel> payChannelList = new ArrayList<>();
        AlipayTypeHandler alipayTypeHandler = new AlipayTypeHandler();
        when(tradePayTypeFactory.getTradePayType(anyObject())).thenReturn(null);

        payTypeListStrategy.genCashierPayTool(unifiedSearchPayTypeRequest,payToolConfig,payChannelList);
        Assert.assertEquals(0,payChannelList.size());
    }
}
