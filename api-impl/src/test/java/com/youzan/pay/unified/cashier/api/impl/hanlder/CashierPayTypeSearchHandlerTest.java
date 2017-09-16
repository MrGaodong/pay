/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierPayTypeSearchHandler;
import com.youzan.pay.unified.cashier.api.request.CashierPayTypeSearchRequest;
import com.youzan.pay.unified.cashier.api.result.CashierPayTypeSearchResult;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;

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
 * @version CashierPayTypeSearchHandlerTest.java, v 0.1 2017-04-23 17:44
 */
public class CashierPayTypeSearchHandlerTest {

  @InjectMocks
  CashierPayTypeSearchHandler cashierPayTypeSearchHandler = new CashierPayTypeSearchHandler();

  @Mock
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @Mock
  private SignKeyServiceClient signKeyServiceClient;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void TestBuildMd5Sign() throws Exception {
    CashierPayTypeSearchRequest cashierPayTypeSearchRequest = buildCashierPayTypeSearchRequest();
    Response<ConfigInfo> configInfoResponse = buildConfigResponse();

    when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(configInfoResponse);
    when(signKeyServiceClient.getSignKey(anyObject()))
        .thenReturn("d7618d3c1936b5c08f99993a928f6a1e");

    com.youzan.pay.unified.cashier.api.response.Response<CashierPayTypeSearchResult>
        result =
        cashierPayTypeSearchHandler
            .handle(cashierPayTypeSearchRequest);

    Assert.assertNotNull(result);
  }

  private Response<ConfigInfo> buildConfigResponse() {
    Response<ConfigInfo> resp = new Response<>();
    List<PayToolConfig> payTools = buildPayTools();
    ConfigInfo configInfo = new ConfigInfo();
    configInfo.setPayTools(payTools);
    resp.setResult(configInfo);
    return resp;
  }

  private List<PayToolConfig> buildPayTools() {
    List<PayToolConfig> payTools = new ArrayList<>();
    PayToolConfig payToolConfig = new PayToolConfig();
    payToolConfig.setPayTool("WX_H5");
    payTools.add(payToolConfig);
    return payTools;
  }

  private CashierPayTypeSearchRequest buildCashierPayTypeSearchRequest() {
    CashierPayTypeSearchRequest cashierPayTypeSearchRequest = new CashierPayTypeSearchRequest();
    cashierPayTypeSearchRequest.setOutBizNo("E123456");
    cashierPayTypeSearchRequest.setPartnerId("820000000947");
    cashierPayTypeSearchRequest.setAppType("WAP");
    cashierPayTypeSearchRequest.setCurrencyCode("CNY");
    cashierPayTypeSearchRequest.setDiscountableAmount(1);
    cashierPayTypeSearchRequest.setUndiscountableAmount(1);
    cashierPayTypeSearchRequest.setGoodsDesc("good");
    cashierPayTypeSearchRequest.setGoodsName("测试洗发水");
    cashierPayTypeSearchRequest.setMchId("12345678");
    cashierPayTypeSearchRequest.setMchName("叮当测试");
    cashierPayTypeSearchRequest.setPartnerNotifyUrl("http://www.baidu.com");
    cashierPayTypeSearchRequest.setPayAmount(1);
    cashierPayTypeSearchRequest.setPayEnviorment("WX");
    cashierPayTypeSearchRequest.setPayerId("ceshiren");
    cashierPayTypeSearchRequest.setReturnUrl("http://dingdang.com/");
    cashierPayTypeSearchRequest.setSign("1E67B8E9C3F07A63BD717D3FE846A058");
    cashierPayTypeSearchRequest.setTel("18617008123");
    cashierPayTypeSearchRequest.setTradeDesc("实时交易");
    return cashierPayTypeSearchRequest;
  }
}
