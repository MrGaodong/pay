package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.assetcenter.api.UnifiedPayService;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BindCardPrepayRequest;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.intergration.client.AssetCenterUnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyObject;

/**
 * @author tao.ke Date: 2017/6/22 Time: 下午10:16
 */
public class CashierBindPrepayHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierBindPrepayHandler cashierBindPrepayHandler;

  @InjectMocks
  @Resource
  private BankCardPayManager bankCardPayManager;

  @InjectMocks
  @Resource
  private AssetCenterUnifiedPayServiceClient assetCenterUnifiedPayServiceClient;

  @Mock
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @Mock
  private UnifiedPayService unifiedPayService;

  @Test(expected = IllegalArgumentException.class)
  public void testvalidateNoBindId() throws Exception {

    BindCardPrepayRequest prepayRequest = new BindCardPrepayRequest();
    prepayRequest.setPhone("18667178789");
    //prepayRequest.setBindId("123456789");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    cashierBindPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoPhone() throws Exception {

    BindCardPrepayRequest prepayRequest = new BindCardPrepayRequest();
    //prepayRequest.setPhone("18667178789");
    prepayRequest.setBindId("123456789");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    cashierBindPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidate() throws Exception {

    BindCardPrepayRequest prepayRequest = new BindCardPrepayRequest();
    prepayRequest.setPhone("18667178789");
    prepayRequest.setBindId("123456789");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    cashierBindPrepayHandler.validate(prepayRequest);
  }

  @Test
  public void testExecute() throws Exception {

    // 绑卡预支付mock
    Response<MultiPayResult> payResponse = new Response<>();
    payResponse.setSuccess(true);
    MultiPayResult payResult = new MultiPayResult();
    payResult.setOutBizNo("123456789");
    List<PayDetailResult> payDetailResult = new ArrayList<>();
    payResult.setPayDetailResult(payDetailResult);
    PayDetailResult detailResult = new PayDetailResult();
    Map<String, Object> deeplink = new HashMap<>();
    deeplink.put("missedParams", Lists.newArrayList("cvv", "cardNo"));
    detailResult.setDeepLinkInfo(deeplink);
    detailResult.setAssetDetailNo("12345678999998");
    payDetailResult.add(detailResult);
    payResponse.setResult(payResult);
    Mockito.when(unifiedPayService.multiPay(anyObject())).thenReturn(payResponse);

    BindCardPrepayRequest prepayRequest = new BindCardPrepayRequest();
    prepayRequest.setPhone("186****8789");
    prepayRequest.setBindId("123456789");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    List<BoundBankCardResult> cardMockResults = Lists.newArrayList();
    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setCardBindId(123456789L);
    cardResult.setMobile("18667179836");
    cardMockResults.add(cardResult);
    Mockito.when(merchantBankCardServiceClient.queryCardList(anyObject())).thenReturn(cardMockResults);

    BindCardPrepayResult prepayResult = cashierBindPrepayHandler.execute(prepayRequest);
    Assert.assertEquals("12345678999998", prepayResult.getTargetId());

    List<String> missedParams = Lists.newArrayList("cvv", "cardNo");
    Assert.assertArrayEquals(new String[]{"cvv", "cardNo"}, prepayResult.getCompleteProperties().toArray());

  }

  @Test
  public void testDoBefore() throws Exception{

    BindCardPrepayRequest prepayRequest = new BindCardPrepayRequest();
    prepayRequest.setPhone("18667178789");
    prepayRequest.setBindId("123456789");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");
    prepayRequest.setCardType("储蓄卡");
    prepayRequest.setPayTool("CREDIT_CARD");
    prepayRequest.setOutBizNo("E12345");
    prepayRequest.setTradeDesc("测试商品");
    prepayRequest.setAcquireNo("12340987");

    cashierBindPrepayHandler.doBefore(prepayRequest);

    Assert.assertEquals("DEBIT_CARD",prepayRequest.getPayTool());
  }

}