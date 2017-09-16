package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.fundchannel.open.api.BankCardService;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BindCardConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.CardSixElementsDTO;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayCardRecordCache;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/22 Time: 下午9:17
 */
public class CashierBindPayConfirmHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierBindPayConfirmHandler cashierBindPayConfirmHandler;

  @InjectMocks
  @Resource
  private BankCardPayManager bankCardPayManager;

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @InjectMocks
  @Resource
  private PayCardRecordCache payCardRecordCache;

  @Mock
  private BankCardService bankCardService;

  @Mock
  private RedisCacheManager redisCacheManager;

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoOrderNo() throws Exception {

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    //request.setOrderNo("198909890");

    cashierBindPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoTargetId() throws Exception {

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    //request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("198909890");

    cashierBindPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoBindId() throws Exception {

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    //request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("198909890");

    cashierBindPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateInvalidCVV() throws Exception {

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("198909890");

    CardSixElementsDTO dto = new CardSixElementsDTO();
    dto.setCcvCode("12345");
    request.setSixElements(dto);

    cashierBindPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateInvalidDate() throws Exception {

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("198909890");
    CardSixElementsDTO dto = new CardSixElementsDTO();
    dto.setCcvCode("123");
    dto.setValidDate("01/234");
    request.setSixElements(dto);

    cashierBindPayConfirmHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidate() throws Exception {

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    //request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("198909890");

    cashierBindPayConfirmHandler.validate(request);
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

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("12345678");
    request.setUserIp("192.168.1.1");
    ConfirmPayResult payResult = cashierBindPayConfirmHandler.execute(request);

    Assert.assertTrue(payResult.isPaySuccess());
  }

  @Test
  public void testExecuteForKeyCache() throws Exception {

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

    BindCardConfirmPayRequest request = new BindCardConfirmPayRequest();
    request.setBindId("1234");
    request.setTargetId("7898979239281");
    request.setVerificationCode("123");
    request.setPartnerId("9989898989");
    request.setOrderNo("12345678");
    request.setUserIp("192.168.1.1");
    request.setBuyerId("111111");
    ConfirmPayResult payResult = cashierBindPayConfirmHandler.execute(request);

    Assert.assertTrue(payResult.isPaySuccess());

  }

}