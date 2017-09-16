package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.customer.api.BankCardService;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.fundchannel.open.enums.ChannelBindStatus;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayCardRecordCache;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

/**
 * @author tao.ke Date: 2017/6/22 Time: 下午5:58
 */
public class CashierBankCardListHandlerTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierBankCardListHandler cashierBankCardListHandler;

  @InjectMocks
  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @InjectMocks
  @Resource
  private BankCardManager bankCardManager;

  @Mock
  private BankCardService merchantBankCardService;

  @Mock
  private PayCardRecordCache payCardRecordCache;

  @Mock
  private com.youzan.pay.fundchannel.open.api.BankCardService bankCardService;

  @InjectMocks
  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Test
  public void testValidateSucc() throws Exception {

    BankCardListQueryRequest request = new BankCardListQueryRequest();

    request.setBuyerId("1234");
    request.setCustomerId("0");
    request.setCustomerType("0");
    request.setMchId("123456778");
    request.setPartnerId("123123");

    cashierBankCardListHandler.validate(request);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidate() throws Exception {

    BankCardListQueryRequest request = new BankCardListQueryRequest();
    request.setMchId("123456778");
    request.setPartnerId("123123");

    cashierBankCardListHandler.validate(request);

  }

  @Test
  public void testExecute() throws Exception {


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

    BankCardListQueryRequest request = new BankCardListQueryRequest();
    request.setBuyerId("1234567890");
    request.setMchId("123456778");
    request.setPartnerId("123123");
    request.setCustomerType("9");
    request.setCustomerId("123456");

    BankCardListQueryResult result = cashierBankCardListHandler.execute(request);

    Assert.assertNotNull(result);
    Assert.assertEquals("ABC",result.getCardList().get(0).getBankCode());
  }

}