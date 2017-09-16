/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargeOrderCreatingResult;
import com.youzan.pay.unified.cashier.api.constants.RechargeConstants;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5AcquireOrderHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierReChargeCreateHandler;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeCreateRequest;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeCreateResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.intergration.client.CashierRechargeServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;

import com.sun.javafx.geom.AreaOp;

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

import javax.annotation.Resource;

import static org.mockito.Matchers.any;

/**
 * @author wulonghui
 * @version CashierReChargeCreateHandlerTest.java, v 0.1 2017-07-04 10:54
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SignCheckUtils.class})
public class CashierReChargeCreateHandlerTest {
  @InjectMocks
  CashierReChargeCreateHandler cashierReChargeCreateHandler=new CashierReChargeCreateHandler();

  @Mock
  private CashierRechargeServiceClient cashierRechargeServiceClient;

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
  public void creatRechargeOrderTest() throws Exception {
    CashierRechargeCreateRequest cashierRechargeCreateRequest=buildCashierRechargeCreateRequest();
    Response<RechargeOrderCreatingResult> resp=buildResp();
    Mockito.when(cashierRechargeServiceClient.create(Mockito.anyObject())).thenReturn(resp);
    Mockito.when(signKeyServiceClient.getSignKey(Mockito.anyObject())).thenReturn(RechargeConstants.KEY);
    PowerMockito.mockStatic(SignCheckUtils.class);
    PowerMockito.when(SignCheckUtils.checkForCashier(any(), any(), any(), any())).thenReturn(true);
   com.youzan.pay.unified.cashier.api.response.Response<CashierReChargeCreateResult>
       cashierReChargeCreateResultResponse=cashierReChargeCreateHandler.handle(cashierRechargeCreateRequest);
    Assert.assertEquals("1223131",cashierReChargeCreateResultResponse.getResult().getUnifiedAcquireOrder());
  }

  private Response<RechargeOrderCreatingResult> buildResp() {
    Response<RechargeOrderCreatingResult> resp=new Response<>();
    RechargeOrderCreatingResult result=new RechargeOrderCreatingResult();
    result.setAcquireNo("1223131");
    result.setStatus(AcquireQueryStatus.PAYING);
    resp.setResult(result);
    resp.setSuccess(true);
    return resp;
  }

  private CashierRechargeCreateRequest buildCashierRechargeCreateRequest() {
    CashierRechargeCreateRequest cashierRechargeCreateRequest=new CashierRechargeCreateRequest();
    cashierRechargeCreateRequest.setMchName("预付卡充值");
    cashierRechargeCreateRequest.setCardNo("123456");
    cashierRechargeCreateRequest.setTradeDesc("预付卡");
    cashierRechargeCreateRequest.setAppType("WAP");
    cashierRechargeCreateRequest.setBizAction("PAY");
    cashierRechargeCreateRequest.setBizMode(2);
    cashierRechargeCreateRequest.setCurrencyCode("CNY");
    cashierRechargeCreateRequest.setCustomerId("123456");
    cashierRechargeCreateRequest.setGoodsDesc("ok");
    cashierRechargeCreateRequest.setDiscountableAmount(1);
    cashierRechargeCreateRequest.setOutBizNo("2324234242");
    cashierRechargeCreateRequest.setIsNeedSuccessJump(1);
    cashierRechargeCreateRequest.setTel("23424242");
    cashierRechargeCreateRequest.setSignType("MD5");
    cashierRechargeCreateRequest.setSign("234243fe22");
    cashierRechargeCreateRequest.setMchId(33423424224L);
    cashierRechargeCreateRequest.setPartnerId("323424242");
    cashierRechargeCreateRequest.setPayAmount(4);
    return cashierRechargeCreateRequest;
  }

}
