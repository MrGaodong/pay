package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.cardvoucher.api.summarycard.dto.SummaryCardInfoDTO;
import com.youzan.pay.cardvoucher.api.summarycard.request.QuerySummaryCardInfoRequest;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.enums.AvailableDescEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.CurrencyEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.intergration.client.SummaryCardQueryServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayToolsCache;
import com.youzan.pay.unified.cashier.service.convert.ConvertManager;
import com.youzan.pay.unified.cashier.service.depository.PayStrategyDepositoryService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xielina on 2017/8/21.
 */
@Data
@Component
@Slf4j
public class ValueCardHandler implements PayTypeListHandler {

  @Resource
  private SummaryCardQueryServiceClient summaryCardQueryServiceClient;

  @Resource
  private PayStrategyDepositoryService payStrategyDepositoryService;

  @Resource
  private RedisCacheManager redisCacheManager;

  @Resource
  private ConvertManager convertManager;

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;

  @Resource
  private PayToolsCache payToolsCache;
  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {
    if(!checkPayToolStrategy()){
      return ;
    }
    //将buyerId转成userNo
    String customerId = convertManager.getUserNo(request.getBuyerId());

    //构造预付卡消息请求参数
    QuerySummaryCardInfoRequest querySummaryCardInfoRequest = new QuerySummaryCardInfoRequest();
    querySummaryCardInfoRequest
        .setCustomerId(customerId);
    querySummaryCardInfoRequest
        .setPublishMerchantId(request.getMchId());

    //去卡券平台查询用户预付卡
    ListResult<SummaryCardInfoDTO>
        result =
        summaryCardQueryServiceClient.querySummaryCardInfo(querySummaryCardInfoRequest);

    //对卡券平台返回的用户预付卡结果进行处理
    /**
     * todo 预付卡任务：以后支持不同模板多卡展示
     * 目前预付卡只支持用户拥有一张卡。超过一张不展示
     */
    if (result == null || result.getData().isEmpty()||result.getData().size() > 1) {
      return ;
    }

    SummaryCardInfoDTO summaryCardInfoDTO = result.getData().get(0);

    //如果余额为零，直接返回
    if(summaryCardInfoDTO.getDenomination()==0){
      return ;
    }


    PayChannel payChannel = buildPayChannel(request,payToolConfig,summaryCardInfoDTO);

   payChannels.add(payChannel);

  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request,
                                  PayToolConfig payToolConfig, List<PayChannel> payChannels) {

  }


  /**
   * 对卡券平台返回的用户预付卡结果进行处理
   */
  private PayChannel buildPayChannel(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig, SummaryCardInfoDTO summaryCardInfoDTO) {

    PayChannel payChannel = new PayChannel();
    payChannel.setPayChannel(payToolConfig.getPayTool());
    payChannel.setVisible(payToolConfig.isVisible());
    payChannel.setVisible_desc(payToolConfig.getVisibleDesc());

    //1:卡片是否需要密码
    payChannel.setNeed_password(false);

    //2.设置卡片名字
    String channelName = this.handlePayChannnelName(payToolConfig, summaryCardInfoDTO.getCardName());

    payChannel.setPayChannelName(channelName);

    //3:卡状态及其提示
    long payAmount =request.getPayAmount();
    boolean available = checkBalance(payAmount,summaryCardInfoDTO.getDenomination());

    payChannel.setAvailable(available);
    payChannel.setAvailable_desc(getAvailableDesc(available,summaryCardInfoDTO.getDenomination()));

    //4:设置支付余额
    payChannel.setPayAmount(String.format("%.2f", payAmount / 100.0));
    //5.设置卡号
    payChannel.setValueCardNo(summaryCardInfoDTO.getCardNo());

    return payChannel;
  }

  private String handlePayChannnelName( PayToolConfig payToolConfig,
                                       String cardName) {
    if (StringUtils.isEmpty(cardName)
        || cardName.length() > 10) {
      return PayToolTypeEnum.getChannelName(payToolConfig.getPayTool());
    }
    return cardName;
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
  private boolean checkPayToolStrategy() {
    Map<String,PayStrategyDo> payToolsCacheMap = payToolsCache.getPayCacheMap();
    PayStrategyDo payStrategyDo= payToolsCacheMap.get("PAY1002");

    //3.判断预付卡卡是否可用以及是否可见
    if (payStrategyDo.getAvailable().equals(1) && payStrategyDo.getVisible().equals(1)
        && payStrategyDo.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD.name())) {
      return true;
    }
    return false;

  }

}
