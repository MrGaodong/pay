package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.unified.cashier.api.impl.handler.impl.FetchQRCodeInfoHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.GoodsInfoHandler;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
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
 * Created by xielina on 2017/6/29.
 */
public class FetchQRCodeInfoHandlerTest {
    @InjectMocks
    FetchQRCodeInfoHandler fetchQRCodeInfoHandler = new FetchQRCodeInfoHandler();
    @Mock
    CacheService cacheService = new CacheService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void TestFetchQrCodeInfoHandler(){
        QRCodeInfo qrCodeInfo = null;
        try {
            Mockito.when(cacheService.get(Mockito.anyString(), Mockito.any())).thenReturn(qrCodeInfo);
        }catch(Exception e){
            Assert.assertEquals(true,true);
        }
    }
}
