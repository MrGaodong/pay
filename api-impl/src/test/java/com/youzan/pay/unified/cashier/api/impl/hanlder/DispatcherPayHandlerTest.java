package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayRequestConvertor;
import com.youzan.pay.unified.cashier.api.impl.enums.PageOperationEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.DispatcherPayHandler;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.result.PayResult;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import com.alibaba.fastjson.JSON;

import org.apache.commons.collections.map.HashedMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author twb
 * @version DispatcherPayHandlerTest.java, v 0.1 2017-01-12 15:19
 */
public class DispatcherPayHandlerTest {

    @Mock
    private UnifiedPayServiceClient unifiedPayServiceClient;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private DispatcherPayHandler dispatcherPayHandler = new DispatcherPayHandler();

    @Before
    public void setUp() throws Exception {
      MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_ECARD() {
      PayRequest request = new PayRequest();
      request.setPayTool("ECARD");
      request.setAcquireNo("1234567890");
      request.setPayerId("123456");
      request.setOutBizNo("098765");
      request.setMchId("12345");
      request.setPartnerId("parnterId");
      request.setTradeDesc("tradeDesc");
      request.setPayAmount(1000L);

      MultiPayRequest multiPayRequest = PayRequestConvertor.convert(request, "12345");

      Response<MultiPayResult> response = new Response<>();
      response.setSuccess(true);

      Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);
      com.youzan.pay.unified.cashier.api.response.Response<PayResult>
          resp = dispatcherPayHandler.handle(request);

      Assert.assertNotNull(resp.getResult());
      Assert.assertTrue(resp.isSuccess());
    }

    @Test
    public void test_WX_JS() {
      PayRequest request = new PayRequest();
      request.setPayTool("WX_JS");
      request.setAcquireNo("1234567890");
      request.setPayerId("123456");
      request.setOutBizNo("098765");
      request.setMchId("12345");
      request.setPartnerId("parnterId");
      request.setTradeDesc("tradeDesc");
      request.setPayAmount(1000L);

      List<PayDetailResult> list = new ArrayList<>();
      PayDetailResult result = new PayDetailResult();
      result.setDeepLinkInfo(new HashedMap() {
        {
          put("key", "value");
        }
      });
      list.add(result);

      Response<MultiPayResult> response = new Response<>();
      response.setSuccess(true);
      MultiPayResult multiPayResult = new MultiPayResult();
      multiPayResult.setPayDetailResult(list);
      response.setResult(multiPayResult);

      Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);
      com.youzan.pay.unified.cashier.api.response.Response<PayResult>
          resp = dispatcherPayHandler.handle(request);

      Assert.assertNotNull(resp.getResult());
      Assert.assertTrue(resp.isSuccess());
      Assert.assertEquals(PageOperationEnum.CALL_CASHIER.name(), resp.getResult().getOperation());
      Assert.assertEquals(JSON.toJSONString(new HashedMap() {
        {
          put("key", "value");
        }
      }), resp.getResult().getDeepLinkInfo());
    }

    @Test
    public void test_paytool_not_exist() {
      PayRequest request = new PayRequest();
      request.setPayTool("WX_WAP");
      request.setAcquireNo("1234567890");
      request.setPayerId("123456");
      request.setOutBizNo("098765");
      request.setMchId("12345");
      request.setPartnerId("parnterId");
      request.setTradeDesc("tradeDesc");
      request.setPayAmount(1000L);

      List<PayDetailResult> list = new ArrayList<>();
      PayDetailResult result = new PayDetailResult();
      result.setDeepLinkInfo(new HashedMap() {
        {
          put("key", "value");
        }
      });
      list.add(result);

      Response<MultiPayResult> response = new Response<>();
      response.setSuccess(true);
      MultiPayResult multiPayResult = new MultiPayResult();
      multiPayResult.setPayDetailResult(list);
      response.setResult(multiPayResult);

      Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);
      try {
        com.youzan.pay.unified.cashier.api.response.Response<PayResult>
            resp = dispatcherPayHandler.handle(request);
      } catch (IllegalArgumentException e) {
        Assert.assertTrue(true);
      }
    }

    @Test
    public void test_() {
      PayRequest request = new PayRequest();
      request.setPayTool("ECARD");
      request.setAcquireNo("1234567890");
      request.setPayerId("123456");
      request.setOutBizNo("098765");
      request.setMchId("12345");
      request.setPartnerId("parnterId");
      request.setTradeDesc("tradeDesc");
      request.setPayAmount(1000L);
      request.setAcceptPrice(1);

      Response<MultiPayResult> response = new Response<>();
      response.setSuccess(false);
      response.setResultCode("112202003");

      Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);
      Mockito.when(unifiedPayServiceClient.getPayAmount(Mockito.anyString())).thenReturn(100L);
      com.youzan.pay.unified.cashier.api.response.Response<PayResult>
          resp = dispatcherPayHandler.handle(request);

      Assert.assertNotNull(resp.getResult());
      Assert.assertTrue(resp.isSuccess());
    }

    @Test
    public void test() {
      PayRequest request = new PayRequest();
      request.setPayTool("ECARD");
      request.setAcquireNo("1234567890");
      request.setPayerId("123456");
      request.setOutBizNo("098765");
      request.setMchId("12345");
      request.setPartnerId("parnterId");
      request.setTradeDesc("tradeDesc");
      request.setPayAmount(1000L);
      request.setAcceptPrice(1);

      Response<MultiPayResult> response = new Response<>();
      response.setSuccess(false);
      response.setResultCode("112202002");

      Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);
      Mockito.when(unifiedPayServiceClient.getPayAmount(Mockito.anyString())).thenReturn(100L);
      com.youzan.pay.unified.cashier.api.response.Response<PayResult>
          resp = dispatcherPayHandler.handle(request);

      Assert.assertNotNull(resp.getResult());
      Assert.assertTrue(resp.isSuccess());
      Assert.assertEquals(PageOperationEnum.REPEATE_PAY.name(), resp.getResult().getOperation());
    }
}