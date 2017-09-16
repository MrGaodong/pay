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
public class WxPayEnvFilterTest {
    @InjectMocks
    WxPayEnvFilter wxPayEnvFilter = new WxPayEnvFilter();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * 测试WX_JS屏蔽ALIPAY_WAP下
     */
    @Test
    public void TestAliPayFilter(){
        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayEnviorment("WX_JS");
        payToolFilterRequest.setPayChannel("ALIPAY_WAP");

        boolean result =wxPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,true);
    }

    /**
     *测试支付环境为WX_JS，屏蔽WX_H5
     */
    @Test
    public void TestAliPayFilter2(){

        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayEnviorment("WX_JS");
        payToolFilterRequest.setPayChannel("WX_H5");

        boolean result =wxPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,true);
    }

    /**
     *测试支付环境为"其他环境"，全部放行
     */
    @Test
    public void TestAliPayFilter3(){
        PayToolFilterRequest payToolFilterRequest = new PayToolFilterRequest();
        payToolFilterRequest.setPayEnviorment("ALIPAY");
        payToolFilterRequest.setPayChannel("WX_JS");

        boolean result =wxPayEnvFilter.doFilter(payToolFilterRequest);
        Assert.assertEquals(result,false);
    }
}
