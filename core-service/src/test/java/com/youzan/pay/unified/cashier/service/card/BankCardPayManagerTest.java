package com.youzan.pay.unified.cashier.service.card;

import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmResult;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsResult;
import com.youzan.pay.unified.cashier.api.request.BankCardPaySmsSendRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardPrepayRequest;
import com.youzan.pay.unified.cashier.api.request.CardSixElementsDTO;
import com.youzan.pay.unified.cashier.api.request.SigningConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.SigningPrepayRequest;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.api.result.SigningPrepayResult;
import com.youzan.pay.unified.cashier.intergration.client.AssetCenterUnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.BaseTest;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyObject;

/**
 * @author tao.ke Date: 2017/6/26 Time: 下午3:22
 */
public class BankCardPayManagerTest extends BaseTest {

  @InjectMocks
  @Resource
  private BankCardPayManager bankCardPayManager;

  @Mock
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private AssetCenterUnifiedPayServiceClient assetCenterUnifiedPayServiceClient;

  @Mock
  private MerchantBankCardServiceClient merchantBankCardServiceClient;


  @Test
  public void testSigningPrepay() throws Exception {

    // 绑卡预支付mock
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

    Mockito.when(assetCenterUnifiedPayServiceClient.multiPay(anyObject())).thenReturn(payResult);

    // 绑卡到客户中心 mock
    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setCardBindId(123456L);
    Mockito.when(merchantBankCardServiceClient.bindBankCard(anyObject())).thenReturn(cardResult);

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("中国农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("223");
    elementsDTO.setValidDate("12/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    SigningPrepayResult result = bankCardPayManager.signingPrepay(prepayRequest);
    Assert.assertEquals("123456", result.getBindId());
    Assert.assertEquals("12345678999998", result.getTargetId());

  }

  @Test
  public void testBindCardPrepay() throws Exception {

    // 绑卡预支付mock
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

    Mockito.when(assetCenterUnifiedPayServiceClient.multiPay(anyObject())).thenReturn(payResult);

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

    BindCardPrepayResult prepayResult = bankCardPayManager.bindCardPrepay(prepayRequest);
    Assert.assertEquals("12345678999998", prepayResult.getTargetId());

    List<String> missedParams = Lists.newArrayList("cvv", "cardNo");
    Assert.assertArrayEquals(new String[]{"cvv", "cardNo"},
                             prepayResult.getCompleteProperties().toArray());
  }

  @Test
  public void testSigningPayConfirm() throws Exception {

    // Mock测试
    BankPayConfirmResult result = new BankPayConfirmResult();
    result.setBankCode("CMB");
    result.setBankName("招商银行");
    result.setCardLast("9876");
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    result.setPaySuccess(true);
    // Mock结束

    Mockito.when(channelBankCardServiceClient.payConfirm(Matchers.anyObject())).thenReturn(result);

    SigningConfirmPayRequest request = new SigningConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("12345678");
    request.setUserIp("192.168.1.1");

    ConfirmPayResult payResult = bankCardPayManager.signingPayConfirm(request);

    Assert.assertTrue(payResult.isPaySuccess());

  }

  @Test
  public void testBindCardPayConfirm() throws Exception {

    // Mock测试
    BankPayConfirmResult result = new BankPayConfirmResult();
    result.setBankCode("CMB");
    result.setBankName("招商银行");
    result.setCardLast("9876");
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    result.setPaySuccess(true);
    // Mock结束

    Mockito.when(channelBankCardServiceClient.payConfirm(Matchers.anyObject())).thenReturn(result);

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("12345678");
    request.setUserIp("192.168.1.1");
    ConfirmPayResult payResult = bankCardPayManager.bindCardPayConfirm(request);

    Assert.assertTrue(payResult.isPaySuccess());
  }

  @Test
  public void testSendConfirmSms() throws Exception {

    // Mock测试
    SendSmsResult result = new SendSmsResult();
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    // Mock结束

    Mockito.when(channelBankCardServiceClient.sendConfirmSms(Matchers.anyObject()))
        .thenReturn(result);

    BankCardPaySmsSendRequest sendRequest = new BankCardPaySmsSendRequest();
    sendRequest.setSmsType("1");
    sendRequest.setTargetId("123456789");
    sendRequest.setBuyerId("123987");
    bankCardPayManager.sendConfirmSms(sendRequest);

  }

}