package com.youzan.pay.unified.cashier.api.impl.hanlder;

import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayRequestConvertor;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.KuahaoPayHandler;
import com.youzan.pay.unified.cashier.api.request.KuahaoPayRequest;
import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.result.KuahaoPayResult;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.apache.commons.collections.map.HashedMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by liumeng on 2017/7/18.
 */
public class KuahaoPayHandlerTest {

  @Mock
  private UnifiedPayServiceClient unifiedPayServiceClient;

  @Mock
  private CacheService cacheService;

  @InjectMocks
  private KuahaoPayHandler kuahaoPayHandler = new KuahaoPayHandler();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void test_NATIVE_SUCCESS() {

    /**
     * cacheService Mock
     */
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

    Mockito.when(cacheService.get(Mockito.anyString(), Mockito.anyObject())).thenReturn(multiPayRequest);

    /**
     * unifiedPayServiceClient Mock
     */
    Map<String, Object> deepInfo = new HashedMap();
    deepInfo.put("awake_pay_url", "https://www.hao123.com/");

    PayDetailResult payDetailResult = new PayDetailResult();
    payDetailResult.setDeepLinkInfo(deepInfo);

    List<PayDetailResult> list = new LinkedList<PayDetailResult>();
    list.add(payDetailResult);

    MultiPayResult multiPayResult = new MultiPayResult();
    multiPayResult.setPayDetailResult(list);

    Response<MultiPayResult> response = new Response<>();
    response.setSuccess(true);
    response.setResult(multiPayResult);

    Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);

    /**
     * kuahaoPayHandler InjectMock
     */
    KuahaoPayRequest kuahaoPayRequest = new KuahaoPayRequest();
    kuahaoPayRequest.setAcquireNo("1234567890");

    com.youzan.pay.unified.cashier.api.response.Response<KuahaoPayResult>
        resp = kuahaoPayHandler.handle(kuahaoPayRequest);

    /**
     * junit Result
     */
    Assert.assertNotNull(resp.getResult());
    Assert.assertTrue(resp.isSuccess());
    System.out.print(resp.getResult());
    System.out.print(resp.getResult().getQrCodeInfoResult().getContent());
  }

  @Test
  public void test_NATIVE_FAIL() {

    /**
     * cacheService Mock
     */
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

    Mockito.when(cacheService.get(Mockito.anyString(), Mockito.anyObject())).thenReturn(multiPayRequest);

    /**
     * unifiedPayServiceClient Mock
     */
    Map<String, Object> deepInfo = new HashedMap();
    deepInfo.put("wakePayUrl", "https://www.hao123.com/");

    PayDetailResult payDetailResult = new PayDetailResult();
    payDetailResult.setDeepLinkInfo(deepInfo);

    List<PayDetailResult> list = new LinkedList<PayDetailResult>();
    list.add(payDetailResult);

    MultiPayResult multiPayResult = new MultiPayResult();
    multiPayResult.setPayDetailResult(list);

    Response<MultiPayResult> response = new Response<>();
    response.setSuccess(false);
    response.setResult(multiPayResult);

    Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);

    /**
     * kuahaoPayHandler Mock
     */
    KuahaoPayRequest kuahaoPayRequest = new KuahaoPayRequest();
    kuahaoPayRequest.setAcquireNo("1234567890");

    com.youzan.pay.unified.cashier.api.response.Response<KuahaoPayResult>
        resp = kuahaoPayHandler.handle(kuahaoPayRequest);

    /**
     * junit Result
     */
    Assert.assertFalse(resp.isSuccess());
    System.out.print(resp.getMsg());
  }

  @Test
  public void test_NATIVE_FAIL_MULTIREQUEST() {

    /**
     * cacheService Mock
     */
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

    Mockito.when(cacheService.get(Mockito.anyString(), Mockito.anyObject())).thenReturn(null);

    /**
     * unifiedPayServiceClient Mock
     */
    Map<String, Object> deepInfo = new HashedMap();
    deepInfo.put("wakePayUrl", "https://www.hao123.com/");

    PayDetailResult payDetailResult = new PayDetailResult();
    payDetailResult.setDeepLinkInfo(null);

    List<PayDetailResult> list = new LinkedList<PayDetailResult>();
    list.add(payDetailResult);

    MultiPayResult multiPayResult = new MultiPayResult();
    multiPayResult.setPayDetailResult(null);

    Response<MultiPayResult> response = new Response<>();
    response.setSuccess(false);
    response.setResult(multiPayResult);

    Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);

    /**
     * kuahaoPayHandler Mock
     */
    KuahaoPayRequest kuahaoPayRequest = new KuahaoPayRequest();
    kuahaoPayRequest.setAcquireNo("1234567890");

    com.youzan.pay.unified.cashier.api.response.Response<KuahaoPayResult>
        resp = kuahaoPayHandler.handle(kuahaoPayRequest);

    /**
     * junit Result
     */
    Assert.assertFalse(resp.isSuccess());
    System.out.print(resp.getMsg());
  }

  @Test
  public void test_NATIVE_FAIL_PAYDETAILRESULT() {

    /**
     * cacheService Mock
     */
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

    Mockito.when(cacheService.get(Mockito.anyString(), Mockito.anyObject())).thenReturn(multiPayRequest);

    /**
     * unifiedPayServiceClient Mock
     */
    Map<String, Object> deepInfo = new HashedMap();
    deepInfo.put("wakePayUrl", "https://www.hao123.com/");

    PayDetailResult payDetailResult = new PayDetailResult();
    payDetailResult.setDeepLinkInfo(deepInfo);

    List<PayDetailResult> list = new LinkedList<PayDetailResult>();
    list.add(payDetailResult);

    MultiPayResult multiPayResult = new MultiPayResult();
    multiPayResult.setPayDetailResult(null);

    Response<MultiPayResult> response = new Response<>();
    response.setSuccess(false);
    response.setResult(multiPayResult);

    Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);

    /**
     * kuahaoPayHandler Mock
     */
    KuahaoPayRequest kuahaoPayRequest = new KuahaoPayRequest();
    kuahaoPayRequest.setAcquireNo("1234567890");

    com.youzan.pay.unified.cashier.api.response.Response<KuahaoPayResult>
        resp = kuahaoPayHandler.handle(kuahaoPayRequest);

    /**
     * junit Result
     */
    Assert.assertFalse(resp.isSuccess());
    System.out.print(resp.getMsg());
  }

  @Test
  public void test_NATIVE_FAIL_DEEPLINK() {

    /**
     * cacheService Mock
     */
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

    Mockito.when(cacheService.get(Mockito.anyString(), Mockito.anyObject())).thenReturn(multiPayRequest);

    /**
     * unifiedPayServiceClient Mock
     */
    Map<String, Object> deepInfo = new HashedMap();
    deepInfo.put("wakePayUrl", "https://www.hao123.com/");

    PayDetailResult payDetailResult = new PayDetailResult();
    payDetailResult.setDeepLinkInfo(null);

    List<PayDetailResult> list = new LinkedList<PayDetailResult>();
    list.add(payDetailResult);

    MultiPayResult multiPayResult = new MultiPayResult();
    multiPayResult.setPayDetailResult(null);

    Response<MultiPayResult> response = new Response<>();
    response.setSuccess(false);
    response.setResult(multiPayResult);

    Mockito.when(unifiedPayServiceClient.multiPay(Mockito.anyObject())).thenReturn(response);

    /**
     * kuahaoPayHandler Mock
     */
    KuahaoPayRequest kuahaoPayRequest = new KuahaoPayRequest();
    kuahaoPayRequest.setAcquireNo("1234567890");

    com.youzan.pay.unified.cashier.api.response.Response<KuahaoPayResult>
        resp = kuahaoPayHandler.handle(kuahaoPayRequest);

    /**
     * junit Result
     */
    Assert.assertFalse(resp.isSuccess());
    System.out.print(resp.getMsg());
  }

}
