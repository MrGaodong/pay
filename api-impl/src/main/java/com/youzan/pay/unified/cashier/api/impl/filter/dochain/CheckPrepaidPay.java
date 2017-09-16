package com.youzan.pay.unified.cashier.api.impl.filter.dochain;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.microaccount.prepaidcard.to.AccountPayTO;
import com.youzan.pay.unified.cashier.api.impl.enums.AvailableDescEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.CurrencyEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.impl.model.filter.PayToolDoRequest;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.intergration.client.MicroAccoutServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayToolsCache;
import com.youzan.pay.unified.cashier.service.depository.impl.PayStrategyDepositoryServiceImpl;
import com.youzan.pay.unified.cashier.service.dto.PayStrategyDto;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xielina on 2017/8/22.
 */
@Slf4j
@Component
public class CheckPrepaidPay implements Filter<PayToolDoRequest> {

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;

  @Resource
  private MicroAccoutServiceClient microAccoutServiceClient;

  @Resource
  private RedisCacheManager redisCacheManager;
  @Resource
  private PayStrategyDepositoryServiceImpl payStrategyDepositoryService;

  @Resource
  private PayToolsCache payToolsCache;

  @Override
  public boolean doFilter(PayToolDoRequest request) {
    if (request.getPayToolConfig().getPayTool().equals(PayToolTypeEnum.PREPAID_PAY.name())) {
      /**
       * 储值卡逻辑
       */
      //1:检查收银台策略是否可用 ,不可用直接返回
      if (!checkPayToolStrategy()) {
        return true;
      }
      //2.重新根据储值卡系统返回值构造老储值卡信息
      PayToolConfig
          payToolConfig =
          buildDepositoryCardPayTool(request.getCashierH5SearchPayToolsRequest());
      //如果用户储值卡信息为空，直接返回
      if (payToolConfig == null) {
        return true;
      }
      //如果余额为零，直接返回
      if(payToolConfig.getBalance()==0){
        return true;
      }
      request.setPayToolConfig(payToolConfig);

      if (request.getPayToolConfig().isVisible()&&request.getPayToolConfig().isAvailable()) {

        PayChannel paychannel = buildPayChannel(request);
        request.getPayChannelList().add(paychannel);
      }
      return true;
    }
    return false;
  }

  /**
   * 储值卡余额展现
   */
  private PayToolConfig buildDepositoryCardPayTool(CashierH5SearchPayToolsRequest request) {
    PayToolConfig payToolConfig = new PayToolConfig();
    if (checkPayToolStrategy()) {
      payToolConfig = buildDepositoryPayToolConfig(request);
    }
    return payToolConfig;
  }

  /**
   * 储值卡支付余额等信息
   */
  private PayToolConfig buildDepositoryPayToolConfig(CashierH5SearchPayToolsRequest request) {
    PayToolConfig payToolConfig = new PayToolConfig();
    LogUtils.info(log, "[userId={}]", request.getBuyerId());

    if (isNumeric(request.getBuyerId())) {
      UserMerchantDto userMerchantDto = getUserMerchantDto(request);
      LogUtils.info(log, "[userMerchantDto={}]", userMerchantDto);
      payToolConfig = getPayToolDepositoryConfig(userMerchantDto, request);
    }
    return payToolConfig;

  }

  private UserMerchantDto getUserMerchantDto(CashierH5SearchPayToolsRequest request) {
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
                                                   CashierH5SearchPayToolsRequest request) {
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

  public PayChannel buildPayChannel(PayToolDoRequest request) {
    PayChannel payChannel = new PayChannel();

    payChannel.setPayChannel(request.getPayToolConfig().getPayTool());
    payChannel.setVisible(request.getPayToolConfig().isVisible());
    payChannel.setVisible_desc(request.getPayToolConfig().getVisibleDesc());
    //1:卡片是否需要密码
    payChannel.setNeed_password(false);

    //2.设置卡片名字
    payChannel
        .setPayChannelName(PayToolTypeEnum.getChannelName(request.getPayToolConfig().getPayTool()));

    //3:卡状态及其提示
    long payAmount = request.getCashierH5SearchPayToolsRequest().getPayAmount();
    boolean available = checkBalance(payAmount, request.getPayToolConfig().getBalance());

    payChannel.setAvailable(available);
    payChannel
        .setAvailable_desc(getAvailableDesc(available, request.getPayToolConfig().getBalance()));

    //4:设置支付余额
    payChannel.setPayAmount(String.format("%.2f", payAmount / 100.0));

    return payChannel;

  }

  private boolean checkBalance(long payAmount, long balance) {
    return (balance >= payAmount);
  }

  private String getAvailableDesc(boolean enoughBalance, long balance) {

    if (enoughBalance) {

      return AvailableDescEnum.BALANCE.getDesc()
             + CurrencyEnum.RMB_YUAN.getDesc() + String
                 .format("%.2f",
                         balance / 100.0);
    } else {
      return AvailableDescEnum.BALANCE_INSUFFICIENT.getDesc()
             + CurrencyEnum.RMB_YUAN.getDesc()
             + String.format("%.2f", balance / 100.0);
    }
  }

  /**
   * 判断收银台数据库储值卡策略是否开启
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

}
