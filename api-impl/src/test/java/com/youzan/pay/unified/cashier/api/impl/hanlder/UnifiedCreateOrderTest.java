/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.UnifiedCashierAcquierOrderHandler;
import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.result.UnifiedAcquireOrderResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;
import com.youzan.pay.unified.cashier.service.CashierSignCheck;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

/**
 * 收银台创建收单单元测试
 *
 * @author wulonghui
 * @version UnifiedCreateOrderTest.java, v 0.1 2017-01-13 18:17
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SignCheckUtils.class})
public class UnifiedCreateOrderTest {

  @Mock
  private UnifiedOrderServiceClient unifiedOrderServiceClient;

  @Mock
  private CashierSignCheck cashierSignCheck;

  @Mock
  private SignKeyServiceClient signKeyServiceClient;

  @InjectMocks
  private UnifiedCashierAcquierOrderHandler
      unifiedCashierAcquierOrderHandler =
      new UnifiedCashierAcquierOrderHandler();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void TestCreateOrder() throws Exception {
    CreateOrder();
    UnifiedAcquireOrderRequest request = new UnifiedAcquireOrderRequest();
    request.setSignType("MD5");
    request.setSign("jkjeljjr3jrj3ojjlj");
    request.setBizAction("pay");
    request.setBizMode(1);
    request.setTradeDesc("rfe");
    request.setBizProd(1);
    request.setCurrencyCodel("CNY");
    request.setOutBizNo("4343434");
    request.setPayAmount(4);
    request.setPayerId("3434");
    request.setPayTools("NOW_PAY");
    request.setPartnerId("34343");
    request.setMchId(4343434343L);
    com.youzan.pay.unified.cashier.api.response.Response<UnifiedAcquireOrderResult>
        resp =
        unifiedCashierAcquierOrderHandler
            .handle(request);
    Assert.assertEquals("123456789", resp.getResult().getUnifiedAquireOrder());
  }

  @Test
  public void TestCreateOrderSysError() throws Exception {
    CreateOrderSysError();
    UnifiedAcquireOrderRequest request = new UnifiedAcquireOrderRequest();

    request.setSignType("MD5");
    request.setSign("jkjeljjr3jrj3ojjlj");
    request.setBizAction("pay");
    request.setBizMode(1);
    request.setTradeDesc("rfe");
    request.setBizProd(1);
    request.setCurrencyCodel("CNY");
    request.setOutBizNo("4343434");
    request.setPayAmount(4);
    request.setPayerId("3434");
    request.setPayTools("NOW_PAY");
    request.setPartnerId("34343");
    request.setMchId(4343434343L);
    try {
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedAcquireOrderResult>
          resp =
          unifiedCashierAcquierOrderHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue("", true);
    }

  }

  @Test
  public void TestCreateOrderPaying() throws Exception {
    CreateOrderPaying();
    UnifiedAcquireOrderRequest request = new UnifiedAcquireOrderRequest();

    request.setSignType("MD5");
    request.setSign("jkjeljjr3jrj3ojjlj");
    request.setBizAction("pay");
    request.setBizMode(1);
    request.setTradeDesc("rfe");
    request.setBizProd(1);
    request.setCurrencyCodel("CNY");
    request.setOutBizNo("4343434");
    request.setPayAmount(4);
    request.setPayerId("3434");
    request.setPayTools("NOW_PAY");
    request.setPartnerId("34343");
    request.setMchId(4343434343L);

    com.youzan.pay.unified.cashier.api.response.Response<UnifiedAcquireOrderResult>
        resp =
        unifiedCashierAcquierOrderHandler
            .handle(request);
    Assert.assertEquals("123456789", resp.getResult().getUnifiedAquireOrder());
  }

  @Test
  public void TestCreateOrderPayFinish() throws Exception {
    CreateOrderPayFinish();
    UnifiedAcquireOrderRequest request = new UnifiedAcquireOrderRequest();

    request.setSignType("MD5");
    request.setSign("jkjeljjr3jrj3ojjlj");
    request.setBizAction("pay");
    request.setBizMode(1);
    request.setTradeDesc("rfe");
    request.setBizProd(1);
    request.setCurrencyCodel("CNY");
    request.setOutBizNo("4343434");
    request.setPayAmount(4);
    request.setPayerId("3434");
    request.setPayTools("NOW_PAY");
    request.setPartnerId("34343");
    request.setMchId(4343434343L);

    try {
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedAcquireOrderResult>
          resp =
          unifiedCashierAcquierOrderHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }

  }

  /**
   * 收单号为空
   */
  @Test
  public void TestCreateOrderNull() throws Exception {
    CreateorderNull();

    UnifiedAcquireOrderRequest request = new UnifiedAcquireOrderRequest();

    request.setSignType("MD5");
    request.setSign("jkjeljjr3jrj3ojjlj");
    request.setBizAction("pay");
    request.setBizMode(1);
    request.setTradeDesc("rfe");
    request.setBizProd(1);
    request.setCurrencyCodel("CNY");
    request.setOutBizNo("4343434");
    request.setPayAmount(4);
    request.setPayerId("3434");
    request.setPayTools("NOW_PAY");
    request.setPartnerId("34343");
    request.setMchId(4343434343L);

    try {
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedAcquireOrderResult>
          resp =
          unifiedCashierAcquierOrderHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }

  }

  @Test
  public void TestBuyerPaied() throws Exception {
    buyerpaied();

    UnifiedAcquireOrderRequest request = new UnifiedAcquireOrderRequest();

    request.setSignType("MD5");
    request.setSign("jkjeljjr3jrj3ojjlj");
    request.setBizAction("pay");
    request.setBizMode(1);
    request.setTradeDesc("rfe");
    request.setBizProd(1);
    request.setCurrencyCodel("CNY");
    request.setOutBizNo("4343434");
    request.setPayAmount(4);
    request.setPayerId("3434");
    request.setPayTools("NOW_PAY");
    request.setPartnerId("34343");
    request.setMchId(4343434343L);

    try {
      com.youzan.pay.unified.cashier.api.response.Response<UnifiedAcquireOrderResult>
          resp =
          unifiedCashierAcquierOrderHandler
              .handle(request);
    } catch (Exception e) {
      Assert.assertTrue(true);
    }
  }

  private void buyerpaied() throws Exception {
    Response<UnifiedOrderCreatingResult> resp = new Response<>();

    UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();
    unifiedOrderCreatingResult.setAcquireNo("454545445");
    unifiedOrderCreatingResult.setStatus(AcquireQueryStatus.BUYER_PAIED);

    resp.setMsg("创建收单失败");

    resp.setSuccess(true);

    resp.setResultCode("1000");

    resp.setResult(unifiedOrderCreatingResult);

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.check(any(), any(), any(), any())).thenReturn(true);
    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);
  }

  private void CreateorderNull() throws Exception {
    Response<UnifiedOrderCreatingResult> resp = new Response<>();

    UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();
    unifiedOrderCreatingResult.setAcquireNo(null);
    unifiedOrderCreatingResult.setStatus(AcquireQueryStatus.SUCCESS);

    resp.setMsg("创建收单失败");

    resp.setSuccess(true);

    resp.setResultCode("1000");

    resp.setResult(unifiedOrderCreatingResult);

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.check(any(), any(), any(), any())).thenReturn(true);
    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);

  }

  private void CreateOrder() throws Exception {

    Response<UnifiedOrderCreatingResult> resp = new Response<>();

    UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();

    unifiedOrderCreatingResult.setAcquireNo("123456789");

    unifiedOrderCreatingResult.setStatus(AcquireQueryStatus.SUCCESS);

    resp.setMsg("创建收单成功");

    resp.setSuccess(true);

    resp.setResultCode("1000");

    resp.setResult(unifiedOrderCreatingResult);

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.check(any(), any(), any(), any())).thenReturn(true);
    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);

  }

  private void CreateOrderSysError() throws Exception {
    Response<UnifiedOrderCreatingResult> resp = new Response<>();

    UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();

    resp.setResult(unifiedOrderCreatingResult);

    resp.setResultCode("1001");

    resp.setSuccess(false);

    resp.setMsg("创建收单失败");

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.check(any(), any(), any(), any())).thenReturn(true);
    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);

  }

  private void CreateOrderPaying() throws Exception {
    Response<UnifiedOrderCreatingResult> resp = new Response<>();

    UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();

    unifiedOrderCreatingResult.setAcquireNo("123456789");

    unifiedOrderCreatingResult.setStatus(AcquireQueryStatus.PAYING);

    resp.setMsg("买家正在支付");

    resp.setSuccess(true);

    resp.setMsg("1002");
    resp.setResult(unifiedOrderCreatingResult);

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.check(any(), any(), any(), any())).thenReturn(true);
    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);

  }

  private void CreateOrderPayFinish() throws Exception {
    Response<UnifiedOrderCreatingResult> resp = new Response<>();

    UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();

    unifiedOrderCreatingResult.setAcquireNo("123456789");

    resp.setMsg("买家已经支付");

    resp.setSuccess(true);

    resp.setMsg("1004");

    resp.setResult(unifiedOrderCreatingResult);

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.check(any(), any(), any(), any())).thenReturn(true);
    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);

  }

}
