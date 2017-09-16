package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.assetcenter.api.UnifiedPayService;
import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.intergration.BaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;

/**
 * @author tao.ke Date: 2017/6/27 Time: 下午4:25
 */
public class AssetCenterUnifiedPayServiceClientTest extends BaseTest {

  @InjectMocks
  @Resource
  private AssetCenterUnifiedPayServiceClient assetCenterUnifiedPayServiceClient;

  @Mock
  private UnifiedPayService unifiedPayService;

  @Test
  public void testMultiPaySucc() throws Exception {

    // 绑卡预支付mock
    Response<MultiPayResult> payResponse = new Response<>();
    payResponse.setSuccess(true);
    MultiPayResult payResult = new MultiPayResult();
    payResult.setOutBizNo("123456789");
    List<PayDetailResult> payDetailResult = new ArrayList<>();
    payResult.setPayDetailResult(payDetailResult);
    PayDetailResult detailResult = new PayDetailResult();
    Map<String, Object> deeplink = new HashMap<>();
    deeplink.put("missedParams", new String[]{"cvv", "cardNo"});
    detailResult.setDeepLinkInfo(deeplink);
    detailResult.setAssetDetailNo("12345678999998");
    payDetailResult.add(detailResult);
    payResponse.setResult(payResult);
    Mockito.when(unifiedPayService.multiPay(anyObject())).thenReturn(payResponse);

    MultiPayRequest request = new MultiPayRequest();

    MultiPayResult result = assetCenterUnifiedPayServiceClient.multiPay(request);

    Assert.assertNotNull(result);
    Assert.assertEquals(1, result.getPayDetailResult().size());

  }

  @Test(expected = CardBaseException.class)
  public void testMultiPayEx() throws Exception {

    // 绑卡预支付mock
    Mockito.when(unifiedPayService.multiPay(anyObject())).thenThrow(SocketException.class);
    MultiPayRequest request = new MultiPayRequest();

    MultiPayResult result = assetCenterUnifiedPayServiceClient.multiPay(request);

  }

  @Test(expected = CardBaseException.class)
  public void testMultiPayFalse() throws Exception {

    // 绑卡预支付mock
    Response<MultiPayResult> payResponse = new Response<>();
    payResponse.setSuccess(false);
    payResponse.setMsg("错误");

    Mockito.when(unifiedPayService.multiPay(anyObject())).thenReturn(payResponse);

    MultiPayRequest request = new MultiPayRequest();

    MultiPayResult result = assetCenterUnifiedPayServiceClient.multiPay(request);

  }

  @Test(expected = CardBaseException.class)
  public void testMultiPayFalseNull() throws Exception {

    // 绑卡预支付mock

    Mockito.when(unifiedPayService.multiPay(anyObject())).thenReturn(null);

    MultiPayRequest request = new MultiPayRequest();

    MultiPayResult result = assetCenterUnifiedPayServiceClient.multiPay(request);

  }

}