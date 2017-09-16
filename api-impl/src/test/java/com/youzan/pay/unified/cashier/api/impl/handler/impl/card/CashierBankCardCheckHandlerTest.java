package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;


import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.fundchannel.open.api.BankCardService;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BankCardInfoCheckRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardInfoCheckResult;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/22 Time: 上午9:15
 */
public class CashierBankCardCheckHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierBankCardCheckHandler cashierBankCardCheckHandler;

  @InjectMocks
  @Resource
  private BankCardManager bankCardManager;

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private BankCardService bankCardService;


  @Test
  public void testExecuteForSucc() throws Exception {

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    request.setCardNo("1234567890123456");
    request.setPartnerId("123321");

    BaseResponse<BankCardCheckResult> checkResponse = new BaseResponse<BankCardCheckResult>();
    BankCardCheckResult checkResult = new BankCardCheckResult();
    checkResult.setBankCode("CMB");
    checkResult.setBankName("招商银行");
    checkResult.setCardNo("1234567890123456");
    checkResult.setCardType(BankCardType.CREDIT_CARD.getCode());
    checkResult.setValid("1");
    checkResponse.setBizResult(checkResult);
    checkResponse.setSuccess(true);

    Mockito.when(bankCardService.checkCard(Matchers.anyObject())).thenReturn(checkResponse);

    BankCardInfoCheckResult result = cashierBankCardCheckHandler.execute(request);

    Assert.assertEquals("CMB",result.getBankCode());
    Assert.assertEquals("1234567890123456",result.getCardNo());
  }

  @Test(expected= IllegalArgumentException.class)
  public void testValidate() throws Exception{

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    request.setCardNo("1234560123456");
    request.setPartnerId("123321");

    cashierBankCardCheckHandler.validate(request);
  }

  @Test(expected= IllegalArgumentException.class)
  public void testValidateForCardNo() throws Exception{

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    //request.setCardNo("1234560123456");

    cashierBankCardCheckHandler.validate(request);
  }

  @Test(expected= IllegalArgumentException.class)
  public void testValidateForInvalidCardNo() throws Exception{

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    request.setCardNo("XXYY1234560123456");

    cashierBankCardCheckHandler.validate(request);
  }

  @Test(expected= IllegalArgumentException.class)
  public void testValidateForNoPartenerId() throws Exception{

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    request.setCardNo("12345601234561234");
    //request.setPartnerId("80000000002");

    cashierBankCardCheckHandler.validate(request);
  }

  @Test
  public void testValidateForSucc() throws Exception{

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    request.setCardNo("12345601234561234");
    request.setPartnerId("123321");

    cashierBankCardCheckHandler.validate(request);
  }

}