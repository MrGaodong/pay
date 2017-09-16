package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayRequestConvertor;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.QRCodePayHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.request.QRCodePayRequest;
import com.youzan.pay.unified.cashier.api.result.QRCodePayResult;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xielina on 2017/6/26.
 */
public class QRCodePayHandlerTest {
    @InjectMocks
    private QRCodePayHandler qrCodePayHandler = new QRCodePayHandler();

    @Mock
    private UnifiedPayServiceClient unifiedPayServiceClient;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void TestQRCodePayHandler(){
        Response<UnifiedOrderCreatingResult> responseOfCreate =new Response<>();
        responseOfCreate.setResult(buildUnifiedOrderCreatingResult());

        Response<MultiPayResult> responseOfMultiPay = new Response<>();
        responseOfMultiPay.setSuccess(true);
        responseOfMultiPay.setResult(buildMultiPayResult());

        Mockito.when(unifiedPayServiceClient.multiPay((Mockito.anyObject()))).thenReturn(responseOfMultiPay);

        com.youzan.pay.unified.cashier.api.response.Response<QRCodePayResult> result = qrCodePayHandler.handle(buildQRCodePayRequest());

        Assert.assertEquals(result.getResult().getAcquireNo(),"12234343313");



    }

    private QRCodePayRequest buildQRCodePayRequest(){
        QRCodePayRequest request = new QRCodePayRequest();
        request.setPayTool("WX_H5");
        request.setWxSubOpenId("wxsubopenid");
        request.setAcquireNo("12234343313");
        request.setDiscountableAmount(8);
        request.setOutBizNo("1223321");
        request.setMchId("342342342");
        request.setPartnerId("434144141");
        request.setPartnerNotifyUrl("http://www.baidu.com");
        request.setPayAmount(1);
        request.setTradeDesc("交易描述");
        request.setPartnerNotifyUrl("http://www.baidu.com");
        request.setPartnerReturnUrl("http://www.baidu.com");
        return request;
    }

    private UnifiedOrderCreatingResult buildUnifiedOrderCreatingResult(){
        UnifiedOrderCreatingResult unifiedOrderCreatingResult = new UnifiedOrderCreatingResult();
        unifiedOrderCreatingResult.setRepeated(false);
        unifiedOrderCreatingResult.setAcquireNo("12234343313");
        unifiedOrderCreatingResult.setOutBizNo("1223321");
        unifiedOrderCreatingResult.setStatus(AcquireQueryStatus.CREATE);
        return unifiedOrderCreatingResult;
    }
    private MultiPayResult buildMultiPayResult(){
        MultiPayResult multiPayResult = new MultiPayResult();
        multiPayResult.setOutBizNo("1223321");
        multiPayResult.setAcquireNo("12234343313");
        List<PayDetailResult> payDetailResult = new ArrayList<>();
        PayDetailResult payDetailResult1 = new PayDetailResult();
        payDetailResult1.setPayTool("WX_JS");
        payDetailResult1.setAssetDetailNo("AssetDetailNo");
        payDetailResult1.setChannelSettleNo("ChannelSettleNo");
        payDetailResult1.setPayDate(new Date());
        payDetailResult.add(payDetailResult1);
        multiPayResult.setPayDetailResult(payDetailResult);
        return multiPayResult;
    }
}
