/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.recharge.handler;
import com.youzan.pay.unified.cashier.api.request.group.recharge.CashierReChargeGenUrlRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeGenUrlResult;
import com.youzan.pay.unified.cashier.core.utils.model.GenRechargeInfo;
import com.youzan.pay.unified.cashier.service.cache.CacheService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
/**
 * @author wulonghui
 * @version CashierReChargeGenUrlHandlerTest.java, v 0.1 2017-07-04 10:56
 */
public class CashierReChargeGenUrlHandlerTest {
  @InjectMocks
  CashierReChargeGenUrlHandler cashierReChargeGenUrlHandler=new CashierReChargeGenUrlHandler();

  @Mock
  CacheService cacheService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void CashierReChargeGenUrlHandlerTest(){

    CashierReChargeGenUrlRequest cashierReChargeGenUrlRequest=new CashierReChargeGenUrlRequest();
    cashierReChargeGenUrlRequest.setCardNo("13121");
    cashierReChargeGenUrlRequest.setUndiscountableAmount(1);
    cashierReChargeGenUrlRequest.setRechargePayType(1);
    cashierReChargeGenUrlRequest.setIsNeedSuccessJump(1);
    cashierReChargeGenUrlRequest.setOutBizNo("3242423");
    cashierReChargeGenUrlRequest.setAppType("WAP");
    cashierReChargeGenUrlRequest.setBizAction("pay");
    cashierReChargeGenUrlRequest.setBizMode(1);
    cashierReChargeGenUrlRequest.setBizProd(1);
    cashierReChargeGenUrlRequest.setCurrencyCode("cny");
    cashierReChargeGenUrlRequest.setGoodsDesc("ok");
    cashierReChargeGenUrlRequest.setGoodsName("24243");
    cashierReChargeGenUrlRequest.setMchId(32424234242L);
    cashierReChargeGenUrlRequest.setMchName("324424fw");
    cashierReChargeGenUrlRequest.setPartnerId("32424424242423");
    cashierReChargeGenUrlRequest.setPartnerNotifyUrl("http://www.baidu.com");
    cashierReChargeGenUrlRequest.setPayAmount(1);
    cashierReChargeGenUrlRequest.setReturnUrl("http://www.baidu.com");
    cashierReChargeGenUrlRequest.setSign("2424effeew");
    cashierReChargeGenUrlRequest.setTel("342423");
    cashierReChargeGenUrlRequest.setTradeDesc("23424fsfsd");

    Response<CashierReChargeGenUrlResult> result=new Response<>();
    CashierReChargeGenUrlResult cashierReChargeGenUrlResult=new CashierReChargeGenUrlResult();
    cashierReChargeGenUrlResult.setCashierUrl("http://www.baidu.com");
    cashierReChargeGenUrlResult.setStatus("success");
    result.setResult(cashierReChargeGenUrlResult);
    result.setSuccess(true);
    result.setResultCode("2144234");

    Mockito.when(cacheService.get(new String(),GenRechargeInfo.class)).thenReturn(null);

    Response<CashierReChargeGenUrlResult> cashierReChargeGenUrlResultResponse=cashierReChargeGenUrlHandler.handle(cashierReChargeGenUrlRequest);

    Assert.assertEquals("success",cashierReChargeGenUrlResultResponse.getResult().getStatus());
  }
}
