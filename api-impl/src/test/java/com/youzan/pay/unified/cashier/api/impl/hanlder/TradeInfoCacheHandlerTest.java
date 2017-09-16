package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.unified.cashier.api.impl.handler.impl.TradeInfoCacheHandler;
import com.youzan.pay.unified.cashier.api.request.TradeInfoRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.TradeInfoResult;
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
public class TradeInfoCacheHandlerTest {
    @InjectMocks
    TradeInfoCacheHandler tradeInfoCacheHandler = new TradeInfoCacheHandler();
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
        TradeInfoRequest tradeInfoRequest = new TradeInfoRequest();
        Response<TradeInfoResult> tradeInfoResult =tradeInfoCacheHandler.handle(tradeInfoRequest);
        Assert.assertEquals(tradeInfoResult.getResult().getStatus(),"success");

    }
}
