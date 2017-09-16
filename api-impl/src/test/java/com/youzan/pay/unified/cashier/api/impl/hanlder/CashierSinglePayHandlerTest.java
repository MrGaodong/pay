/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.result.SinglePayResult;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierSinglePayHandler;
import com.youzan.pay.unified.cashier.api.request.CashierSinglePayRequest;
import com.youzan.pay.unified.cashier.api.response.CashierSinglePayResponse;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;

import org.apache.commons.collections.map.HashedMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @author wulonghui
 * @version CashierSinglePayHandlerTest.java, v 0.1 2017-04-25 17:50
 */
public class CashierSinglePayHandlerTest {

  @InjectMocks
  CashierSinglePayHandler cashierSinglePayHandler = new CashierSinglePayHandler();

  @Mock
  private UnifiedPayServiceClient unifiedPayServiceClient;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void TestCashierSinglePay() {
    CashierSinglePayRequest cashierSinglePayRequest = buildCashierSinglePayRequest();
    SinglePayResult singlePayResult = buildSinglePayResult();
    when(unifiedPayServiceClient.singlePay(anyObject())).thenReturn(singlePayResult);
    Response<CashierSinglePayResponse> resp = buildCashierSinglePayResponse();
    com.youzan.pay.unified.cashier.api.response.Response<CashierSinglePayResponse>
        result =
        cashierSinglePayHandler
            .handle(cashierSinglePayRequest);
    Assert.assertEquals(result.getResult().getAcquireNo(), "12323");
  }

  private CashierSinglePayRequest buildCashierSinglePayRequest() {
    CashierSinglePayRequest cashierSinglePayRequest = new CashierSinglePayRequest();
    cashierSinglePayRequest.setUserAgentIp("123232");
    cashierSinglePayRequest.setTradeDesc("测试");
    cashierSinglePayRequest.setPayAmount(1);
    cashierSinglePayRequest.setPayTool("WX_H5");
    cashierSinglePayRequest.setAppName("");
    cashierSinglePayRequest.setAppType("WAP");
    cashierSinglePayRequest.setCurrencyCode("CNY");
    cashierSinglePayRequest.setGoodsDesc("有赞测试");
    cashierSinglePayRequest.setMchId("123456");
    cashierSinglePayRequest.setMemo("oo");
    cashierSinglePayRequest.setOutBizNo("E4324243432");
    cashierSinglePayRequest.setPartnerId("123214242");
    cashierSinglePayRequest.setPartnerNotifyUrl("http://www.baidu.com");
    cashierSinglePayRequest.setPartnerReturnUrl("http://www.baidu.com");
    cashierSinglePayRequest.setYzAuthToken("42412412412jljlldlfj");
    return cashierSinglePayRequest;
  }

  private SinglePayResult buildSinglePayResult() {
    SinglePayResult singlePayResult = new SinglePayResult();
    singlePayResult.setAcquireNo("12323");
    Map<String, Object> map = new HashedMap();
    map.put("Wx", "http://mapi.com.wx.com/");
    singlePayResult.setDeepLinkInfo(map);
    return singlePayResult;
  }

  private Response<CashierSinglePayResponse> buildCashierSinglePayResponse() {
    Response<CashierSinglePayResponse> resp = new Response<>();
    CashierSinglePayResponse cashierSinglePayResponse = new CashierSinglePayResponse();
    cashierSinglePayResponse.setAcquireNo("12323");
    Map<String, Object> map = new HashedMap();
    map.put("Wx", "http://mapi.com.wx.com/");
    cashierSinglePayResponse.setDeepLinkInfo(map);

    resp.setResult(cashierSinglePayResponse);
    return resp;
  }

  private CashierSinglePayRequest buildcashierPayTypeSearchRequest() {
    CashierSinglePayRequest cashierSinglePayRequest = new CashierSinglePayRequest();
    cashierSinglePayRequest.setOutBizNo("1223321");
    cashierSinglePayRequest.setAppType("WAP");
    cashierSinglePayRequest.setCurrencyCode("CNY");
    cashierSinglePayRequest.setGoodsDesc("测试");
    cashierSinglePayRequest.setMchId("342342342");
    cashierSinglePayRequest.setDistributionPackageName("com.youzan.app");
    cashierSinglePayRequest.setMemo("可以测试");
    cashierSinglePayRequest.setPartnerId("434144141");
    cashierSinglePayRequest.setPartnerNotifyUrl("http://www.baidu.com");
    cashierSinglePayRequest.setPayTool("WX_H5");
    cashierSinglePayRequest.setPayAmount(1);
    cashierSinglePayRequest.setTradeDesc("交易描述");
    cashierSinglePayRequest.setUserAgentIp("127.0.0.1");
    return cashierSinglePayRequest;
  }

}
