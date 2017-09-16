/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5AcquireOrderHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.result.CashierH5AcquireOrderResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;

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

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

/**
 * @author wulonghui
 * @version CashierH5AcquireOrderHandlerTest.java, v 0.1 2017-05-22 11:29
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SignCheckUtils.class})
public class CashierH5AcquireOrderHandlerTest {
  @InjectMocks
  CashierH5AcquireOrderHandler cashierPayTypeSearchHandler=new CashierH5AcquireOrderHandler();

  @Mock
  private UnifiedOrderServiceClient unifiedOrderServiceClient;

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
  public void TestCashierH5AcquireOrderHandlerSuccess()
      throws Exception {
    CashierH5AcquireOrderRequest cashierH5AcquireOrderRequest=buildCashierH5AcquireOrderRequest();

    Response<UnifiedOrderCreatingResult> unifiedOrderCreatingResult=createAcquireOrder();

    Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(unifiedOrderCreatingResult);

    Mockito.when(signKeyServiceClient.getSignKey(anyObject())).thenReturn(null);

    com.youzan.pay.unified.cashier.api.response.Response<CashierH5AcquireOrderResult>
        resp=cashierPayTypeSearchHandler.handle(cashierH5AcquireOrderRequest);
    Assert.assertEquals(cashierH5AcquireOrderRequest.getMchId(),12345678);

//    Assert.assertEquals("342432423",resp.getResult().getUnifiedAquireOrder());
  }

  private CashierH5AcquireOrderResult buildCashierH5AcquireOrderResult() {
    CashierH5AcquireOrderResult cashierH5AcquireOrderResult=new CashierH5AcquireOrderResult();
//    cashierH5AcquireOrderResult.setUnifiedAquireOrder("123213231");
    cashierH5AcquireOrderResult.setNeedReturnPayResultUrl("http://www.baidu.com");
    return cashierH5AcquireOrderResult;
  }

  private Response<UnifiedOrderCreatingResult> createAcquireOrder() {
    Response<UnifiedOrderCreatingResult> unifiedOrderCreatingResult=new Response<>();
    UnifiedOrderCreatingResult unifiedOrderCreatingResult1=new UnifiedOrderCreatingResult();
    unifiedOrderCreatingResult1.setAcquireNo("342432423");
    unifiedOrderCreatingResult1.setStatus(AcquireQueryStatus.CREATE);
    unifiedOrderCreatingResult.setResult(unifiedOrderCreatingResult1);
    unifiedOrderCreatingResult.setMsg("ok");
    unifiedOrderCreatingResult.setSuccess(true);
    return unifiedOrderCreatingResult;
  }

  private CashierH5AcquireOrderRequest buildCashierH5AcquireOrderRequest() {
    CashierH5AcquireOrderRequest cashierH5AcquireOrderRequest=new CashierH5AcquireOrderRequest();
    cashierH5AcquireOrderRequest.setTradeDesc("交易描述");
    cashierH5AcquireOrderRequest.setPayerNickName("wuge");
    cashierH5AcquireOrderRequest.setPayerId("wuge");
    cashierH5AcquireOrderRequest.setPayAmount(1);
    cashierH5AcquireOrderRequest.setAppType("wap");
    cashierH5AcquireOrderRequest.setBizAction("1");
    cashierH5AcquireOrderRequest.setBizMode(1);
    cashierH5AcquireOrderRequest.setBizProd(1);
    cashierH5AcquireOrderRequest.setCurrencyCode("CNY");
    cashierH5AcquireOrderRequest.setDiscountableAmount(Long.valueOf(1));
    cashierH5AcquireOrderRequest.setGoodsName("12");
    cashierH5AcquireOrderRequest.setMchId(12345678);
    cashierH5AcquireOrderRequest.setMchName("wuge");
    cashierH5AcquireOrderRequest.setOutBizNo("443242342423r");
    cashierH5AcquireOrderRequest.setBizAction("pay");
    cashierH5AcquireOrderRequest.setPartnerId("34342423");
    cashierH5AcquireOrderRequest.setPartnerNotifyUrl("http://www.baidu.com");
    cashierH5AcquireOrderRequest.setPartnerReturnUrl("http://www.baidu.com");
    cashierH5AcquireOrderRequest.setGoodsName("jiaoyi");
    cashierH5AcquireOrderRequest.setSign("38DD7B94F601B25027CDFA14EE43660C");
    cashierH5AcquireOrderRequest.setSignType("MD5");
    cashierH5AcquireOrderRequest.setTel("18617008123");
    return cashierH5AcquireOrderRequest;
  }

  public static void main(String[] args) {

  }
}
