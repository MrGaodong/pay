package com.youzan.pay.unified.cashier.service.cache;

import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.service.depository.impl.PayStrategyDepositoryServiceImpl;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;


/**
 * @Title: PayToolsCache
 * @Description: 这是一个收银台支付策略缓存， 缓存了支付排序方式和收银台目前支持的支付方式的信息
 * @Author: xielina
 * @Date: 2017/8/23
 * @Version: V1.0.0
 */
@Slf4j
@Component
public class PayToolsCache {

  @Resource
  private RedisCacheManager redisCacheManager;
  @Resource
  private PayStrategyDepositoryServiceImpl payStrategyDepositoryService;

  private static final String UCASHIER_PAY_TOOLS = "ucashier_pay_tools";
  private static final String STRATEGY_PAY = "PAY";


  public Map<String, PayStrategyDo> getPayCacheMap() {
    Map<String, PayStrategyDo> payToolsCache = null;

    //1:先从缓存中查找
    try {
      payToolsCache = redisCacheManager
          .get(UCASHIER_PAY_TOOLS, payToolsCache.getClass());
      LogUtils.debug(log, "[收银台：从redisCacheManager中取数据:key={},value={}]", UCASHIER_PAY_TOOLS,payToolsCache);

    } catch (Exception e) {
      LogUtils.warn(e, log, "[收银台：从redisCacheManager中取数据失败:key={}]", UCASHIER_PAY_TOOLS);
    }

    //2.如果没有查到，从数据库捞，捞到加入缓存，缓存一般逻辑，不解释

    if (payToolsCache == null) {
      List<PayStrategyDo> payStrategyDolist = getPayToolsStrategy(STRATEGY_PAY);
      if (payStrategyDolist!=null&& (!payStrategyDolist.isEmpty())) {
        payToolsCache = buildPayCacheMap(payStrategyDolist);

        LogUtils.debug(log, "[收银台：从数据库中读取数据:key={},value={}]", UCASHIER_PAY_TOOLS,payToolsCache);

        //加入缓存
        redisCacheManager
            .put(UCASHIER_PAY_TOOLS, payToolsCache);

      }

    }
    return payToolsCache;
  }

  private Map<String, PayStrategyDo> buildPayCacheMap(List<PayStrategyDo> payStrategyDtolist) {
    Map<String, PayStrategyDo> payCacheMap = new TreeMap();
    for (PayStrategyDo payStrategyDo : payStrategyDtolist) {
      String key = payStrategyDo.getBizStrategy() + payStrategyDo.getBizCode();
      payCacheMap.put(key, payStrategyDo);
    }
    return payCacheMap;
  }

  private List<PayStrategyDo> getPayToolsStrategy(String bizStrategy) {
    List<PayStrategyDo> payStrategyDolist = null;
    try {
      payStrategyDolist =
          payStrategyDepositoryService.loaPayAll(STRATEGY_PAY);
      if (payStrategyDolist.isEmpty()) {
        LogUtils.error(log, "[收银台：数据库表unified_cashier_strategy数据异常]");
      }

    } catch (Exception e) {
      LogUtils.error(log, "[收银台：收银台数据库挂了]");
    }
    return payStrategyDolist;
  }

}
