package com.youzan.pay.unified.cashier.api.impl.filter.dochain;

import com.youzan.pay.cardvoucher.api.summarycard.dto.SummaryCardInfoDTO;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.BaseTest;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.FilterChain;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5SearchPayToolsHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.result.CashierH5SearchPayToolsResult;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.SummaryCardQueryServiceClient;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by xielina on 2017/8/15.
 */
public class CashierH5SearchPayToolsPrepayTest extends BaseTest {

  @InjectMocks
  @Resource
  private CashierH5SearchPayToolsHandler cashierH5SearchPayToolsHandler;

  @Mock
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @InjectMocks
  @Resource
  private FilterChain payToolDoChain;

  @Mock
  private SummaryCardQueryServiceClient summaryCardQueryServiceClient;

  @InjectMocks
  @Resource
  private ValueCard checkPrepayCard;

  @Test
  public void TestPrepayCard(){
    CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest=buildCashierH5AcquireOrderRequest();
    Response<ConfigInfo> payToolConfigResponse=createSearchPayToolsFromMchPlaForm2();
    Mockito
        .when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject())).thenReturn(payToolConfigResponse);



    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject())).thenReturn(buildResponse());


    ValueCard checkPrepayCard=Mockito.mock(ValueCard.class);
    Mockito.when(checkPrepayCard.doFilter(Mockito.anyObject())).thenReturn(true);
    com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
        resp=cashierH5SearchPayToolsHandler.handle(cashierH5SearchPayToolsRequest);
    List<PayChannel> payChannelList = resp.getResult().getPayChannels();
    for (PayChannel payChannel : payChannelList) {
      if(payChannel.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD)){
        Assert.assertTrue(false);
      }
    }

  }
  @Test
  public void TestPrepayCard1(){
    CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest=buildCashierH5AcquireOrderRequest();
    Response<ConfigInfo> payToolConfigResponse=createSearchPayToolsFromMchPlaForm2();
    Mockito
        .when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject())).thenReturn(payToolConfigResponse);



    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject())).thenReturn(buildResponse2());


    ValueCard checkPrepayCard=Mockito.mock(ValueCard.class);
    Mockito.when(checkPrepayCard.doFilter(Mockito.anyObject())).thenReturn(true);
    com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
        resp=cashierH5SearchPayToolsHandler.handle(cashierH5SearchPayToolsRequest);
    List<PayChannel> payChannelList = resp.getResult().getPayChannels();
    for (PayChannel payChannel : payChannelList) {
      if(payChannel.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD)){
        if(payChannel.getPayChannelName().equals("卡片1")&&payChannel.isAvailable()){
        }else{
          Assert.assertTrue(false);
        }
      }
    }

  }
  @Test
  public void TestPrepayCard2(){
    CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest=buildCashierH5AcquireOrderRequest();
    Response<ConfigInfo> payToolConfigResponse=createSearchPayToolsFromMchPlaForm2();
    Mockito
        .when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject())).thenReturn(payToolConfigResponse);
    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject())).thenReturn(buildResponse3());


    ValueCard checkPrepayCard=Mockito.mock(ValueCard.class);
    Mockito.when(checkPrepayCard.doFilter(Mockito.anyObject())).thenReturn(true);
    com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
        resp=cashierH5SearchPayToolsHandler.handle(cashierH5SearchPayToolsRequest);
    List<PayChannel> payChannelList = resp.getResult().getPayChannels();
    for (PayChannel payChannel : payChannelList) {
      if (payChannel.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD)) {
        Assert.assertTrue(false);
      }
    }
  }
  @Test
  public void TestPrepayCard4(){
    CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest=buildCashierH5AcquireOrderRequest();
    Response<ConfigInfo> payToolConfigResponse=createSearchPayToolsFromMchPlaForm2();
    Mockito
        .when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject())).thenReturn(payToolConfigResponse);
    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject())).thenReturn(buildResponse4());


    ValueCard checkPrepayCard=Mockito.mock(ValueCard.class);
    Mockito.when(checkPrepayCard.doFilter(Mockito.anyObject())).thenReturn(true);
    com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
        resp=cashierH5SearchPayToolsHandler.handle(cashierH5SearchPayToolsRequest);
    List<PayChannel> payChannelList = resp.getResult().getPayChannels();
    for (PayChannel payChannel : payChannelList) {
      if(payChannel.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD)){
        if(payChannel.getPayChannelName().equals(PayToolTypeEnum.getChannelName("VALUE_CARD"))){

        }else{
          Assert.assertTrue(false);
        }

      }
    }

  }
  @Test
  public void TestPrepayCard5(){
    CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest=buildCashierH5AcquireOrderRequest();
    Response<ConfigInfo> payToolConfigResponse=createSearchPayToolsFromMchPlaForm2();
    Mockito
        .when(queryPayToolConfigServiceClient.queryConfig(Mockito.anyObject())).thenReturn(payToolConfigResponse);
    Mockito.when(summaryCardQueryServiceClient.querySummaryCardInfo(Mockito.anyObject())).thenReturn(buildResponse5());


    ValueCard checkPrepayCard=Mockito.mock(ValueCard.class);
    Mockito.when(checkPrepayCard.doFilter(Mockito.anyObject())).thenReturn(true);
    com.youzan.pay.unified.cashier.api.response.Response<CashierH5SearchPayToolsResult>
        resp=cashierH5SearchPayToolsHandler.handle(cashierH5SearchPayToolsRequest);
    List<PayChannel> payChannelList = resp.getResult().getPayChannels();
    for (PayChannel payChannel : payChannelList) {
      if(payChannel.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD)){
        if(payChannel.getPayChannelName().equals(PayToolTypeEnum.getChannelName("VALUE_CARD"))&& (!payChannel.isAvailable()))
        {}else{
          Assert.assertTrue(false);
        }
        }
    }

  }
  public ListResult<SummaryCardInfoDTO> buildResponse(){
    ListResult<SummaryCardInfoDTO> result = new ListResult<SummaryCardInfoDTO>();
    SummaryCardInfoDTO summaryCardInfoDTO1 =new SummaryCardInfoDTO();
    summaryCardInfoDTO1.setCardName("卡片1");
    summaryCardInfoDTO1.setCardNo("12312312");
    summaryCardInfoDTO1.setCardStatus("normal");
    summaryCardInfoDTO1.setDenomination(50);

    SummaryCardInfoDTO summaryCardInfoDTO2 =new SummaryCardInfoDTO();
    summaryCardInfoDTO2.setCardName("卡片2");
    summaryCardInfoDTO2.setCardNo("123123112");
    summaryCardInfoDTO2.setCardStatus("normal");
    summaryCardInfoDTO2.setDenomination(10);

    SummaryCardInfoDTO summaryCardInfoDTO3 =new SummaryCardInfoDTO();
    summaryCardInfoDTO3.setCardName("卡片33333333333333");
    summaryCardInfoDTO3.setCardNo("12312312312");
    summaryCardInfoDTO3.setCardStatus("freeze");
    summaryCardInfoDTO3.setDenomination(50);

    SummaryCardInfoDTO summaryCardInfoDTO4 =new SummaryCardInfoDTO();
    summaryCardInfoDTO4.setCardName("卡片2");
    summaryCardInfoDTO4.setCardNo("123123112");
    summaryCardInfoDTO4.setCardStatus("normal");
    summaryCardInfoDTO4.setDenomination(1);

    List<SummaryCardInfoDTO> list  =  new ArrayList<>();
    list.add(summaryCardInfoDTO1);
    list.add(summaryCardInfoDTO2);
    list.add(summaryCardInfoDTO3);
    list.add(summaryCardInfoDTO4);

    result.setData(list);
    return result;
  }
  public ListResult<SummaryCardInfoDTO> buildResponse2(){
    ListResult<SummaryCardInfoDTO> result = new ListResult<SummaryCardInfoDTO>();
    SummaryCardInfoDTO summaryCardInfoDTO1 =new SummaryCardInfoDTO();
    summaryCardInfoDTO1.setCardName("卡片1");
    summaryCardInfoDTO1.setCardNo("12312312");
    summaryCardInfoDTO1.setCardStatus("normal");
    summaryCardInfoDTO1.setDenomination(50);

    List<SummaryCardInfoDTO> list  =  new ArrayList<>();
    list.add(summaryCardInfoDTO1);

    result.setData(list);
    return result;
  }
  public ListResult<SummaryCardInfoDTO> buildResponse3(){
    ListResult<SummaryCardInfoDTO> result = new ListResult<SummaryCardInfoDTO>();
    List<SummaryCardInfoDTO> list  =  new ArrayList<>();
    result.setData(list);
    return result;
  }
  public ListResult<SummaryCardInfoDTO> buildResponse4(){
    ListResult<SummaryCardInfoDTO> result = new ListResult<SummaryCardInfoDTO>();
    SummaryCardInfoDTO summaryCardInfoDTO1 =new SummaryCardInfoDTO();
    summaryCardInfoDTO1.setCardName("12345678901");
    summaryCardInfoDTO1.setCardNo("12312312");
    summaryCardInfoDTO1.setCardStatus("normal");
    summaryCardInfoDTO1.setDenomination(50);

    List<SummaryCardInfoDTO> list  =  new ArrayList<>();
    list.add(summaryCardInfoDTO1);

    result.setData(list);
    return result;
  }
  public ListResult<SummaryCardInfoDTO> buildResponse5(){
    ListResult<SummaryCardInfoDTO> result = new ListResult<SummaryCardInfoDTO>();
    SummaryCardInfoDTO summaryCardInfoDTO1 =new SummaryCardInfoDTO();
    summaryCardInfoDTO1.setCardName("");
    summaryCardInfoDTO1.setCardNo("12312312");
    summaryCardInfoDTO1.setCardStatus("normal");
    summaryCardInfoDTO1.setDenomination(1);

    List<SummaryCardInfoDTO> list  =  new ArrayList<>();
    list.add(summaryCardInfoDTO1);

    result.setData(list);
    return result;
  }
  private void addPayToolConfigList(List<PayToolConfig> list) {

    PayToolConfig payToolConfig = new PayToolConfig();

    PayToolConfig payToolConfig1 = new PayToolConfig();

    PayToolConfig payToolConfig2 = new PayToolConfig();

    PayToolConfig payToolConfig3 = new PayToolConfig();

    PayToolConfig payToolConfig4 = new PayToolConfig();

    PayToolConfig payToolConfig5 = new PayToolConfig();

    PayToolConfig payToolConfig6 = new PayToolConfig();


    payToolConfig.setAvailableDesc("");
    payToolConfig.setAvailable(true);
    payToolConfig.setVisibleDesc("");
    payToolConfig.setVisible(true);
    payToolConfig.setPayTool("WX_JS");

    payToolConfig1.setAvailableDesc("");
    payToolConfig1.setAvailable(true);
    payToolConfig1.setVisibleDesc("");
    payToolConfig1.setVisible(true);
    payToolConfig1.setPayTool("ALIPAY_WAP");

    payToolConfig2.setAvailableDesc("");
    payToolConfig2.setAvailable(true);
    payToolConfig2.setVisibleDesc("");
    payToolConfig2.setVisible(true);
    payToolConfig2.setPayTool("ECARD");

    payToolConfig3.setAvailableDesc("");
    payToolConfig3.setAvailable(true);
    payToolConfig3.setVisibleDesc("");
    payToolConfig3.setVisible(true);
    payToolConfig3.setPayTool("PEERPAY");

    payToolConfig4.setAvailableDesc("");
    payToolConfig4.setAvailable(true);
    payToolConfig4.setVisibleDesc("");
    payToolConfig4.setVisible(true);
    payToolConfig4.setPayTool("CASH_ON_DELIVERY");

    payToolConfig5.setAvailableDesc("");
    payToolConfig5.setAvailable(true);
    payToolConfig5.setVisibleDesc("");
    payToolConfig5.setVisible(true);
    payToolConfig5.setPayTool("BALANCE");

    payToolConfig6.setAvailableDesc("");
    payToolConfig6.setAvailable(true);
    payToolConfig6.setVisibleDesc("");
    payToolConfig6.setVisible(true);
    payToolConfig6.setPayTool("VALUE_CARD");

    list.add(payToolConfig);
    list.add(payToolConfig1);
    list.add(payToolConfig2);
    list.add(payToolConfig3);
    list.add(payToolConfig4);
    list.add(payToolConfig5);
    list.add(payToolConfig6);

  }
  private Response<ConfigInfo> createSearchPayToolsFromMchPlaForm2() {
    Response<ConfigInfo> resp=new Response<>();
    ConfigInfo configInfo=new ConfigInfo();
    List<PayToolConfig> payToolConfigs=new ArrayList<>();
    addPayToolConfigList(payToolConfigs);
    configInfo.setPayTools(payToolConfigs);
    resp.setResult(configInfo);
    resp.setSuccess(true);
    resp.setMsg("success");
    return resp;
  }
  private CashierH5SearchPayToolsRequest buildCashierH5AcquireOrderRequest() {
    CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest=new CashierH5SearchPayToolsRequest();
    cashierH5SearchPayToolsRequest.setBuyerId("3434");
    cashierH5SearchPayToolsRequest.setMchId("12324224");
    cashierH5SearchPayToolsRequest.setPartnerId("23423423423");
    cashierH5SearchPayToolsRequest.setPayAmount(10);
    cashierH5SearchPayToolsRequest.setPayEnviorment("WX_JS");
    return cashierH5SearchPayToolsRequest;
  }


}
