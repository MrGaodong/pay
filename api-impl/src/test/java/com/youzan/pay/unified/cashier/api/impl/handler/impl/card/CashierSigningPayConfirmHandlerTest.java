package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.fundchannel.open.api.BankCardService;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.SigningConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/23 Time: 上午10:13
 */
public class CashierSigningPayConfirmHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierSigningPayConfirmHandler cashierSigningPayConfirmHandler;

  @InjectMocks
  @Resource
  private BankCardPayManager bankCardPayManager;

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private BankCardService bankCardService;

  @Test
  public void testValidateSucc() throws Exception {

    SigningConfirmPayRequest request = new SigningConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("4975");

    cashierSigningPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoCode() throws Exception {

    SigningConfirmPayRequest request = new SigningConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    //request.setOrderNo("4975");

    cashierSigningPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoTargetId() throws Exception {

    SigningConfirmPayRequest request = new SigningConfirmPayRequest();
    request.setBindId("1234");
    //request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("4975");

    cashierSigningPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoOrderNo() throws Exception {

    SigningConfirmPayRequest request = new SigningConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    //request.setOrderNo("4975");

    cashierSigningPayConfirmHandler.validate(request);
  }

  @Test
  public void testExecute() throws Exception {

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

    SigningConfirmPayRequest request = new SigningConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("12345678");
    request.setUserIp("192.168.1.1");

    ConfirmPayResult payResult = cashierSigningPayConfirmHandler.execute(request);

    Assert.assertTrue(payResult.isPaySuccess());
  }

}