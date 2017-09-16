package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.fundchannel.open.api.BankCardService;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BankCardPaySmsSendRequest;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.cache.SmsSendRecordCache;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/22 Time: 下午9:46
 */
public class CashierSmsSendHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierSmsSendHandler cashierSmsSendHandler;

  @InjectMocks
  @Resource
  private BankCardManager bankCardManager;

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private BankCardService bankCardService;

  @InjectMocks
  @Resource
  private SmsSendRecordCache smsSendRecordCache;

  @Mock
  private RedisCacheManager redisCacheManager;

  @Test
  public void testvalidateSucc() throws Exception {

    BankCardPaySmsSendRequest sendRequest = new BankCardPaySmsSendRequest();
    sendRequest.setSmsType("1");
    sendRequest.setTargetId("123456789");
    sendRequest.setBuyerId("123987");

    cashierSmsSendHandler.validate(sendRequest);

  }

  @Test(expected = IllegalArgumentException.class)
  public void validate() throws Exception {

    BankCardPaySmsSendRequest sendRequest = new BankCardPaySmsSendRequest();
    sendRequest.setSmsType("1");
    //sendRequest.setTargetId("123456789");
    sendRequest.setBuyerId("123987");

    cashierSmsSendHandler.validate(sendRequest);

  }

  @Test
  public void testExecuteForSucc() throws Exception {

    // Mock测试
    BaseResponse<SendSmsResult> response = new BaseResponse<>();
    response.setSuccess(true);
    SendSmsResult result = new SendSmsResult();
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    response.setBizResult(result);
// Mock结束

    Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenReturn(response);

    BankCardPaySmsSendRequest sendRequest = new BankCardPaySmsSendRequest();
    sendRequest.setSmsType("1");
    sendRequest.setTargetId("123456789");
    sendRequest.setBuyerId("123987");
    Boolean ret = cashierSmsSendHandler.execute(sendRequest);

    Assert.assertTrue(ret);
  }

  @Test(expected = CardBaseException.class)
  public void testExecuteForFail() throws Exception {

    // Mock测试
    BaseResponse<SendSmsResult> response = new BaseResponse<>();
    response.setSuccess(true);
    SendSmsResult result = new SendSmsResult();
    result.setPayOrderNo("123456789");
    result.setPhone("18667179835");
    response.setBizResult(result);
// Mock结束

    //Mockito.when(bankCardService.sendSms(Matchers.anyObject())).thenReturn(response);
    Mockito.when(redisCacheManager.get("C_SSR_123987_123456789",String.class)).thenReturn("1234");

    BankCardPaySmsSendRequest sendRequest = new BankCardPaySmsSendRequest();
    sendRequest.setSmsType("1");
    sendRequest.setTargetId("123456789");
    sendRequest.setBuyerId("123987");
    Boolean ret = cashierSmsSendHandler.execute(sendRequest);

    Assert.assertFalse(ret);
  }

}