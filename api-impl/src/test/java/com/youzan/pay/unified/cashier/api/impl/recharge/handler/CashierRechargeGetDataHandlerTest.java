/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.recharge.handler;

import com.youzan.pay.unified.cashier.api.request.CashierRechargeGetDataRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierRechargeGetDataResult;
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
 * @version CashierRechargeGetDataHandlerTest.java, v 0.1 2017-07-04 10:57
 */
public class CashierRechargeGetDataHandlerTest {
  @InjectMocks
  CashierRechargeGetDataHandler cashierRechargeGetDataHandler=new CashierRechargeGetDataHandler();

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
  public void cashierRechargeGetDataHandlerTest(){
    CashierRechargeGetDataRequest cashierRechargeGetDataRequest=buildCashierRechargeGetDataRequest();

    Response<CashierRechargeGetDataResult> resp=buildCashierRechargeGetDataResultResp();
    GenRechargeInfo genRechargeInfo=new GenRechargeInfo();
    genRechargeInfo.setRechargePayType(1);
    genRechargeInfo.setMchName("342432");
    genRechargeInfo.setCardNo("23423");
    genRechargeInfo.setCustomerId("2342423");
    genRechargeInfo.setTradeDesc("2342fsfs");
    genRechargeInfo.setBizAction("recharge");

    Mockito.when(cacheService.get(Mockito.anyString(),
                                  Mockito.anyObject())).thenReturn(genRechargeInfo);

    Response<CashierRechargeGetDataResult> resp_cashier=cashierRechargeGetDataHandler.handle(cashierRechargeGetDataRequest);
    System.out.println(resp_cashier);
    Assert.assertEquals("23423",resp_cashier.getResult().getCardNo());
  }

  private Response<CashierRechargeGetDataResult> buildCashierRechargeGetDataResultResp() {
    CashierRechargeGetDataResult cashierRechargeGetDataResult=new CashierRechargeGetDataResult();
    Response<CashierRechargeGetDataResult> cashierRechargeGetDataResultResponse=new Response<>();
    cashierRechargeGetDataResult.setGoodsName("2424fewr");
    cashierRechargeGetDataResult.setMchName("2342342");
    cashierRechargeGetDataResult.setSign("2342feew");
    cashierRechargeGetDataResult.setTradeDesc("3424dsfs");
    cashierRechargeGetDataResult.setCardNo("23424234rwrqw");
    cashierRechargeGetDataResult.setCustomerId("234234234");
    cashierRechargeGetDataResult.setRechargePayType(1);
    cashierRechargeGetDataResult.setAppType("wap");
    cashierRechargeGetDataResult.setBizAction("pay");
    cashierRechargeGetDataResult.setBizProd(1);
    cashierRechargeGetDataResult.setBizMode(1);
    cashierRechargeGetDataResult.setCurrencyCode("cny");
    cashierRechargeGetDataResult.setDiscountableAmount(1);
    cashierRechargeGetDataResult.setIsNeedSuccessJump(1);
    cashierRechargeGetDataResult.setMchId(23424234234243L);
    cashierRechargeGetDataResult.setOutBizNo("323424242");
    cashierRechargeGetDataResult.setPartnerId("34234242423423525");
    cashierRechargeGetDataResult.setPartnerNotifyUrl("http://www.baidu.com");
    cashierRechargeGetDataResult.setPayAmount(1);
    cashierRechargeGetDataResult.setReturnUrl("http://www.baidu.com");
    cashierRechargeGetDataResult.setTel("32424424222");
    cashierRechargeGetDataResult.setSignType("md5");
    cashierRechargeGetDataResult.setUndiscountableAmount(1);

    cashierRechargeGetDataResultResponse.setResult(cashierRechargeGetDataResult);
    cashierRechargeGetDataResultResponse.setSuccess(true);
    cashierRechargeGetDataResultResponse.setMsg("ok");

    return cashierRechargeGetDataResultResponse;
  }

  private CashierRechargeGetDataRequest buildCashierRechargeGetDataRequest() {
    CashierRechargeGetDataRequest cashierRechargeGetDataRequest=new CashierRechargeGetDataRequest();
    cashierRechargeGetDataRequest.setSign("234224errw");
    return cashierRechargeGetDataRequest;
  }
}
