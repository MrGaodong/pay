package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.unified.cashier.api.impl.handler.impl.GoodsInfoHandler;
import com.youzan.pay.unified.cashier.api.impl.model.GoodsInfo;
import com.youzan.pay.unified.cashier.api.request.GoodsInfoRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.GoodsInfoResult;
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
public class GoodsInfoHandlerTest {
    @InjectMocks
    GoodsInfoHandler goodsInfoHandler = new GoodsInfoHandler();

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
    public void TestGoodsInfoHandler(){
        GoodsInfoRequest goodsInfoRequest = new GoodsInfoRequest();
        goodsInfoRequest.setAcquireNo("1232312");
        GoodsInfo goodsInfo = null;
        Mockito.when(cacheService.get(Mockito.anyString(), Mockito.any())).thenReturn(goodsInfo);
        Response<GoodsInfoResult> goodsInfoResult =goodsInfoHandler.handle(goodsInfoRequest);
        Assert.assertNotNull(goodsInfoResult);

    }
}
