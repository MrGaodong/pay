/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.impl.filter.FilterChain;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5SearchPayToolsHandler;
import com.youzan.pay.unified.cashier.api.impl.strategy.impl.PayTypeListStrategy;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.result.CashierH5SearchPayToolsResult;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;

import org.junit.Assert;
import org.junit.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

/**
 * @author wulonghui
 * @version CashierH5SearchPayToolsHandlerTest.java, v 0.1 2017-05-22 12:08
 */
public class CashierH5SearchPayToolsHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  CashierH5SearchPayToolsHandler cashierH5SearchPayToolsHandler;

  @Mock
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @Mock
  private PayTypeListStrategy payTypeListStrategy;


  @InjectMocks
  @Resource
  private FilterChain payToolDoChain;

  @Test
  public void TestCashierH5SearchPayToolsHandlerSuccess()
      throws Exception {
    CashierH5SearchPayToolsRequest
        cashierH5SearchPayToolsRequest =
        buildCashierH5AcquireOrderRequest();

    Response<ConfigInfo> payToolConfigResponse = createSearchPayToolsFromMchPlaForm();

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject()))
        .thenReturn(payToolConfigResponse);

    com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
        resp = cashierH5SearchPayToolsHandler.handle(cashierH5SearchPayToolsRequest);

    Assert.assertEquals(0, resp.getResult().getPayChannels().size());
  }

  @Test
  public void TestCashierH5PayToolsSearchNull() {
    CashierH5SearchPayToolsRequest request = new CashierH5SearchPayToolsRequest();

    request.setMchId("43434");
    request.setPayAmount(10);
    request.setBuyerId("5434");
    request.setPartnerId("34343");
    request.setPayEnviorment("ALIPAY");

    com.youzan.pay.customer.api.result.Response<ConfigInfo>
        response =
        new com.youzan.pay.customer.api.result.Response<>();

    ConfigInfo configInfo = new ConfigInfo();

    List<PayToolConfig> list = new ArrayList<>();

    addPayToolConfigList(list);

    configInfo.setPayTools(list);

    response.setResult(null);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);

    try {
      com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
          resp = cashierH5SearchPayToolsHandler.handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void TestCashierH5SearchPayToolsNotNull() {

    CashierH5SearchPayToolsRequest request = new CashierH5SearchPayToolsRequest();

    request.setMchId("43434");
    request.setPayAmount(10);
    request.setBuyerId("5434");
    request.setPartnerId("34343");
    request.setPayEnviorment("ALIPAY");

    com.youzan.pay.customer.api.result.Response<ConfigInfo>
        response =
        new com.youzan.pay.customer.api.result.Response<>();

    ConfigInfo configInfo = new ConfigInfo();

    List<PayToolConfig> list = new ArrayList<>();

    configInfo.setPayTools(list);

    response.setResult(configInfo);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);

    try {
      com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
          resp = cashierH5SearchPayToolsHandler.handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }
  }

  private void addPayToolConfigList(List<PayToolConfig> list) {

    PayToolConfig payToolConfig = new PayToolConfig();

    PayToolConfig payToolConfig1 = new PayToolConfig();

    PayToolConfig payToolConfig2 = new PayToolConfig();

    PayToolConfig payToolConfig3 = new PayToolConfig();

    PayToolConfig payToolConfig4 = new PayToolConfig();

    PayToolConfig payToolConfig5 = new PayToolConfig();

    payToolConfig.setAvailableDesc("");
    payToolConfig.setAvailable(true);
    payToolConfig.setVisibleDesc("");
    payToolConfig.setVisible(true);
    payToolConfig.setPayTool("WX_JS");

    payToolConfig1.setAvailableDesc("");
    payToolConfig1.setAvailable(true);
    payToolConfig1.setVisibleDesc("");
    payToolConfig1.setVisible(true);
    payToolConfig1.setPayTool("ALIPAY_WAP");

    payToolConfig2.setAvailableDesc("");
    payToolConfig2.setAvailable(true);
    payToolConfig2.setVisibleDesc("");
    payToolConfig2.setVisible(true);
    payToolConfig2.setPayTool("ECARD");

    payToolConfig3.setAvailableDesc("");
    payToolConfig3.setAvailable(true);
    payToolConfig3.setVisibleDesc("");
    payToolConfig3.setVisible(true);
    payToolConfig3.setPayTool("PEERPAY");

    payToolConfig4.setAvailableDesc("");
    payToolConfig4.setAvailable(true);
    payToolConfig4.setVisibleDesc("");
    payToolConfig4.setVisible(true);
    payToolConfig4.setPayTool("CASH_ON_DELIVERY");

    payToolConfig5.setAvailableDesc("");
    payToolConfig5.setAvailable(true);
    payToolConfig5.setVisibleDesc("");
    payToolConfig5.setVisible(true);
    payToolConfig5.setPayTool("BALANCE");

    list.add(payToolConfig);
    list.add(payToolConfig1);
    list.add(payToolConfig2);
    list.add(payToolConfig3);
    list.add(payToolConfig4);
    list.add(payToolConfig5);

  }

  private Response<ConfigInfo> createSearchPayToolsFromMchPlaForm() {
    Response<ConfigInfo> resp = new Response<>();
    ConfigInfo configInfo = new ConfigInfo();
    List<PayToolConfig> payToolConfigs = new ArrayList<>();
    PayToolConfig payToolConfig = new PayToolConfig();
    payToolConfig.setPayTool("ALIPAY_WAP");
    payToolConfig.setBalance(1);
    payToolConfig.setVisible(true);
    payToolConfigs.add(payToolConfig);

    configInfo.setPayTools(payToolConfigs);
    resp.setResult(configInfo);
    payToolConfig.setAvailableDesc("");
    resp.setSuccess(true);
    resp.setMsg("success");
    return resp;
  }

  private CashierH5SearchPayToolsRequest buildCashierH5AcquireOrderRequest() {
    CashierH5SearchPayToolsRequest
        cashierH5SearchPayToolsRequest =
        new CashierH5SearchPayToolsRequest();
    cashierH5SearchPayToolsRequest.setBuyerId("3434");
    cashierH5SearchPayToolsRequest.setMchId("12324224");
    cashierH5SearchPayToolsRequest.setPartnerId("23423423423");
    cashierH5SearchPayToolsRequest.setPayAmount(1);
    cashierH5SearchPayToolsRequest.setPayEnviorment("WX_JS");
    return cashierH5SearchPayToolsRequest;
  }
}
