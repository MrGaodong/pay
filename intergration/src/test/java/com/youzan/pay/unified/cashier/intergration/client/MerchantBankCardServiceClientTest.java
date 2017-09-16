package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.customer.api.BankCardService;
import com.youzan.pay.customer.api.request.BindCardRequest;
import com.youzan.pay.customer.api.request.QueryBoundCardRequest;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.intergration.BaseTest;

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
import static org.mockito.Matchers.anyObject;

/**
 * @author tao.ke Date: 2017/6/27 Time: 下午7:56
 */
public class MerchantBankCardServiceClientTest extends BaseTest {

  @InjectMocks
  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @Mock
  private BankCardService merchantBankCardService;

  @Test
  public void testQueryCardList() throws Exception {

    // MockCardList数据
    ListResult<BoundBankCardResult> cardResponse = new ListResult<>();
    cardResponse.setSuccess(true);

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
    cardResponse.setData(results);

    Mockito.when(merchantBankCardService.queryBoundQuickPayCard(anyObject()))
        .thenReturn(cardResponse);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    List<BoundBankCardResult> results1 = merchantBankCardServiceClient.queryCardList(request);
    Assert.assertEquals(2, results1.size());
    Assert.assertEquals(123456789987L, results1.get(0).getCardBindId().longValue());
  }

  @Test
  public void testQueryCardListFail() throws Exception {

    // MockCardList数据
    ListResult<BoundBankCardResult> cardResponse = new ListResult<>();
    cardResponse.setSuccess(false);

    Mockito.when(merchantBankCardService.queryBoundQuickPayCard(anyObject())).thenReturn(cardResponse);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    List<BoundBankCardResult> results1 = merchantBankCardServiceClient.queryCardList(request);
    Assert.assertEquals(0,results1.size());

  }

  @Test(expected = CardBaseException.class)
  public void testQueryCardListNull() throws Exception {

    // MockCardList数据
    Mockito.when(merchantBankCardService.queryBoundQuickPayCard(anyObject())).thenReturn(null);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    List<BoundBankCardResult> results1 = merchantBankCardServiceClient.queryCardList(request);

  }

  @Test(expected = CardBaseException.class)
  public void testQueryCardListEx() throws Exception {

    // MockCardList数据
    Mockito.when(merchantBankCardService.queryBoundQuickPayCard(anyObject())).thenThrow(SocketException.class);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    List<BoundBankCardResult> results1 = merchantBankCardServiceClient.queryCardList(request);

  }

  @Test
  public void testUnbindCard() throws Exception {

    DataResult<Boolean> mockRet = new DataResult<>();
    mockRet.setData(true);
    mockRet.setSuccess(true);
    Mockito.when(merchantBankCardService.unbindQuickPayCard(Matchers.anyObject())).thenReturn(mockRet);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    request.setUserNo(1234L);
    request.setBindID(4567L);
    Boolean ret = merchantBankCardServiceClient.unbindCard(request);
    Assert.assertTrue(ret);
  }

  @Test(expected = CardBaseException.class)
  public void testUnbindCardEx() throws Exception {

    Mockito.when(merchantBankCardService.unbindQuickPayCard(Matchers.anyObject())).thenThrow(SocketException.class);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    request.setUserNo(1234L);
    request.setBindID(4567L);
    Boolean ret = merchantBankCardServiceClient.unbindCard(request);
  }

  @Test(expected = CardBaseException.class)
  public void testUnbindCardNull() throws Exception {

    Mockito.when(merchantBankCardService.unbindQuickPayCard(Matchers.anyObject())).thenReturn(null);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    request.setUserNo(1234L);
    request.setBindID(4567L);
    Boolean ret = merchantBankCardServiceClient.unbindCard(request);
  }

  @Test(expected = CardBaseException.class)
  public void testUnbindCardFail() throws Exception {

    DataResult<Boolean> mockRet = new DataResult<>();
    mockRet.setSuccess(false);
    Mockito.when(merchantBankCardService.unbindQuickPayCard(Matchers.anyObject())).thenReturn(mockRet);

    QueryBoundCardRequest request = new QueryBoundCardRequest();
    request.setUserNo(1234L);
    request.setBindID(4567L);
    Boolean ret = merchantBankCardServiceClient.unbindCard(request);
  }

  @Test
  public void testBindBankCard() throws Exception {

    DataResult<BoundBankCardResult> bindRet = new DataResult<>();
    bindRet.setSuccess(true);

    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setCardBindId(4567L);
    bindRet.setData(cardResult);

    Mockito.when(merchantBankCardService.bindQuackPayCard(anyObject())).thenReturn(bindRet);

    BindCardRequest request = new BindCardRequest();
    request.setPartnerId(123);
    request.setCardNo("4782");
    request.setUserNo(1234L);
    BoundBankCardResult result = merchantBankCardServiceClient.bindBankCard(request);
    Assert.assertEquals(4567L,result.getCardBindId().longValue());

  }

  @Test(expected = CardBaseException.class)
  public void testBindBankCardEx() throws Exception {

    DataResult<BoundBankCardResult> bindRet = new DataResult<>();
    bindRet.setSuccess(true);

    BoundBankCardResult cardResult = new BoundBankCardResult();
    cardResult.setCardBindId(4567L);
    bindRet.setData(cardResult);

    Mockito.when(merchantBankCardService.bindQuackPayCard(anyObject())).thenThrow(SocketException.class);

    BindCardRequest request = new BindCardRequest();
    request.setPartnerId(123);
    request.setCardNo("4782");
    request.setUserNo(1234L);
    BoundBankCardResult result = merchantBankCardServiceClient.bindBankCard(request);

  }

  @Test(expected = CardBaseException.class)
  public void testBindBankCardFail() throws Exception {

    DataResult<BoundBankCardResult> bindRet = new DataResult<>();
    bindRet.setSuccess(false);

    Mockito.when(merchantBankCardService.bindQuackPayCard(anyObject())).thenReturn(bindRet);

    BindCardRequest request = new BindCardRequest();
    request.setPartnerId(123);
    request.setCardNo("4782");
    request.setUserNo(1234L);
    BoundBankCardResult result = merchantBankCardServiceClient.bindBankCard(request);

  }

  @Test(expected = CardBaseException.class)
  public void testBindBankCardNull() throws Exception {

    Mockito.when(merchantBankCardService.bindQuackPayCard(anyObject())).thenReturn(null);

    BindCardRequest request = new BindCardRequest();
    request.setPartnerId(123);
    request.setCardNo("4782");
    request.setUserNo(1234L);
    BoundBankCardResult result = merchantBankCardServiceClient.bindBankCard(request);

  }

}