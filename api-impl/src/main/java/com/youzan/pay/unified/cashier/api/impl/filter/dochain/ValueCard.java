package com.youzan.pay.unified.cashier.api.impl.filter.dochain;

import com.youzan.pay.cardvoucher.api.summarycard.dto.SummaryCardInfoDTO;
import com.youzan.pay.cardvoucher.api.summarycard.request.QuerySummaryCardInfoRequest;
import com.youzan.pay.core.api.model.response.ListResult;

import com.youzan.pay.unified.cashier.api.impl.enums.AvailableDescEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.CurrencyEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.impl.model.filter.PayToolDoRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.intergration.client.SummaryCardQueryServiceClient;
import com.youzan.pay.unified.cashier.service.cache.PayToolsCache;
import com.youzan.pay.unified.cashier.service.convert.ConvertManager;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


/**
 * @Descrption 过滤用户预付卡服务 Created by xielina on 2017/8/2.
 */
public final class ValueCard implements Filter<PayToolDoRequest> {

  //预付卡卡号key
  private static final String valueCardNo="prepaidcard";

  private static final String DEFAULT="yzpay";
  @Resource
  private SummaryCardQueryServiceClient summaryCardQueryServiceClient;

  @Resource
  private ConvertManager convertManager;

  @Resource
  private PayToolsCache payToolsCache;


  @Override
  public boolean doFilter(PayToolDoRequest request) {
    if (request.getPayToolConfig().getPayTool()
        .equals(PayToolTypeEnum.VALUE_CARD.name())) {
      //1:检查收银台策略是否可用 ,不可用直接返回
      if(!checkPayToolStrategy()){
        return true;
      }

      //预付卡客户号
      String customerId = convertManager.getUserNo(request.getCashierH5SearchPayToolsRequest().getBuyerId());

      //构造预付卡消息请求参数
      QuerySummaryCardInfoRequest querySummaryCardInfoRequest = new QuerySummaryCardInfoRequest();
      querySummaryCardInfoRequest
          .setCustomerId(customerId);
      querySummaryCardInfoRequest
          .setPublishMerchantId(request.getCashierH5SearchPayToolsRequest().getMchId());

      //去卡券平台查询用户预付卡
      ListResult<SummaryCardInfoDTO>
          result =
          summaryCardQueryServiceClient.querySummaryCardInfo(querySummaryCardInfoRequest);

      //对卡券平台返回的用户预付卡结果进行处理
      /**
       * todo 预付卡任务：以后支持不同模板多卡展示
       * 目前预付卡只支持用户拥有一张卡。超过一张不展示
       */
      if (result == null || result.getData().isEmpty() || result.getData().size() > 1) {
        return true;
      }

      SummaryCardInfoDTO summaryCardInfoDTO = result.getData().get(0);

      //如果余额为零，直接返回
      if(summaryCardInfoDTO.getDenomination()==0){
        return true;
      }

      PayChannel payChannel = buildPayChannel(request, summaryCardInfoDTO);

      request.getPayChannelList().add(payChannel);
      return true;
    }

    return false;
  }

  /**
   * 对卡券平台返回的用户预付卡结果进行处理
   */
  private PayChannel buildPayChannel(PayToolDoRequest request,
                                     SummaryCardInfoDTO summaryCardInfoDTO) {

    PayChannel payChannel = new PayChannel();
    payChannel.setPayChannel(request.getPayToolConfig().getPayTool());
    payChannel.setVisible(request.getPayToolConfig().isVisible());
    payChannel.setVisible_desc(request.getPayToolConfig().getVisibleDesc());

    //1:卡片是否需要密码
    payChannel.setNeed_password(false);

    //2.设置卡片名字
    String channelName = this.handlePayChannnelName(request, summaryCardInfoDTO.getCardName());

    payChannel.setPayChannelName(channelName);

    //3:卡状态及其提示
    long payAmount = request.getCashierH5SearchPayToolsRequest().getPayAmount();
    boolean available = checkBalance(payAmount, summaryCardInfoDTO.getDenomination());

    payChannel.setAvailable(available);
    payChannel.setAvailable_desc(getAvailableDesc(available, summaryCardInfoDTO.getDenomination()));

    //4:设置支付余额
    payChannel.setPayAmount(String.format("%.2f", payAmount / 100.0));

    //5.加入卡号信息
    payChannel.setValueCardNo(summaryCardInfoDTO.getCardNo());

    //6.确认框
    payChannel.setNeed_confirm(true);

    return payChannel;
  }

  private String handlePayChannnelName(PayToolDoRequest request,
                                       String cardName) {
    if (StringUtils.isEmpty(cardName)
        || cardName.length() > 10) {
      return PayToolTypeEnum.getChannelName(request.getPayToolConfig().getPayTool());
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

  /**
   * 判断收银台数据库储值卡策略是否开启
   */
  private boolean checkPayToolStrategy() {
    Map<String,PayStrategyDo> payToolsCacheMap = payToolsCache.getPayCacheMap();
    PayStrategyDo payStrategyDo= payToolsCacheMap.get("PAY1002");

    //3.判断老储值卡是否可用以及是否可见
    if (payStrategyDo.getAvailable().equals(1) && payStrategyDo.getVisible().equals(1)
        && payStrategyDo.getPayChannel().equals(PayToolTypeEnum.VALUE_CARD.name())) {
      return true;
    }
    return false;

  }

}
