/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargePayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierReChargePayHandler;
import com.youzan.pay.unified.cashier.api.request.CashierReChargePayRequest;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargePayResult;
import com.youzan.pay.unified.cashier.intergration.client.CashierRechargeServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import org.apache.commons.collections.map.HashedMap;
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

import javax.annotation.Resource;

/**
 * @author wulonghui
 * @version CashierReChargePayHandlerTest.java, v 0.1 2017-07-04 10:55
 */
public class CashierReChargePayHandlerTest {
  @InjectMocks
  CashierReChargePayHandler cashierReChargePayHandler=new CashierReChargePayHandler();

  @Mock
  protected UnifiedPayServiceClient unifiedPayServiceClient;

  @Mock
  protected CashierRechargeServiceClient cashierRechargeServiceClient;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void CashierReChargePayTest(){
    CashierReChargePayRequest cashierReChargePayRequest=buildReqeust();

    Response<RechargePayResult>
        cashierReChargePayResultResponse=buildResp();

    Mockito.when(cashierRechargeServiceClient.pay(Mockito.anyObject())).thenReturn(cashierReChargePayResultResponse);

    com.youzan.pay.unified.cashier.api.response.Response<CashierReChargePayResult>
        resp=cashierReChargePayHandler.handle(cashierReChargePayRequest);
    Assert.assertEquals(0,resp.getResult().getNewPrice());
    System.out.println(resp);

  }

  private Response<RechargePayResult> buildResp() {
    Response<RechargePayResult> cashierReChargePayResultResponse=new Response<>();
    RechargePayResult cashierReChargePayResult=new RechargePayResult();
    cashierReChargePayResult.setCardNo("ojij");
    PayDetailResult payDetailResult=new PayDetailResult();
    Map<String,Object> map=new HashedMap();
    map.put("deepLinkInfo","http://alipay.com");
    payDetailResult.setDeepLinkInfo(map);
    List<PayDetailResult> list=new ArrayList<>();
    list.add(payDetailResult);
    cashierReChargePayResult.setPayDetailResult(list);
    cashierReChargePayResultResponse.setSuccess(true);
    cashierReChargePayResultResponse.setResult(cashierReChargePayResult);
    cashierReChargePayResultResponse.setRespId("3424234234rrrr");
    return cashierReChargePayResultResponse;
  }

  private CashierReChargePayRequest buildReqeust() {
    CashierReChargePayRequest cashierReChargePayRequest=new CashierReChargePayRequest();
    cashierReChargePayRequest.setPayAmount(1);
    cashierReChargePayRequest.setPartnerId("3424234234");
    cashierReChargePayRequest.setAcquireNo("33424242");
    cashierReChargePayRequest.setAppName("ok");
    cashierReChargePayRequest.setCardNo("3424342");
    cashierReChargePayRequest.setCustomerId("342423");
    cashierReChargePayRequest.setDiscountableAmount(1);
    cashierReChargePayRequest.setMchId("2334242");
    cashierReChargePayRequest.setOutBizNo("242424242");
    cashierReChargePayRequest.setUserAgentIp("234244");
    cashierReChargePayRequest.setWxSubOpenId("34242");
    cashierReChargePayRequest.setTradeDesc("242423");
    cashierReChargePayRequest.setPayTool("WX_JS");
    cashierReChargePayRequest.setPayerId("0");
    return cashierReChargePayRequest;
  }

}
