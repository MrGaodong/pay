/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QueryConfigRequest;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.microaccount.prepaidcard.to.AccountPayTO;
import com.youzan.pay.unified.cashier.api.impl.convertor.PayChannelsConvertor;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.TradePayToolTypeExcludeEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractSearchPayTypeHandler;
import com.youzan.pay.unified.cashier.api.impl.strategy.impl.PayTypeListStrategy;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.api.result.UnifiedSearchPayTypeResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.intergration.client.MicroAccoutServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.cache.CacheService;
import com.youzan.pay.unified.cashier.service.cache.PayToolsCache;
import com.youzan.pay.unified.cashier.service.cache.PayToolsSortingCache;
import com.youzan.pay.unified.cashier.service.depository.impl.PayStrategyDepositoryServiceImpl;
import com.youzan.pay.unified.cashier.service.dto.PayStrategyDto;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

/**
 * 微商城收银台：查询支付方式hanlder实现类
 *
 * @author wulonghui
 * @version UnifiedCashierSearchPayTypeHandler.java, v 0.1 2017-01-12 11:00
 */
@Component
public class UnifiedCashierSearchPayTypeHandler
    extends AbstractSearchPayTypeHandler<UnifiedSearchPayTypeRequest, UnifiedSearchPayTypeResult> {

  @Resource
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  /**
   * 支付方式路由策略
   */
  @Resource
  private PayTypeListStrategy payTypeListStrategy;

  @Resource
  private PayToolsSortingCache payToolsSortingCache;

  @Resource
  private PayStrategyDepositoryServiceImpl payStrategyDepositoryService;

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;

  @Resource
  private MicroAccoutServiceClient microAccoutServiceClient;

  @Resource
  private RedisCacheManager redisCacheManager;

  @Resource
  private PayToolsCache payToolsCache;

  /**
   * 具体实现方法
   */
  protected UnifiedSearchPayTypeResult execute(UnifiedSearchPayTypeRequest request) {

    LogUtils.info(log, "[查询支付方式],req={}", request);

    QueryConfigRequest queryConfigRequest = new QueryConfigRequest();
    queryConfigRequest.setBuyerId(request.getBuyerId());
    queryConfigRequest.setMchId(request.getMchId());
    queryConfigRequest.setPartnerId(request.getPartnerId());

    Response<ConfigInfo> payToolConfigResponse = new Response<ConfigInfo>();

    try {
      payToolConfigResponse = queryPayToolConfigServiceClient.queryConfig(queryConfigRequest);
    } catch (Exception e) {
      LogUtils.error(log, "[queryPayToolConfigServiceClient异常]:{}", e);
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

    List<PayToolConfig> payToolConfigs = new LinkedList<>();

    if (payToolConfigResponse.getResult() == null) {
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

    if (payToolConfigResponse.getResult() != null
        && payToolConfigResponse.getResult().getPayTools() != null) {
      payToolConfigs = payToolConfigResponse.getResult().getPayTools();

    } else {
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

    LogUtils.info(log, "[商户平台获取支付配置payToolConfigs]:{}", payToolConfigs);

    //存储可支付的支付方式
    List<PayToolConfig> depositoryCardList = payToolConfigs;

    /**
     *  排除商户返回不可见不可用的支付方式
     */

    for (PayToolConfig payToolConfig : depositoryCardList) {
      if (payToolConfig.isVisible() && payToolConfig.isAvailable()) {
        continue;
      } else {
        depositoryCardList.remove(payToolConfig);
      }
    }
    /**
     * 排除新交易不支持的支付方式
     */

    if (request.getExcludePayTools() != null) {
      excludeTradePayTools(depositoryCardList, request);
    }

    /**
     * 预付卡，储值卡逻辑：
     * 预付卡和储值卡都存在，则都不展示
     */

    if (isExistValueCard(depositoryCardList) && isExistPrepaidPayCard(depositoryCardList)) {
      //预付卡，储值卡都存在，移除预付卡 储值卡
      depositoryCardList.removeIf(t -> t.getPayTool().equals(PayToolTypeEnum.PREPAID_PAY.name()));
      depositoryCardList
          .removeIf(t -> t.getPayTool().equals(PayToolTypeEnum.VALUE_CARD.name()));

    }

    /**
     * 储值卡逻辑
     */

    if (isExistPrepaidPayCard(depositoryCardList)) {
      //1.移除商户平台返回的老储值卡信息
      depositoryCardList.removeIf(t -> t.getPayTool().equals(PayToolTypeEnum.PREPAID_PAY.name()));
      //2.重新根据储值卡系统返回值构造老储值卡信息
      PayToolConfig payToolConfig = buildDepositoryCardPayTool(request);
      //3.将老储值卡信息加入支付方式list
      if (payToolConfig != null && payToolConfig.getPayTool() != null) {
        depositoryCardList.add(payToolConfig);
      }
    }
    /**
     * 默认支付方式排序，新交易一期，二期
     */
    List<PayToolConfig> sortPayToolConfigs = sortPayToolList(depositoryCardList);

    LogUtils.info(log, "[最后支持的已经排序的支付方式 depositoryCardList]:{}", depositoryCardList);

    UnifiedSearchPayTypeResult result = searchPayToolList(request, sortPayToolConfigs);

    LogUtils.info(log, "[查询支付方式],result=:{}", result);
    return result;

  }

  /**
   * 储值卡余额展现
   */
  private PayToolConfig buildDepositoryCardPayTool(UnifiedSearchPayTypeRequest request) {
    PayToolConfig payToolConfig = new PayToolConfig();

    if (checkPayToolStrategy()) {
      payToolConfig = buildDepositoryPayToolConfig(request);
    }
    return payToolConfig;
  }

  /**
   * 储值卡支付余额等信息
   */
  private PayToolConfig buildDepositoryPayToolConfig(UnifiedSearchPayTypeRequest request) {
    PayToolConfig payToolConfig = new PayToolConfig();
    LogUtils.info(log, "[userId={}]", request.getBuyerId());

    if (isNumeric(request.getBuyerId())) {
      UserMerchantDto userMerchantDto = getUserMerchantDto(request);
      LogUtils.info(log, "[userMerchantDto={}]", userMerchantDto);
      payToolConfig = getPayToolDepositoryConfig(userMerchantDto, request);
    }
    return payToolConfig;

  }

  private UserMerchantDto getUserMerchantDto(UnifiedSearchPayTypeRequest request) {
    UserMerchantDto userMerchantDto = null;

    try {
      userMerchantDto =
          redisCacheManager.get("userId_cUserNo_" + request.getBuyerId(), UserMerchantDto.class);
      if (userMerchantDto == null) {
        UserMerchantSinleRequest userMerchantSinleRequest = new UserMerchantSinleRequest();
        userMerchantSinleRequest.setUserId(Long.valueOf(request.getBuyerId()));

        userMerchantDto =
            userMerchantServiceClient.queryMchByUserId(userMerchantSinleRequest);
        redisCacheManager.put("userId_cUserNo_" + request.getBuyerId(), userMerchantDto);
      }
    } catch (Exception e) {
      LogUtils.warn(e, log, "[userMerchantServiceClient调用失败,buyerId={}]", request.getBuyerId());
      return null;
    }
    return userMerchantDto;
  }

  /**
   * 储值卡支付方式构造
   */
  private PayToolConfig getPayToolDepositoryConfig(UserMerchantDto userMerchantDto,
                                                   UnifiedSearchPayTypeRequest request) {

    //npe修复
    if (userMerchantDto == null) {
      return null;
    }

    PayToolConfig payToolConfig = new PayToolConfig();
    long userMerchantNo = userMerchantDto.getMerchantId();
    long mchId = Long.valueOf(request.getMchId());

    AccountPayTO accountPayTO = microAccoutServiceClient.queryAccountTO(userMerchantNo, mchId);
    LogUtils.info(log, "[查询到accountPayTO={}]", accountPayTO);
    /**
     * 储值卡信息空
     */
    if (accountPayTO == null) {
      LogUtils.warn(log, "[卡券平台查询不到对应c端储值卡信息,userMerchantNo={}]", userMerchantNo);
      return payToolConfig;
    }
    /**
     * 储值卡可用
     */
    if (accountPayTO.isCanPay()) {
      payToolConfig.setPayTool(PayToolTypeEnum.PREPAID_PAY.name());
      payToolConfig.setAvailable(true);
      payToolConfig.setVisible(true);
      payToolConfig.setBalance(accountPayTO.getBalance());
    }
    return payToolConfig;
  }

  /**
   * 判断收银台数据库储值卡策略是否开启 todo  下次优化全部加入策略类，不要写死，所有的checkPayToolStrategy（）
   */
  private boolean checkPayToolStrategy() {
    Map<String, PayStrategyDo> payToolsCacheMap = payToolsCache.getPayCacheMap();
    PayStrategyDo payStrategyDo = payToolsCacheMap.get("PAY1001");

    //3.判断老储值卡是否可用以及是否可见
    if (payStrategyDo.getAvailable().equals(1) && payStrategyDo.getVisible().equals(1)
        && payStrategyDo.getPayChannel().equals(PayToolTypeEnum.PREPAID_PAY.name())) {
      return true;
    }
    return false;

  }

  /**
   * 是否存在预付卡,商户平台查询
   */
  private boolean isExistValueCard(List<PayToolConfig> payToolConfigs) {
    if (payToolConfigs.isEmpty()) {
      return false;
    }
    return payToolConfigs.stream()
        .anyMatch(item -> item.getPayTool().equals(PayToolTypeEnum.VALUE_CARD.name()));
  }

  /**
   * 是否存在储值卡,商户平台查询
   */
  private boolean isExistPrepaidPayCard(List<PayToolConfig> payToolConfigs) {
    if (payToolConfigs.isEmpty()) {
      return false;
    }
    return payToolConfigs.stream()
        .anyMatch(item -> item.getPayTool().equals(PayToolTypeEnum.PREPAID_PAY.name()));
  }

  private List<PayToolConfig> sortPayToolList(List<PayToolConfig> payToolConfigs) {
    List<PayToolConfig> payToolConfigSorts = new LinkedList<>();
    Map<String, Integer> map = payToolsSortingCache.getCacheMap();
    LogUtils.info(log, "[payToolsSortingCache]:{}", map);
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (exitInPayToolConfigs(entry.getKey(), payToolConfigs)) {
        payToolConfigSorts.add(getPayToolConfig(entry.getKey(), payToolConfigs));
      }
    }
    return payToolConfigSorts;
  }

  private PayToolConfig getPayToolConfig(String key, List<PayToolConfig> payToolConfigs) {
    PayToolConfig payToolConfig1 = new PayToolConfig();
    for (PayToolConfig payToolConfig : payToolConfigs) {
      if (payToolConfig.getPayTool().equals(key)) {
        payToolConfig1 = payToolConfig;
      }
    }
    return payToolConfig1;
  }

  private boolean exitInPayToolConfigs(String key, List<PayToolConfig> payToolConfigs) {
    for (PayToolConfig payToolConfig : payToolConfigs) {
      if (payToolConfig.getPayTool().equals(key)) {
        return true;
      }
    }
    return false;
  }

  private void excludeTradePayTools(List<PayToolConfig> payToolConfigs,
                                    UnifiedSearchPayTypeRequest request) {
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

  @Override
  protected UnifiedSearchPayTypeResult execute(UnifiedSearchPayTypeRequest request,

                                               List<PayToolConfig> payToolconfigs) {

    UnifiedSearchPayTypeResult unifiedSearchPayTypeResult = new UnifiedSearchPayTypeResult();

    List<PayChannel> payChannels = new ArrayList<>();

    /**
     *
     * 支付方式策略选择
     */
    for (PayToolConfig payToolConfig : payToolconfigs) {

      // TODO 到时候余额支付需要开放出去
      if (payToolConfig.getPayTool().equals(PayToolTypeEnum.BALANCE.getCode())) {
        continue;
      }

      // 路由支付方式
      genCashierPayTool(request, payToolConfig, payChannels);
    }

    payChannels = PayChannelsConvertor.convert(payChannels);

    unifiedSearchPayTypeResult.setPayChannels(payChannels);

    return unifiedSearchPayTypeResult;
  }

  private void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                 List<PayChannel> payChannels) {
    payTypeListStrategy.genCashierPayTool(request, payToolConfig, payChannels);
  }

  protected String getPayTools(UnifiedSearchPayTypeRequest request) {
    return request.getPayTools();
  }

  public boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    List<PayToolConfig> lists = new ArrayList<>();

    PayToolConfig payToolConfig = new PayToolConfig();
    payToolConfig.setPayTool("wx");

    PayToolConfig payToolConfig1 = new PayToolConfig();
    payToolConfig1.setPayTool("alipay");

    lists.add(payToolConfig);
    lists.add(payToolConfig1);

    System.out.println("pre:" + lists);

    lists.removeIf(t -> t.getPayTool().equals("wx"));
    System.out.println("end:" + lists);
  }

}
