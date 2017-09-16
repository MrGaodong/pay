package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.unified.cashier.api.impl.handler.impl.TradeInfoCacheHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.TradeInfoCacheSelectHandler;
import com.youzan.pay.unified.cashier.api.request.TradeInfoListRequest;
import com.youzan.pay.unified.cashier.api.request.TradeInfoRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.TradeInfoListResult;
import com.youzan.pay.unified.cashier.api.result.TradeInfoResult;
import com.youzan.pay.unified.cashier.core.utils.model.CacheTradeInfo;
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
public class TradeInfoCacheSelectHandlerTest {
    @InjectMocks
    TradeInfoCacheSelectHandler tradeInfoCacheSelectHandler = new TradeInfoCacheSelectHandler();
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
    public void TestTradeInfoCache(){
        TradeInfoListRequest tradeInfoListRequest = new TradeInfoListRequest();
        tradeInfoListRequest.setSign("lalalasdas");
        CacheTradeInfo  cacheTradeInfo = new CacheTradeInfo();
        cacheTradeInfo.setSign("lalalala");
        Mockito.when(cacheService.get(Mockito.anyString(),Mockito.any())).thenReturn(cacheTradeInfo);
        Response<TradeInfoListResult> tradeInfoResult =tradeInfoCacheSelectHandler.handle(tradeInfoListRequest);
       Assert.assertEquals(tradeInfoResult.getResult().getSign(),"lalalala");
    }
}
