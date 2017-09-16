package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.pay.assetcenter.api.UnifiedPayService;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.customer.api.BankCardService;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.CardSixElementsDTO;
import com.youzan.pay.unified.cashier.api.request.SigningPrepayRequest;
import com.youzan.pay.unified.cashier.api.result.SigningPrepayResult;
import com.youzan.pay.unified.cashier.intergration.client.AssetCenterUnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.PayAccountManager;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

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
 * @author tao.ke Date: 2017/6/23 Time: 上午10:12
 */
public class CashierSigningPrepayHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierSigningPrepayHandler cashierSigningPrepayHandler;

  @InjectMocks
  @Resource
  private BankCardPayManager bankCardPayManager;

  @InjectMocks
  @Resource
  private AssetCenterUnifiedPayServiceClient assetCenterUnifiedPayServiceClient;

  @InjectMocks
  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @InjectMocks
  @Resource
  private PayAccountManager payAccountManager;

  @Mock
  private BankCardService merchantBankCardService;

  @Mock
  private UnifiedPayService unifiedPayService;

  @Mock
  private UserMerchantServiceClient userMerchantServiceClient;


  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoSix() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    cashierSigningPrepayHandler.validate(prepayRequest);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testValidateForPhoneMin() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("223");
    elementsDTO.setValidDate("14/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("187789889");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForIdCardNoMin() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
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
    elementsDTO.setIdCardNo("38989282938");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoPay() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
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

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoCardType() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    //elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("223");
    elementsDTO.setValidDate("12/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoIdCardNo() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
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
    //elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoName() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
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
    //elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoPhone() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
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
    //elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoCardNo() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    //elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("223");
    elementsDTO.setValidDate("12/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoCvv() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    //elementsDTO.setCcvCode("223");
    elementsDTO.setValidDate("12/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForInvalidCvv() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("2234");
    elementsDTO.setValidDate("12/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForInvalidDate() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("2234");
    elementsDTO.setValidDate("123/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateForNoDate() throws Exception {

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType("信用卡");
    elementsDTO.setCcvCode("2234");
    //elementsDTO.setValidDate("123/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.validate(prepayRequest);
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
    deeplink.put("missedParams", new String[]{"cvv", "cardNo"});
    detailResult.setDeepLinkInfo(deeplink);
    detailResult.setAssetDetailNo("12345678999998");
    payDetailResult.add(detailResult);
    payResponse.setResult(payResult);
    Mockito.when(unifiedPayService.multiPay(anyObject())).thenReturn(payResponse);

    // 绑卡到客户中心 mock
    DataResult<BoundBankCardResult> bindResult = new DataResult<>();
    bindResult.setSuccess(true);
    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setCardBindId(123456L);
    cardResult.setMobile("18667179836");
    bindResult.setData(cardResult);
    Mockito.when(merchantBankCardService.bindQuackPayCard(anyObject())).thenReturn(bindResult);

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
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

    UserMerchantDto dto = new UserMerchantDto(123L, 321L, "signkey");
    Mockito.when(userMerchantServiceClient.queryMchByUserId(Matchers.anyObject())).thenReturn(dto);

    SigningPrepayResult result = cashierSigningPrepayHandler.execute(prepayRequest);
    Assert.assertEquals("12345678999998",result.getTargetId());
  }

  @Test
  public void testBefore() throws Exception{

    SigningPrepayRequest prepayRequest = new SigningPrepayRequest();
    prepayRequest.setBankName("农业银行");
    prepayRequest.setBankCode("ABC");
    prepayRequest.setBuyerId("123456");
    prepayRequest.setCustomerId("0");
    prepayRequest.setCustomerType("0");
    prepayRequest.setPartnerId("123");
    prepayRequest.setPayTool(BankCardType.CREDIT_CARD.name());
    prepayRequest.setTradeDesc("商品描述测试");
    prepayRequest.setAcquireNo("88888888888");
    prepayRequest.setOutBizNo("E1234567890");

    CardSixElementsDTO elementsDTO = new CardSixElementsDTO();
    elementsDTO.setCardNo("12345678989898989");
    elementsDTO.setCardType(BankCardType.DEBIT_CARD.getDesc());
    elementsDTO.setCcvCode("223");
    elementsDTO.setValidDate("12/23");
    elementsDTO.setIdCardNo("389892829384930234");
    elementsDTO.setIdCardType("01");
    elementsDTO.setName("张三");
    elementsDTO.setPhone("18778988907");
    prepayRequest.setSixElements(elementsDTO);

    cashierSigningPrepayHandler.doBefore(prepayRequest);

    Assert.assertEquals(BankCardType.DEBIT_CARD.name(),prepayRequest.getPayTool());

  }

}