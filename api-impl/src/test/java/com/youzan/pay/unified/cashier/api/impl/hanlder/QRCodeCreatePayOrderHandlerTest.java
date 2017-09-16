package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.DispatcherPayHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.QRCodeCreatePayOrderHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodeCreateOrderRequest;
import com.youzan.pay.unified.cashier.api.result.QRCodeCreateOrderResult;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by xielina on 2017/6/29.
 */
public class QRCodeCreatePayOrderHandlerTest {

    @Mock
    private UnifiedOrderServiceClient unifiedOrderServiceClient;

    @InjectMocks
    QRCodeCreatePayOrderHandler qrCodeCreatePayOrderHandler = new QRCodeCreatePayOrderHandler();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void TestQRCodeCreatePayOrder(){
        Response<UnifiedOrderCreatingResult> responseOfCreate = new Response<>();
        UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();
        unifiedOrderCreatingResult.setAcquireNo("hehehehe");
        unifiedOrderCreatingResult.setStatus(AcquireQueryStatus.PAYING);
        responseOfCreate.setResult(unifiedOrderCreatingResult);

        Mockito.when(unifiedOrderServiceClient.create(Mockito.anyObject())).thenReturn(responseOfCreate);
        com.youzan.pay.unified.cashier.api.response.Response<QRCodeCreateOrderResult>  qrCodeCreateOrderResult
                = qrCodeCreatePayOrderHandler.handle(buildQRCodeCreateOrderRequest());
        Assert.assertNotNull(qrCodeCreateOrderResult);

    }
    private QRCodeCreateOrderRequest buildQRCodeCreateOrderRequest(){
        QRCodeCreateOrderRequest request = new QRCodeCreateOrderRequest();
        request.setOutBizNo("1223321");
        request.setMchId("342342342");
        request.setPartnerId("434144141");
        request.setPayAmount(1);
        request.setTradeDesc("交易描述");
        request.setPartnerReturnUrl("http://www.baidu.com");
        request.setCreateTime(Long.toString(System.currentTimeMillis()));
        return request;
    }

}
