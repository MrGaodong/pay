/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.result.QueryAcquireResult;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierOrderStatusSearchHandler;
import com.youzan.pay.unified.cashier.api.request.CashierOrderStatusSearchRequest;
import com.youzan.pay.unified.cashier.api.result.CashierOrderStatusSearchResult;
import com.youzan.pay.unified.cashier.intergration.client.CashierOrderStatusSearchClient;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @author wulonghui
 * @version CashierOrderStatusSearchHandlerTest.java, v 0.1 2017-04-25 16:58
 */
public class CashierOrderStatusSearchHandlerTest {

  @InjectMocks
  CashierOrderStatusSearchHandler
      cashierPayTypeSearchHandler =
      new CashierOrderStatusSearchHandler();

  @Mock
  private CashierOrderStatusSearchClient cashierOrderStatusSearchClient;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void TestQueryOrderStatusSuccess() {
    CashierOrderStatusSearchRequest
        cashierPayTypeSearchRequest =
        buildCashierOrderStatusSearchRequest();
    QueryAcquireResult queryAcquireResult = buildQueryAcquireResult();

    when(cashierOrderStatusSearchClient.query(anyObject())).thenReturn(queryAcquireResult);

    com.youzan.pay.unified.cashier.api.response.Response<CashierOrderStatusSearchResult>
        result =
        cashierPayTypeSearchHandler
            .handle(cashierPayTypeSearchRequest);

    Assert.assertEquals(result.getResult().getAcquireNo(), "123456");
  }

  private QueryAcquireResult buildQueryAcquireResult() {
    QueryAcquireResult queryAcquireResult = new QueryAcquireResult();
    queryAcquireResult.setStatus(AcquireQueryStatus.BUYER_PAIED);
    queryAcquireResult.setAcquireNo("123456");
    return queryAcquireResult;
  }

  private CashierOrderStatusSearchRequest buildCashierOrderStatusSearchRequest() {
    CashierOrderStatusSearchRequest
        cashierOrderStatusSearchRequest =
        new CashierOrderStatusSearchRequest();
    cashierOrderStatusSearchRequest.setAcquireNo("123456");
    cashierOrderStatusSearchRequest.setOutBizNo("E1234");
    return cashierOrderStatusSearchRequest;
  }
}
