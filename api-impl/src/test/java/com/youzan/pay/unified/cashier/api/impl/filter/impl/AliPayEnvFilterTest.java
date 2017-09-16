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
public class AliPayEnvFilterTest {
    @InjectMocks
    AliPayEnvFilter aliPayEnvFilter = new AliPayEnvFilter();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * 测试ALIPAY下屏蔽WX_JS
     */
    @Test
    public void TestAliPayFilter(){
        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayEnviorment("ALIPAYWAP");
        payToolFilterRequest.setPayChannel("WX_JS");

        boolean result =aliPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,true);
    }

    /**
     *测试支付环境为""，屏蔽WX_JS
     */
    @Test
    public void TestAliPayFilter2(){

        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayEnviorment("");
        payToolFilterRequest.setPayChannel("WX_JS");

        boolean result =aliPayEnvFilter.doFilter(payToolFilterRequest);
        //Assert.assertEquals(result,true);
    }

    /**
     *测试支付环境为"其他环境"，全部放行
     */
    @Test
    public void TestAliPayFilter3(){
        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayEnviorment("WX_JS");
        payToolFilterRequest.setPayChannel("WX_JS");

        boolean result =aliPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,false);
    }
}
