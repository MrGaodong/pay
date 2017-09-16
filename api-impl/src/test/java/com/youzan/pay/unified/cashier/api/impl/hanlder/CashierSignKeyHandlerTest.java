package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierSignKeyHandler;
import com.youzan.pay.unified.cashier.api.request.CashierPayTypeSearchRequest;
import com.youzan.pay.unified.cashier.api.request.CashierSignKeyRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierPayTypeSearchResult;
import com.youzan.pay.unified.cashier.api.result.CashierSignKeyResult;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Created by xielina on 2017/6/26.
 */
public class CashierSignKeyHandlerTest {
    @InjectMocks
    CashierSignKeyHandler cashierSignKeyHandler=new CashierSignKeyHandler();

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
    public void TestCashierSignkeyHandler() throws Exception {
        CashierSignKeyRequest cashierSignKeyRequest = buildCashierSignKeyRequest();
        when(signKeyServiceClient.getSignKey(anyObject())).thenReturn("d7618d3c1936b5c08f99993a928f6a1e");
        Response<CashierSignKeyResult> result = cashierSignKeyHandler.handle(cashierSignKeyRequest);
        Assert.assertEquals(result.getResult().getSignKey(),"d7618d3c1936b5c08f99993a928f6a1e");
    }

    private CashierSignKeyRequest buildCashierSignKeyRequest(){
        CashierSignKeyRequest cashierSignKeyRequest = new CashierSignKeyRequest();
        cashierSignKeyRequest.setPartnerId("820000000947");
        return cashierSignKeyRequest;
    }


}
