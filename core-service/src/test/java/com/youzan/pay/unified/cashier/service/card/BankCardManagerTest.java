package com.youzan.pay.unified.cashier.service.card;

import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.fundchannel.open.enums.ChannelBindStatus;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckResult;
import com.youzan.pay.unified.cashier.api.request.BankCardInfoCheckRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardUnbindRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardInfoCheckResult;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.BaseTest;
import com.youzan.pay.unified.cashier.service.cache.PayCardRecordCache;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

/**
 * @author tao.ke Date: 2017/6/26 Time: 下午2:51
 */
public class BankCardManagerTest extends BaseTest {

  @InjectMocks
  @Resource
  private BankCardManager bankCardManager;

  @Mock
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Mock
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @Mock
  private PayCardRecordCache payCardRecordCache;


  @Test
  public void testQueryCardListNoUser() throws Exception {

    // MockCardList数据
    List<BoundBankCardResult> results = new ArrayList<>();

    Mockito.when(merchantBankCardServiceClient.queryCardList(Matchers.anyObject()))
        .thenReturn(results);

    List<BankBindCheckResult.Result> rs = new ArrayList<>();
    BankBindCheckResult.Result r = new BankBindCheckResult.Result();
    r.setWaterNo(123456789987L);
    r.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    BankBindCheckResult.Result r2 = new BankBindCheckResult.Result();
    r2.setWaterNo(213456789987L);
    r2.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    rs.add(r);
    rs.add(r2);

    Mockito.when(channelBankCardServiceClient.checkBindIds(anyObject())).thenReturn(rs);

    // 缓存mock
    Mockito.when(payCardRecordCache.getBindId(anyString())).thenReturn("213456789987");

    BankCardListQueryRequest request = new BankCardListQueryRequest();
    //request.setBuyerId("1234567890");
    request.setMchId("123456778");
    request.setPartnerId("123123");
    request.setCustomerType("9");
    //request.setCustomerId("123456");
    BankCardListQueryResult result = bankCardManager.queryCardList(request);

    Assert.assertNotNull(result);
    Assert.assertNull(result.getCardList());

  }

  @Test
  public void testQueryCardListNoUserAndCid() throws Exception {

    // MockCardList数据
    List<BoundBankCardResult> results = new ArrayList<>();
    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setBankCode("CMB");
    cardResult.setBankName("招商银行");
    cardResult.setCardNoLastFour("9876");
    cardResult.setMobile("18667179835");
    cardResult.setCardBindId(123456789987L);
    cardResult.setCardType("信用卡");
    results.add(cardResult);

    BoundBankCardResult cardResult1 = new BoundBankCardResult();
    cardResult1.setBankCode("ABC");
    cardResult1.setBankName("中国农业银行");
    cardResult1.setCardNoLastFour("9878");
    cardResult1.setMobile("18667179835");
    cardResult1.setCardBindId(213456789987L);
    cardResult1.setCardType("储蓄卡");
    results.add(cardResult1);

    Mockito.when(merchantBankCardServiceClient.queryCardList(Matchers.anyObject()))
        .thenReturn(results);

    List<BankBindCheckResult.Result> rs = new ArrayList<>();
    BankBindCheckResult.Result r = new BankBindCheckResult.Result();
    r.setWaterNo(123456789987L);
    r.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    BankBindCheckResult.Result r2 = new BankBindCheckResult.Result();
    r2.setWaterNo(213456789987L);
    r2.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    rs.add(r);
    rs.add(r2);

    Mockito.when(channelBankCardServiceClient.checkBindIds(anyObject())).thenReturn(rs);

    // 缓存mock
    Mockito.when(payCardRecordCache.getBindId(anyString())).thenReturn("213456789987");

    BankCardListQueryRequest request = new BankCardListQueryRequest();
    //request.setBuyerId("1234567890");
    request.setMchId("123456778");
    request.setPartnerId("123123");
    request.setCustomerType("9");
    //request.setCustomerId("123456");
    BankCardListQueryResult result = bankCardManager.queryCardList(request);

    Assert.assertNotNull(result);
    Assert.assertNull(result.getCardList());

  }

  @Test
  public void testQueryCardList() throws Exception {

    // MockCardList数据
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
    cardResult1.setBankName("中国农业银行");
    cardResult1.setCardNoLastFour("9878");
    cardResult1.setMobile("18667179835");
    cardResult1.setCardBindId(213456789987L);
    cardResult1.setCardType("DEBIT_CARD");
    results.add(cardResult1);

    Mockito.when(merchantBankCardServiceClient.queryCardList(Matchers.anyObject()))
        .thenReturn(results);

    List<BankBindCheckResult.Result> rs = new ArrayList<>();
    BankBindCheckResult.Result r = new BankBindCheckResult.Result();
    r.setWaterNo(123456789987L);
    r.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    BankBindCheckResult.Result r2 = new BankBindCheckResult.Result();
    r2.setWaterNo(213456789987L);
    r2.setBindStaus(ChannelBindStatus.BIND_AVAILABLE);

    rs.add(r);
    rs.add(r2);

    Mockito.when(channelBankCardServiceClient.checkBindIds(anyObject())).thenReturn(rs);

    // 缓存mock
    Mockito.when(payCardRecordCache.getBindId(anyString())).thenReturn("213456789987");

    BankCardListQueryRequest request = new BankCardListQueryRequest();
    request.setBuyerId("1234567890");
    request.setMchId("123456778");
    request.setPartnerId("123123");
    request.setCustomerType("9");
    request.setCustomerId("123456");
    BankCardListQueryResult result = bankCardManager.queryCardList(request);

    Assert.assertNotNull(result);
    Assert.assertEquals("ABC", result.getCardList().get(0).getBankCode());

  }

  @Test
  public void testQueryCardValidatorInfo() throws Exception {

    BankCardInfoCheckRequest request = new BankCardInfoCheckRequest();
    request.setCardNo("1234567890123456");

    BankCardCheckResult checkResult = new BankCardCheckResult();
    checkResult.setBankCode("CMBCHINA");
    checkResult.setBankName("招商银行");
    checkResult.setCardNo("1234567890123456");
    checkResult.setCardType(2);
    checkResult.setValid("1");

    Mockito.when(channelBankCardServiceClient.checkBankCard(Matchers.anyObject()))
        .thenReturn(checkResult);

    BankCardInfoCheckResult result = bankCardManager.queryCardValidatorInfo(request);

    Assert.assertEquals("CMBCHINA", result.getBankCode());
    Assert.assertEquals(true, result.isValid());


  }

  @Test
  public void testUnbindBankCard() throws Exception {

    Mockito.when(merchantBankCardServiceClient.unbindCard(Mockito.anyObject())).thenReturn(true);

    BankCardUnbindRequest request = new BankCardUnbindRequest();
    request.setBindId("123456789");
    request.setBuyerId("1234");
    request.setCustomerId("0");
    request.setCustomerType("0");
    request.setPartnerId("898989");

    boolean ret = bankCardManager.unbindBankCard(request);
    Assert.assertTrue(ret);
  }

  @Test
  public void testUnbindBankCardNoUser() throws Exception {

    Mockito.when(merchantBankCardServiceClient.unbindCard(Mockito.anyObject())).thenReturn(true);

    BankCardUnbindRequest request = new BankCardUnbindRequest();
    request.setBindId("123456789");
    //request.setBuyerId("1234");
    request.setCustomerId("0");
    request.setCustomerType("0");
    request.setPartnerId("898989");

    boolean ret = bankCardManager.unbindBankCard(request);
    Assert.assertTrue(ret);
  }

}