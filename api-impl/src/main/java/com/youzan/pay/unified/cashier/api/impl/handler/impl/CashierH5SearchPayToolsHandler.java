/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;


import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QueryConfigRequest;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.api.enums.PayEviormentEnum;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayChannelsConvertor;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.TradePayToolTypeExcludeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.impl.filter.FilterChain;
import com.youzan.pay.unified.cashier.api.impl.filter.impl.AliPayEnvFilter;
import com.youzan.pay.unified.cashier.api.impl.filter.impl.CardPayEnvFilter;
import com.youzan.pay.unified.cashier.api.impl.filter.impl.CommonPayEnvFilter;
import com.youzan.pay.unified.cashier.api.impl.filter.impl.WxPayEnvFilter;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractSearchPayTypeHandler;
import com.youzan.pay.unified.cashier.api.impl.model.filter.PayToolDoRequest;
import com.youzan.pay.unified.cashier.api.impl.strategy.impl.PayTypeListStrategy;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.api.request.group.PayToolFilterRequest;
import com.youzan.pay.unified.cashier.api.result.CashierH5SearchPayToolsResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayToolsSortingCache;
import com.youzan.pay.unified.cashier.service.depository.PayStrategyDepositoryService;
import com.youzan.pay.unified.cashier.service.dto.PayStrategyDto;
import com.youzan.pay.unified.cashier.intergration.client.SummaryCardQueryServiceClient;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierH5SearchPayToolsHandler.java, v 0.1 2017-05-18 14:25
 */
@Slf4j
@Component
public class CashierH5SearchPayToolsHandler extends
                                            AbstractSearchPayTypeHandler<CashierH5SearchPayToolsRequest, CashierH5SearchPayToolsResult> {

  @Resource
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  //spring-handlers.xml中配置支付方式处理链
  @Resource
  private FilterChain payToolDoChain;

  @Resource
  private FilterChain payToolFilterChain;


  @Resource
  private PayToolsSortingCache payToolsSortingCache;
  /**
   * 支付方式路由策略
   */
  @Resource
  private PayTypeListStrategy payTypeListStrategy;

  @Resource
  private PayStrategyDepositoryService payStrategyDepositoryService;

  @Resource
  private SummaryCardQueryServiceClient summaryCardQueryServiceClient;


  /**
   * 支付方式查询处理类
   * @param request
   * @return
   * @throws Exception
   */
  @Override
  protected CashierH5SearchPayToolsResult execute(CashierH5SearchPayToolsRequest request)
      throws Exception {

    LogUtils.info(log, "[查询支付方式处理开始],req={}", request);

    Response<ConfigInfo> payToolConfigResponse = getPayToolConfigResponse(request);

    List<PayToolConfig> payToolConfigs = getPayToolConfigs(payToolConfigResponse);

    CashierH5SearchPayToolsResult result = searchPayToolList(request, payToolConfigs);



    LogUtils.info(log, "[支付工具集合结果]:{}", result);

    return result;
  }

  //判定商户是否配置卡服务，用户是否拥有该卡，用户该卡是否可用（例如：预付卡）
  private void doSupportPayTools(CashierH5SearchPayToolsRequest request,List<PayToolConfig> payToolConfigs,List<PayChannel> payChannelList) {
    List<PayToolConfig> payToolConfigsFilter=payToolConfigs;
    //构造过滤链请求参数
    PayToolDoRequest specialCardRequest=new PayToolDoRequest(request, payChannelList);

    for (PayToolConfig payToolConfig : payToolConfigsFilter) {
      //可见且可用的时候加入处理链路
      if(payToolConfig.isVisible()&&payToolConfig.isAvailable()) {
        specialCardRequest.setPayToolConfig(payToolConfig);
        payToolDoChain.handleRequest(specialCardRequest);
      }
    }
  }

  @Override
  protected String getPayTools(CashierH5SearchPayToolsRequest request) {
    LogUtils.debug(log,"H5暂时不需要获取交易支付工具");
    return new String();
  }

  @Override
  protected CashierH5SearchPayToolsResult execute(CashierH5SearchPayToolsRequest request,
                                                  List<PayToolConfig> payToolconfigs) {

    CashierH5SearchPayToolsResult cashierH5SearchPayToolsResult = new CashierH5SearchPayToolsResult();

    /**
     * 排除新交易不支持的支付方式
     */

    if (request.getExcludePayTools() != null) {
      excludeTradePayTools(payToolconfigs, request);
    }
    /**
     *
     * 生成支付环境过滤链
     */
    //存储该环境下支持的支付方式
    List<PayToolConfig> payToolConfigsSupport =new ArrayList<>();

    for (PayToolConfig payToolConfig : payToolconfigs) {
      PayToolFilterRequest payToolFilterRequest=buildPayToolFilterRequest(payToolConfig, request);
     boolean filterResult= payToolFilterChain.handleRequest(payToolFilterRequest);

     if(!filterResult){
       payToolConfigsSupport.add(payToolConfig);
     }

    }

    /**
     * 对过滤后环境支持的支付方式进行包装处理
     */
    List<PayChannel> payChannels = new ArrayList<>();

    doSupportPayTools(request,payToolConfigsSupport,payChannels);
    //支付方式编排
    payChannels =sortPayChannelList(payChannels);
    //支付方式重排
    payChannels = PayChannelsConvertor.convert(payChannels);

    cashierH5SearchPayToolsResult.setPayChannels(payChannels);

    return cashierH5SearchPayToolsResult;
  }

  private void excludeTradePayTools(List<PayToolConfig> payToolConfigs,
                                    CashierH5SearchPayToolsRequest request) {
    List<String> excludePayTools = Arrays.asList(request.getExcludePayTools().split(","));
    payToolConfigs.removeIf(payToolConfig -> env(payToolConfig, excludePayTools));
  }

  /**
   * 判断是否有交易不支持的线上支付方式
   */
  private boolean env(PayToolConfig payToolConfig, List<String> excludePayTools) {

    /**
     * 微信支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.WX.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.WX_JS.name())
          || payToolConfig.getPayTool().equals(PayToolTypeEnum.WX_H5.name())) {
        return true;
      }
    }
    /**
     * 支付宝支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.ALIPAY.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.ALIPAY_WAP.name())) {
        return true;
      }
    }
    /**
     * 礼品卡支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.GIFT_CARD.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.GIFT_CARD.name())) {
        return true;
      }
    }
    /**
     * 余额支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.BALANCE.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.BALANCE.name())) {
        return true;
      }
    }
    /**
     * 有赞E卡支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.ECARD.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.ECARD.name())) {
        return true;
      }
    }
    /**
     * 储值卡支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.PREPAID_PAY.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.PREPAID_PAY.name())) {
        return true;
      }
    }
    /**
     * 预付卡支付方式排除
     */
    if (excludePayTools.contains(TradePayToolTypeExcludeEnum.VALUE_CARD.name())) {
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.VALUE_CARD.name())) {
        return true;
      }
    }

    return false;
  }

  private  List<PayChannel> sortPayChannelList(List<PayChannel> payChannels) {
    List<PayChannel> payChannelsSorts = new LinkedList<>();
    Map<String, Integer> map = payToolsSortingCache.getCacheMap();
    LogUtils.info(log, "[payToolsSortingCache]:{}", map);
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (exitInPayToolConfigs(entry.getKey(), payChannels)) {
        payChannelsSorts.add(getPayChannel(entry.getKey(), payChannels));
      }
    }
    return payChannelsSorts;
  }

  private PayChannel getPayChannel(String key, List<PayChannel> payChannels) {
    PayChannel payChannel1 = new PayChannel();
    for (PayChannel paychannel : payChannels) {
      if (paychannel.getPayChannel().equals(key)) {
        payChannel1 = paychannel;
      }
    }
    return payChannel1;
  }

  private boolean exitInPayToolConfigs(String key, List<PayChannel> payChannels) {
    for (PayChannel payChannel : payChannels) {
      if (payChannel.getPayChannel().equals(key)) {
        return true;
      }
    }
    return false;
  }
  private PayToolFilterRequest buildPayToolFilterRequest(PayToolConfig payToolConfig,
                                         CashierH5SearchPayToolsRequest request) {
    PayToolFilterRequest payToolFilterRequest=new PayToolFilterRequest();
    payToolFilterRequest.setAvailable(payToolConfig.isAvailable());
    payToolFilterRequest.setVisible(payToolConfig.isVisible());
    payToolFilterRequest.setAvailable_desc(payToolConfig.getAvailableDesc());
    payToolFilterRequest.setVisible_desc(payToolConfig.getVisibleDesc());
    payToolFilterRequest.setPayChannel(payToolConfig.getPayTool());
    payToolFilterRequest.setPayEnviorment(request.getPayEnviorment());
    return payToolFilterRequest;
  }



  private FilterChain buildFilters() {
    List<Filter> filters=new ArrayList<>();
    filters.add(new AliPayEnvFilter());
    filters.add(new CardPayEnvFilter());
    filters.add(new WxPayEnvFilter());
    filters.add(new CommonPayEnvFilter());
    return new FilterChain(filters);
  }

  private List<PayToolConfig> getPayToolConfigs(Response<ConfigInfo> payToolConfigResponse) {

    if (payToolConfigResponse.getResult() == null) {
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

    if (payToolConfigResponse.getResult() != null
        && payToolConfigResponse.getResult().getPayTools() != null) {
      List<PayToolConfig> payToolConfigs = payToolConfigResponse.getResult().getPayTools();
      return payToolConfigs;

    } else {
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }
  }

  private Response<ConfigInfo> getPayToolConfigResponse(CashierH5SearchPayToolsRequest request) {

    QueryConfigRequest queryConfigRequest = new QueryConfigRequest();
    queryConfigRequest.setBuyerId(request.getBuyerId());
    queryConfigRequest.setMchId(request.getMchId());
    queryConfigRequest.setPartnerId(request.getPartnerId());

    Response<ConfigInfo> payToolConfigResponse=new Response<>();
    try {
      payToolConfigResponse = queryPayToolConfigServiceClient.queryConfig(queryConfigRequest);
    } catch (Exception e) {
      LogUtils.error(log, "[queryPayToolConfigServiceClient异常]:{}", e);
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

    return payToolConfigResponse;
  }


  private void genCashierPayTool(CashierH5SearchPayToolsRequest request, PayToolConfig payToolConfig,
                                 List<PayChannel> payChannels) {
    payTypeListStrategy.genCashierH5PayTool(request, payToolConfig, payChannels);
  }
}
