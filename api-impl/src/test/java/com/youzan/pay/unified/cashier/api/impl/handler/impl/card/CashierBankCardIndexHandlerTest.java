package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.assetcenter.api.UnifiedPayService;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.customer.api.BankCardService;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.fundchannel.open.enums.ChannelBindStatus;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BankCardIndexRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardIndexResult;
import com.youzan.pay.unified.cashier.intergration.client.AssetCenterUnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayCardRecordCache;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;
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
import static org.mockito.Matchers.anyString;

/**
 * @author tao.ke Date: 2017/6/22 Time: 下午2:47
 */
public class CashierBankCardIndexHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierBankCardIndexHandler cashierBankCardIndexHandler;

  @InjectMocks
  @Resource
  private AssetCenterUnifiedPayServiceClient assetCenterUnifiedPayServiceClient;

  @InjectMocks
  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @InjectMocks
  @Resource
  private BankCardManager bankCardManager;

  @InjectMocks
  @Resource
  private BankCardPayManager bankCardPayManager;

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private BankCardService merchantBankCardService;

  @Mock
  private UnifiedPayService unifiedPayService;

  @Mock
  private PayCardRecordCache payCardRecordCache;

  @Mock
  private com.youzan.pay.fundchannel.open.api.BankCardService bankCardService;

  @Test(expected= IllegalArgumentException.class)
  public void testValidate() throws Exception {

    BankCardIndexRequest indexRequest = new BankCardIndexRequest();
    indexRequest.setPhone("18667178789");

    cashierBankCardIndexHandler.validate(indexRequest);

  }


  @Test
  public void testExecute() throws Exception {

    BankCardIndexRequest indexRequest = new BankCardIndexRequest();
    indexRequest.setPhone("18667178789");
    indexRequest.setBindId("123456789");
    indexRequest.setBuyerId("123456");
    indexRequest.setCustomerId("0");
    indexRequest.setCustomerType("0");
    indexRequest.setPartnerId("123");

    // MockCardList数据
    ListResult<BoundBankCardResult> cardResponse = new ListResult<>();
    cardResponse.setSuccess(true);

    List<BoundBankCardResult> results = new ArrayList<>();
    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setBankCode("CMBCHINA");
    cardResult.setBankName("招商银行");
    cardResult.setCardNoLastFour("9876");
    cardResult.setMobile("18667179835");
    cardResult.setCardBindId(123456789987L);
    cardResult.setCardType("CREDIT_CARD");
    results.add(cardResult);

    BoundBankCardResult cardResult1 = new BoundBankCardResult();
    cardResult1.setBankCode("ABC");
    cardResult1.setBankName("农业银行");
    cardResult1.setCardNoLastFour("9878");
    cardResult1.setMobile("18667179835");
    cardResult1.setCardBindId(213456789987L);
    cardResult1.setCardType("DEBIT_CARD");
    results.add(cardResult1);
    cardResponse.setData(results);

    Mockito.when(merchantBankCardService.queryBoundQuickPayCard(anyObject()))
        .thenReturn(cardResponse);

    // Mock bindId有效性
    BaseResponse<BankBindCheckResult> checkResponse = new BaseResponse<>();
    checkResponse.setSuccess(true);
    BankBindCheckResult checkResults = new BankBindCheckResult();

    List<BankBindCheckResult.Result> rs = new ArrayList<>();
    BankBindCheckResult.Result r = new BankBindCheckResult.Result();
    r.setWaterNo(123456789987L);
    r.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    BankBindCheckResult.Result r2 = new BankBindCheckResult.Result();
    r2.setWaterNo(213456789987L);
    r2.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    rs.add(r);
    rs.add(r2);
    checkResults.setResult(rs);
    checkResponse.setBizResult(checkResults);
    Mockito.when(bankCardService.checkBankBinding(anyObject())).thenReturn(checkResponse);

    // 缓存mock
    Mockito.when(payCardRecordCache.getBindId(anyString())).thenReturn("213456789987");

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

    BankCardIndexResult result = cashierBankCardIndexHandler.execute(indexRequest);
    Assert.assertNotNull(result.getCardList());
    Assert.assertNotNull(result.getBindCard());

    Assert.assertEquals("213456789987", result.getCardList().getCardList().get(0).getBindId());
    Assert.assertEquals(
        "https://img.yzcdn.cn/public_files/2017/06/21/abbe0a4ee9d868d5520af23e73df70d8.png",
        result.getCardList().getCardList().get(0).getLogo());
  }

  @Test
  public void testExecuteForCredit() throws Exception {

    BankCardIndexRequest indexRequest = new BankCardIndexRequest();
    indexRequest.setPhone("18667178789");
    indexRequest.setBindId("123456789");
    indexRequest.setBuyerId("123456");
    indexRequest.setCustomerId("0");
    indexRequest.setCustomerType("0");
    indexRequest.setPartnerId("123");

    // MockCardList数据
    ListResult<BoundBankCardResult> cardResponse = new ListResult<>();
    cardResponse.setSuccess(true);

    List<BoundBankCardResult> results = new ArrayList<>();
    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setBankCode("CMBCHINA");
    cardResult.setBankName("招商银行");
    cardResult.setCardNoLastFour("9876");
    cardResult.setMobile("18667179835");
    cardResult.setCardBindId(123456789987L);
    cardResult.setCardType("CREDIT_CARD");
    results.add(cardResult);

    BoundBankCardResult cardResult1 = new BoundBankCardResult();
    cardResult1.setBankCode("ABC");
    cardResult1.setBankName("农业银行");
    cardResult1.setCardNoLastFour("9878");
    cardResult1.setMobile("18667179835");
    cardResult1.setCardBindId(213456789987L);
    cardResult1.setCardType("DEBIT_CARD");
    results.add(cardResult1);
    cardResponse.setData(results);

    Mockito.when(merchantBankCardService.queryBoundQuickPayCard(anyObject()))
        .thenReturn(cardResponse);

    // Mock bindId有效性
    BaseResponse<BankBindCheckResult> checkResponse = new BaseResponse<>();
    checkResponse.setSuccess(true);
    BankBindCheckResult checkResults = new BankBindCheckResult();

    List<BankBindCheckResult.Result> rs = new ArrayList<>();
    BankBindCheckResult.Result r = new BankBindCheckResult.Result();
    r.setWaterNo(123456789987L);
    r.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    BankBindCheckResult.Result r2 = new BankBindCheckResult.Result();
    r2.setWaterNo(213456789987L);
    r2.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    rs.add(r);
    rs.add(r2);
    checkResults.setResult(rs);
    checkResponse.setBizResult(checkResults);
    Mockito.when(bankCardService.checkBankBinding(anyObject())).thenReturn(checkResponse);

    // 缓存mock
    Mockito.when(payCardRecordCache.getBindId(anyString())).thenReturn(null);

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

    BankCardIndexResult result = cashierBankCardIndexHandler.execute(indexRequest);
    Assert.assertNotNull(result.getCardList());
    Assert.assertNotNull(result.getBindCard());

    Assert.assertEquals("213456789987", result.getCardList().getCardList().get(1).getBindId());
    Assert.assertEquals(
        "https://img.yzcdn.cn/public_files/2017/06/21/abbe0a4ee9d868d5520af23e73df70d8.png",
        result.getCardList().getCardList().get(1).getLogo());
  }
}