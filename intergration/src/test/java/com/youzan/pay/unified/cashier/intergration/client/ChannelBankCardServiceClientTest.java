package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.fundchannel.open.api.BankCardService;
import com.youzan.pay.fundchannel.open.enums.ChannelBindStatus;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmResult;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsResult;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.intergration.BaseTest;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/6/27 Time: 下午4:56
 */
public class ChannelBankCardServiceClientTest extends BaseTest {

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private BankCardService bankCardService;

  @Test
  public void testCheckBankCardSucc() throws Exception {

    BankCardCheckRequest request = new BankCardCheckRequest();
    request.setCardNo("1234567890123456");
    request.setBizPartnerCode("11111111");

    BaseResponse<BankCardCheckResult> checkResponse = new BaseResponse<BankCardCheckResult>();
    BankCardCheckResult checkResult = new BankCardCheckResult();
    checkResult.setBankCode("CMBCHAINA");
    checkResult.setBankName("招商银行");
    checkResult.setCardNo("1234567890123456");
    checkResult.setCardType(BankCardType.CREDIT_CARD.getCode());
    checkResult.setValid("1");
    checkResponse.setBizResult(checkResult);
    checkResponse.setSuccess(true);

    Mockito.when(bankCardService.checkCard(Matchers.anyObject())).thenReturn(checkResponse);

    BankCardCheckResult result = channelBankCardServiceClient.checkBankCard(request);
    Assert.assertEquals("CMBCHAINA", result.getBankCode());
    Assert.assertEquals(BankCardType.CREDIT_CARD.getCode(), result.getCardType());
  }

  @Test(expected = CardBaseException.class)
  public void testCheckBankCardFail() throws Exception {

    BankCardCheckRequest request = new BankCardCheckRequest();
    request.setCardNo("1234567890123456");
    request.setBizPartnerCode("11111111");

    BaseResponse<BankCardCheckResult> checkResponse = new BaseResponse<BankCardCheckResult>();
    checkResponse.setSuccess(false);

    Mockito.when(bankCardService.checkCard(Matchers.anyObject())).thenReturn(checkResponse);
    BankCardCheckResult result = channelBankCardServiceClient.checkBankCard(request);

  }

  @Test(expected = CardBaseException.class)
  public void testCheckBankCardFailNull() throws Exception {

    BankCardCheckRequest request = new BankCardCheckRequest();
    request.setCardNo("1234567890123456");
    request.setBizPartnerCode("11111111");

    BaseResponse<BankCardCheckResult> checkResponse = new BaseResponse<BankCardCheckResult>();
    checkResponse.setBizResult(null);
    checkResponse.setSuccess(true);
    checkResponse.setErrorMsg("返回失败");

    Mockito.when(bankCardService.checkCard(Matchers.anyObject())).thenReturn(checkResponse);

    BankCardCheckResult result = channelBankCardServiceClient.checkBankCard(request);
  }

  @Test(expected = CardBaseException.class)
  public void testCheckBankCardNull() throws Exception {

    BankCardCheckRequest request = new BankCardCheckRequest();
    request.setCardNo("1234567890123456");
    request.setBizPartnerCode("11111111");

    Mockito.when(bankCardService.checkCard(Matchers.anyObject())).thenReturn(null);

    BankCardCheckResult result = channelBankCardServiceClient.checkBankCard(request);
  }

  @Test(expected = CardBaseException.class)
  public void testCheckBankCardEx() throws Exception {

    BankCardCheckRequest request = new BankCardCheckRequest();
    request.setCardNo("1234567890123456");
    request.setBizPartnerCode("11111111");

    Mockito.when(bankCardService.checkCard(Matchers.anyObject())).thenThrow(SocketException.class);

    BankCardCheckResult result = channelBankCardServiceClient.checkBankCard(request);
  }

  @Test
  public void testSendConfirmSmsSucc() throws Exception {

    // Mock测试
    BaseResponse<SendSmsResult> response = new BaseResponse<>();
    response.setSuccess(true);
    SendSmsResult result = new SendSmsResult();
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    response.setBizResult(result);
    // Mock结束
    Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenReturn(response);

    SendSmsRequest request = new SendSmsRequest();
    SendSmsResult result1 = channelBankCardServiceClient.sendConfirmSms(request);

    Assert.assertEquals("18667179835", result1.getPhone());
    Assert.assertEquals("123456789", result1.getPayOrderNo());

  }

  @Test(expected = CardBaseException.class)
  public void testSendConfirmSmsFail() throws Exception {

    // Mock测试
    BaseResponse<SendSmsResult> response = new BaseResponse<>();
    response.setSuccess(false);
    // Mock结束
    Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenReturn(response);

    SendSmsRequest request = new SendSmsRequest();
    SendSmsResult result1 = channelBankCardServiceClient.sendConfirmSms(request);

  }

  @Test(expected = CardBaseException.class)
  public void testSendConfirmSmsNull() throws Exception {

    Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenReturn(null);

    SendSmsRequest request = new SendSmsRequest();
    SendSmsResult result1 = channelBankCardServiceClient.sendConfirmSms(request);

  }

  @Test(expected = CardBaseException.class)
  public void testSendConfirmSmsFailNull() throws Exception {

    // Mock测试
    BaseResponse<SendSmsResult> response = new BaseResponse<>();
    response.setSuccess(true);
    response.setBizResult(null);
    // Mock结束
    Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenReturn(response);

    SendSmsRequest request = new SendSmsRequest();
    SendSmsResult result1 = channelBankCardServiceClient.sendConfirmSms(request);

  }

  @Test(expected = CardBaseException.class)
  public void testSendConfirmSmsEx() throws Exception {

    Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenThrow(SocketException.class);

    SendSmsRequest request = new SendSmsRequest();
    SendSmsResult result1 = channelBankCardServiceClient.sendConfirmSms(request);


  }

  @Test
  public void testPayConfirmSucc() throws Exception {

    // Mock测试
    BaseResponse<BankPayConfirmResult> response = new BaseResponse<>();
    response.setSuccess(true);
    BankPayConfirmResult result = new BankPayConfirmResult();
    result.setBankCode("CMB");
    result.setBankName("招商银行");
    result.setCardLast("9876");
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    result.setPaySuccess(true);
    response.setBizResult(result);
    // Mock结束

    Mockito.when(bankCardService.payConfirm(Matchers.anyObject())).thenReturn(response);

    BankPayConfirmRequest request = new BankPayConfirmRequest();
    BankPayConfirmResult result1 = channelBankCardServiceClient.payConfirm(request);
    Assert.assertEquals("123456789", result1.getPayOrderNo());
  }

  @Test(expected = CardBaseException.class)
  public void testPayConfirmFail() throws Exception {

    // Mock测试
    BaseResponse<BankPayConfirmResult> response = new BaseResponse<>();
    response.setSuccess(false);
    // Mock结束

    Mockito.when(bankCardService.payConfirm(Matchers.anyObject())).thenReturn(response);

    BankPayConfirmRequest request = new BankPayConfirmRequest();
    BankPayConfirmResult result1 = channelBankCardServiceClient.payConfirm(request);
  }

  @Test(expected = CardBaseException.class)
  public void testPayConfirmFailNull() throws Exception {

    // Mock测试
    BaseResponse<BankPayConfirmResult> response = new BaseResponse<>();
    response.setSuccess(true);
    response.setBizResult(null);
    // Mock结束

    Mockito.when(bankCardService.payConfirm(Matchers.anyObject())).thenReturn(response);

    BankPayConfirmRequest request = new BankPayConfirmRequest();
    BankPayConfirmResult result1 = channelBankCardServiceClient.payConfirm(request);
  }

  @Test(expected = CardBaseException.class)
  public void testPayConfirmNull() throws Exception {

    Mockito.when(bankCardService.payConfirm(Matchers.anyObject())).thenReturn(null);

    BankPayConfirmRequest request = new BankPayConfirmRequest();
    BankPayConfirmResult result1 = channelBankCardServiceClient.payConfirm(request);
  }

  @Test(expected = CardBaseException.class)
  public void testPayConfirmEx() throws Exception {

    Mockito.when(bankCardService.payConfirm(Matchers.anyObject())).thenThrow(SocketException.class);

    BankPayConfirmRequest request = new BankPayConfirmRequest();
    BankPayConfirmResult result1 = channelBankCardServiceClient.payConfirm(request);
  }

  @Test
  public void testCheckBindIdsSucc() throws Exception {

    BankBindCheckRequest request = new BankBindCheckRequest();
    request.setBizPartnerCode("111111111");
    request.setWaterNoList(Lists.newArrayList(1234L, 5678L));

    BaseResponse<BankBindCheckResult> response = new BaseResponse<>();
    response.setSuccess(true);

    BankBindCheckResult result = new BankBindCheckResult();
    List<BankBindCheckResult.Result> results = new ArrayList<>();

    BankBindCheckResult.Result a = new BankBindCheckResult.Result();
    a.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);
    a.setWaterNo(1234L);
    results.add(a);

    BankBindCheckResult.Result b = new BankBindCheckResult.Result();
    b.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);
    b.setWaterNo(5678L);
    results.add(b);
    result.setResult(results);
    response.setBizResult(result);

    Mockito.when(bankCardService.checkBankBinding(Matchers.anyObject())).thenReturn(response);

    List<BankBindCheckResult.Result> checks = channelBankCardServiceClient.checkBindIds(request);
    Assert.assertEquals(2, checks.size());
  }

  @Test(expected = CardBaseException.class)
  public void testCheckBindIdsFail() throws Exception {

    BankBindCheckRequest request = new BankBindCheckRequest();
    request.setBizPartnerCode("111111111");
    request.setWaterNoList(Lists.newArrayList(1234L, 5678L));

    BaseResponse<BankBindCheckResult> response = new BaseResponse<>();
    response.setSuccess(false);

    Mockito.when(bankCardService.checkBankBinding(Matchers.anyObject())).thenReturn(response);

    List<BankBindCheckResult.Result> checks = channelBankCardServiceClient.checkBindIds(request);

  }

  @Test(expected = CardBaseException.class)
  public void testCheckBindIdsNull() throws Exception {

    BankBindCheckRequest request = new BankBindCheckRequest();
    request.setBizPartnerCode("111111111");
    request.setWaterNoList(Lists.newArrayList(1234L, 5678L));

    Mockito.when(bankCardService.checkBankBinding(Matchers.anyObject())).thenReturn(null);

    List<BankBindCheckResult.Result> checks = channelBankCardServiceClient.checkBindIds(request);

  }

  @Test(expected = CardBaseException.class)
  public void testCheckBindIdsFailNull() throws Exception {

    BankBindCheckRequest request = new BankBindCheckRequest();
    request.setBizPartnerCode("111111111");
    request.setWaterNoList(Lists.newArrayList(1234L, 5678L));

    BaseResponse<BankBindCheckResult> response = new BaseResponse<>();
    response.setSuccess(true);
    response.setBizResult(null);

    Mockito.when(bankCardService.checkBankBinding(Matchers.anyObject())).thenReturn(response);

    List<BankBindCheckResult.Result> checks = channelBankCardServiceClient.checkBindIds(request);

  }

  @Test(expected = CardBaseException.class)
  public void testCheckBindIdsEx() throws Exception {

    BankBindCheckRequest request = new BankBindCheckRequest();
    request.setBizPartnerCode("111111111");
    request.setWaterNoList(Lists.newArrayList(1234L, 5678L));

    Mockito.when(bankCardService.checkBankBinding(Matchers.anyObject())).thenThrow(SocketException.class);

    List<BankBindCheckResult.Result> checks = channelBankCardServiceClient.checkBindIds(request);

  }

}