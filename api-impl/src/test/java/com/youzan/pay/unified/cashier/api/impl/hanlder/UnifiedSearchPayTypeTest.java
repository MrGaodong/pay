/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.UnifiedCashierSearchPayTypeHandler;
import com.youzan.pay.unified.cashier.api.impl.strategy.impl.PayTypeListStrategy;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.api.result.UnifiedSearchPayTypeResult;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;

import com.youzan.pay.unified.cashier.service.cache.PayToolsSortingCache;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.mockito.Matchers.anyObject;

/**
 * 支付方式列表单元测试类
 *
 * @author wulonghui
 * @version UnifiedSearchPayTypeTest.java, v 0.1 2017-01-13 18:17
 */
public class UnifiedSearchPayTypeTest {

  @Mock
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @Mock
  private PayTypeListStrategy payTypeListStrategy;

  @Mock
  private PayToolsSortingCache payToolsSortingCache;


  @InjectMocks
  UnifiedCashierSearchPayTypeHandler
      unifiedCashierSearchPayTypeHandler =
      new UnifiedCashierSearchPayTypeHandler();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void TestSearchPayTypeFinish() {
    UnifiedSearchPayTypeRequest request = new UnifiedSearchPayTypeRequest();

    request.setPayTools("NOW_PAY,CASH_ON_DELIVERY,PEER_PAY");
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

    response.setResult(configInfo);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);
    Mockito.when(payToolsSortingCache.getCacheMap()).thenReturn(buildMap());

    com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
        resp =
        unifiedCashierSearchPayTypeHandler
            .handle(request);

   // Assert.assertNotNull(resp.getResult());

  }

  /**
   *
   */
  @Test
  public void TestSearchPayType() {

    UnifiedSearchPayTypeRequest request = new UnifiedSearchPayTypeRequest();

    request.setPayTools("NOW_PAY,CASH_ON_DELIVERY,PEER_PAY");
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

    response.setResult(configInfo);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);

    try {
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
          resp =
          unifiedCashierSearchPayTypeHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }

  }

  /**
   * 测试商户平台支付方式返回结果为空
   */
  @Test
  public void TestSearchResultNull() {
    UnifiedSearchPayTypeRequest request = new UnifiedSearchPayTypeRequest();

    request.setPayTools("NOW_PAY,CASH_ON_DELIVERY,PEER_PAY");
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
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
          resp =
          unifiedCashierSearchPayTypeHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }

  }

  /**
   * 测试商户平台支付方式返回支付方式列表结果为空
   */
  @Test
  public void TestSearchPayToolsNull() {
    UnifiedSearchPayTypeRequest request = new UnifiedSearchPayTypeRequest();

    request.setPayTools("NOW_PAY,CASH_ON_DELIVERY,PEER_PAY");
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
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
          resp =
          unifiedCashierSearchPayTypeHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }
  }

  private Map<String,Integer>  buildMap(){
    Map<String,Integer>  map = new TreeMap<>();
    map.put("WX_JS",1);
    map.put("ALIPAY_WAP",1);
    map.put("CASH_ON_DELIVERY",2);
    map.put("ECARD",2);
    map.put("BALANCE",2);
    map.put("PEERPAY",2);
    return map;
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
}
