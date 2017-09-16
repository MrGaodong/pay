package com.youzan.pay.unified.cashier.api.impl.filter.impl;

import com.youzan.pay.unified.cashier.api.request.group.PayToolFilterRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Created by xielina on 2017/6/29.
 */
public class CardPayEnvFilterTest {

    @InjectMocks
    CardPayEnvFilter cardPayEnvFilter = new CardPayEnvFilter();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * 屏蔽零钱渠道
     */
    @Test
    public void TestAliPayFilter(){
        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayChannel("BALANCE");

        boolean result =cardPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,true);
    }

    /**
     *放行其他渠道
     */
    @Test
    public void TestAliPayFilter2(){
        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayChannel("WX_JS");

        boolean result =cardPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,false);
    }

}
