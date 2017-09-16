package com.youzan.pay.unified.cashier.api.impl.hanlder;


import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.pay.cardvoucher.api.summarycard.dto.SummaryCardInfoDTO;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.microaccount.prepaidcard.to.AccountPayTO;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.impl.filter.dochain.ValueCard;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.UnifiedCashierSearchPayTypeHandler;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.api.result.UnifiedSearchPayTypeResult;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.SummaryCardQueryServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayToolsSortingCache;
import com.youzan.pay.unified.cashier.service.depository.impl.PayStrategyDepositoryServiceImpl;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyObject;

/**
 * Created by xielina on 2017/8/21.
 */
public class UnifiedCashierSearchPayTypeTest extends BaseTest {

  @InjectMocks
  @Resource
  private UnifiedCashierSearchPayTypeHandler
      unifiedCashierSearchPayTypeHandler;

  @Mock
  @Resource
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @Mock
  private SummaryCardQueryServiceClient summaryCardQueryServiceClient;

  @Mock
  @Resource
  private PayToolsSortingCache payToolsSortingCache;

  @InjectMocks
  @Resource
  private ValueCard checkPrepayCard;
  @Mock
  @Resource
  private PayStrategyDepositoryServiceImpl payStrategyDepositoryService;


  /**
   * 测试预付卡
   */
  @Test
  public void TestPrepayCard() {
    UnifiedSearchPayTypeRequest request = buildUnifiedSearchPayTypeRequest();

    com.youzan.pay.customer.api.result.Response<ConfigInfo>
        response =
        new com.youzan.pay.customer.api.result.Response<>();

    ConfigInfo configInfo = new ConfigInfo();

    List<PayToolConfig> list = new ArrayList<>();

    addPayToolConfigList(list);
    addPayToolConfigPrepay(list);

    configInfo.setPayTools(list);

    response.setResult(configInfo);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject())).thenReturn(response);

    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject()))
        .thenReturn(buildResponse2());

    com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
        resp =
        unifiedCashierSearchPayTypeHandler
            .handle(request);

    List<PayChannel> payChannelList = (List<PayChannel>) resp.getResult();
    for (PayChannel payChannel : payChannelList) {
      System.out.println(payChannel);
    }
  }

  /**
   * 测试储存卡
   */
//  @Test
//  public void TestPrepaidCard() {
//    UnifiedSearchPayTypeRequest request = buildUnifiedSearchPayTypeRequest();
//
//    com.youzan.pay.customer.api.result.Response<ConfigInfo>
//        response =
//        new com.youzan.pay.customer.api.result.Response<>();
//
//    ConfigInfo configInfo = new ConfigInfo();
//
//    List<PayToolConfig> list = new ArrayList<>();
//
//    addPayToolConfigList(list);
//    addPayToolConfigPrepaid(list);
//    configInfo.setPayTools(list);
//
//    response.setResult(configInfo);
//
//    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);
//
//    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject()))
//        .thenReturn(buildResponse2());
//
//    Mockito.when(userMerchantServiceClient.queryMchByUserId(Mockito.anyObject())).thenReturn(getUserMerchantDto());
//
//    Mockito.when(microAccoutServiceClient.queryAccountTO(Mockito.anyObject(),Mockito.anyLong())).thenReturn(getAccountPayTO());
//
//
//
//
//    com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
//        resp =
//        unifiedCashierSearchPayTypeHandler
//            .handle(request);
//
//    List<PayChannel> payChannelList = (List<PayChannel>) resp.getResult();
//    for (PayChannel payChannel : payChannelList) {
//      System.out.println(payChannel);
//    }
//  }

  /**
   * 测试两种都有返回，屏蔽
   */
  @Test
  public void TestNullCard() {
    UnifiedSearchPayTypeRequest request = buildUnifiedSearchPayTypeRequest();

    com.youzan.pay.customer.api.result.Response<ConfigInfo>
        response =
        new com.youzan.pay.customer.api.result.Response<>();

    ConfigInfo configInfo = new ConfigInfo();

    List<PayToolConfig> list = new ArrayList<>();

    addPayToolConfigList(list);
    addPayToolConfigPrepaid(list);
    addPayToolConfigPrepay(list);
    configInfo.setPayTools(list);

    response.setResult(configInfo);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);

    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject()))
        .thenReturn(buildResponse2());

    com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
        resp =
        unifiedCashierSearchPayTypeHandler
            .handle(request);

    List<PayChannel> payChannelList = (List<PayChannel>) resp.getResult();
    for (PayChannel payChannel : payChannelList) {
      System.out.println(payChannel);
    }
  }

  /**
   * 测试交易排除预付卡
   */
  @Test
  public void TestNoPrepayCard() {
    UnifiedSearchPayTypeRequest request = buildUnifiedSearchPayTypeRequest();
    request.setPayTools("VALUE_CARD");

    com.youzan.pay.customer.api.result.Response<ConfigInfo>
        response =
        new com.youzan.pay.customer.api.result.Response<>();

    ConfigInfo configInfo = new ConfigInfo();

    List<PayToolConfig> list = new ArrayList<>();

    addPayToolConfigList(list);
    addPayToolConfigPrepay(list);

    configInfo.setPayTools(list);

    response.setResult(configInfo);

    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);

    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject()))
        .thenReturn(buildResponse2());

    com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
        resp =
        unifiedCashierSearchPayTypeHandler
            .handle(request);

    List<PayChannel> payChannelList = (List<PayChannel>) resp.getResult();
    for (PayChannel payChannel : payChannelList) {
      System.out.println(payChannel);
    }
  }

  /**
   * 测试交易排除储值卡
   */
//  @Test
//  public void TestNoPrepaidCard() {
//    UnifiedSearchPayTypeRequest request = buildUnifiedSearchPayTypeRequest();
//    request.setPayTools("PREPAID_PAY");
//
//    com.youzan.pay.customer.api.result.Response<ConfigInfo>
//        response =
//        new com.youzan.pay.customer.api.result.Response<>();
//
//    ConfigInfo configInfo = new ConfigInfo();
//
//    List<PayToolConfig> list = new ArrayList<>();
//
//    addPayToolConfigList(list);
//    addPayToolConfigPrepaid(list);
//
//    configInfo.setPayTools(list);
//
//    response.setResult(configInfo);
//
//    Mockito.when(queryPayToolConfigServiceClient.queryConfig(anyObject())).thenReturn(response);
//
//    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject()))
//        .thenReturn(buildResponse2());
//
//    Mockito.when(userMerchantServiceClient.queryMchByUserId(Mockito.anyObject())).thenReturn(getUserMerchantDto());
//
//    Mockito.when(microAccoutServiceClient.queryAccountTO(Mockito.anyObject(),Mockito.anyLong())).thenReturn(getAccountPayTO());
//
//
//
//
//    com.youzan.pay.unified.cashier.api.response.Response<UnifiedSearchPayTypeResult>
//        resp =
//        unifiedCashierSearchPayTypeHandler
//            .handle(request);
//
//    List<PayChannel> payChannelList = (List<PayChannel>) resp.getResult();
//    for (PayChannel payChannel : payChannelList) {
//      System.out.println(payChannel);
//    }
//  }

  //构造请求参数
  public UnifiedSearchPayTypeRequest buildUnifiedSearchPayTypeRequest() {
    UnifiedSearchPayTypeRequest request = new UnifiedSearchPayTypeRequest();

    request.setPayTools("NOW_PAY,CASH_ON_DELIVERY,PEER_PAY");
    request.setMchId("43434");
    request.setPayAmount(10);
    request.setBuyerId("5434");
    request.setPartnerId("34343");
    request.setPayEnviorment("ALIPAY");
    return request;
  }

  private void addPayToolConfigList(List<PayToolConfig> list) {

    PayToolConfig payToolConfig = new PayToolConfig();

    payToolConfig.setAvailableDesc("");
    payToolConfig.setAvailable(true);
    payToolConfig.setVisibleDesc("");
    payToolConfig.setVisible(true);
    payToolConfig.setPayTool("WX_JS");

    list.add(payToolConfig);

  }

  private void addPayToolConfigPrepaid(List<PayToolConfig> list) {

    PayToolConfig payToolConfig1 = new PayToolConfig();

    payToolConfig1.setAvailableDesc("");
    payToolConfig1.setAvailable(true);
    payToolConfig1.setVisibleDesc("");
    payToolConfig1.setVisible(true);
    payToolConfig1.setPayTool("PREPAID_PAY");
    list.add(payToolConfig1);

  }

  private void addPayToolConfigPrepay(List<PayToolConfig> list) {
    PayToolConfig payToolConfig2 = new PayToolConfig();

    payToolConfig2.setAvailableDesc("");
    payToolConfig2.setAvailable(true);
    payToolConfig2.setVisibleDesc("");
    payToolConfig2.setVisible(true);
    payToolConfig2.setPayTool("VALUE_CARD");

    list.add(payToolConfig2);


  }
  //构造卡券平台返回参数

  public ListResult<SummaryCardInfoDTO> buildResponse2() {
    ListResult<SummaryCardInfoDTO> result = new ListResult<SummaryCardInfoDTO>();
    SummaryCardInfoDTO summaryCardInfoDTO1 = new SummaryCardInfoDTO();
    summaryCardInfoDTO1.setCardName("卡片1");
    summaryCardInfoDTO1.setCardNo("12312312");
    summaryCardInfoDTO1.setCardStatus("normal");
    summaryCardInfoDTO1.setDenomination(50);

    List<SummaryCardInfoDTO> list = new ArrayList<>();
    list.add(summaryCardInfoDTO1);

    result.setData(list);
    return result;
  }

  //返回UserMerchantDto
  public UserMerchantDto getUserMerchantDto(){
    UserMerchantDto userMerchantDto= new UserMerchantDto();
    userMerchantDto.setMerchantId(1231241);
    userMerchantDto.setSignKey("342323432");
    userMerchantDto.setUserId(3142231);
 return  userMerchantDto;
  }
  public AccountPayTO getAccountPayTO(){
    AccountPayTO accountPayTO = new AccountPayTO();
    accountPayTO.setBalance(100);
    accountPayTO.setBUserNo(342343);
    accountPayTO.setCanPay(true);
    accountPayTO.setMAcctNo(3242342);
    return accountPayTO;
  }
}
