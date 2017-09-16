package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierSinglePayHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.GenPayQRCodeHandler;
import com.youzan.pay.unified.cashier.api.request.GenPayQRCodeRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.service.cache.CacheService;
import com.youzan.pay.unified.cashier.service.cache.SerialidGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Created by xielina on 2017/6/26.
 */
public class GenPayQRCodeHandlerTest {
    @InjectMocks
    private GenPayQRCodeHandler genPayQRCodeHandler=new GenPayQRCodeHandler();

    @Mock
    private CacheService cacheService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void TestGenPayQRCodeHandler(){
        GenPayQRCodeRequest request = buildGenPayQRCodeRequest();
        QRCodeInfo qrCodeInfo = buildQrCodeInfo();
        when( cacheService.get(Mockito.anyString(), Mockito.any())).thenReturn(qrCodeInfo);
        Response<QRCodeInfoResult> result = genPayQRCodeHandler.handle(request);
        Assert.assertEquals(result.getResult().getCodeId(),"4341441411223321");

    }
    @Test
    public void TestGenPayQRCodeHandler2(){
        GenPayQRCodeRequest request = buildGenPayQRCodeRequest();
        when( cacheService.get(Mockito.anyString(), Mockito.any())).thenReturn(null);
        Response<QRCodeInfoResult> result = genPayQRCodeHandler.handle(request);
        Assert.assertEquals(result.getResult().getCodeId(),"4341441411223321");

    }

    private QRCodeInfo buildQrCodeInfo(){
        QRCodeInfo qrCodeInfo = new QRCodeInfo();
        qrCodeInfo.setOutBizNo("1223321");
        qrCodeInfo.setCurrencyCode("CNY");
        qrCodeInfo.setGoodsDesc("测试");
        qrCodeInfo.setMchId("342342342");
        qrCodeInfo.setPartnerId("434144141");
        qrCodeInfo.setPartnerNotifyUrl("http://www.baidu.com");
        qrCodeInfo.setPayAmount(1);
        qrCodeInfo.setTradeDesc("交易描述");
        qrCodeInfo.setPartnerNotifyUrl("http://www.baidu.com");
        qrCodeInfo.setBizAction("1");
        qrCodeInfo.setBizMode("1");
        qrCodeInfo.setBizProd("1");
        qrCodeInfo.setBizAction("1");
        return qrCodeInfo;
    }
    private GenPayQRCodeRequest buildGenPayQRCodeRequest(){
        GenPayQRCodeRequest request= new GenPayQRCodeRequest();

        request.setOutBizNo("1223321");
        request.setCurrencyCode("CNY");
        request.setGoodsDesc("测试");
        request.setMchId("342342342");
        request.setPartnerId("434144141");
        request.setPartnerNotifyUrl("http://www.baidu.com");
        request.setPayAmount(1);
        request.setTradeDesc("交易描述");
        request.setPartnerNotifyUrl("http://www.baidu.com");
        request.setBizAction("1");
        request.setBizMode("1");
        request.setBizProd("1");
        request.setBizAction("1");
        return request;

    }
}
