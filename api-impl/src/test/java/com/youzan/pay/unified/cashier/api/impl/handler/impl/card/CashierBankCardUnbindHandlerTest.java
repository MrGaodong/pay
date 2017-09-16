package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.customer.api.BankCardService;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BankCardUnbindRequest;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/22 Time: 下午9:01
 */
public class CashierBankCardUnbindHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierBankCardUnbindHandler cashierBankCardUnbindHandler;

  @InjectMocks
  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @InjectMocks
  @Resource
  private BankCardManager bankCardManager;

  @Mock
  private BankCardService merchantBankCardService;

  @Test(expected = IllegalArgumentException.class)
  public void testValidateNoBindId() throws Exception {

    BankCardUnbindRequest request = new BankCardUnbindRequest();
    //request.setBindId("123456789");
    request.setBuyerId("1234");
    request.setCustomerId("0");
    request.setCustomerType("0");
    request.setPartnerId("898989");

    cashierBankCardUnbindHandler.validate(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidate() throws Exception {

    BankCardUnbindRequest request = new BankCardUnbindRequest();
    request.setBindId("123456789");
    //request.setBuyerId("1234");
    //request.setCustomerId("0");
    request.setCustomerType("0");
    request.setPartnerId("898989");

    cashierBankCardUnbindHandler.validate(request);
  }

  @Test
  public void testValidateForSucc() throws Exception {

    BankCardUnbindRequest request = new BankCardUnbindRequest();
    request.setBindId("123456789");
    request.setBuyerId("1234");
    request.setCustomerId("0");
    request.setCustomerType("0");
    request.setPartnerId("898989");

    cashierBankCardUnbindHandler.validate(request);
  }


  @Test
  public void testExecute() throws Exception {

    BankCardUnbindRequest request = new BankCardUnbindRequest();
    request.setBindId("123456789");
    request.setBuyerId("1234");
    request.setCustomerId("0");
    request.setCustomerType("0");
    request.setPartnerId("898989");

    DataResult<Boolean> mockRet = new DataResult<>();
    mockRet.setData(true);
    mockRet.setSuccess(true);
    Mockito.when(merchantBankCardService.unbindQuickPayCard(Matchers.anyObject())).thenReturn(mockRet);

    boolean ret = cashierBankCardUnbindHandler.execute(request);
    Assert.assertTrue(ret);


  }

}