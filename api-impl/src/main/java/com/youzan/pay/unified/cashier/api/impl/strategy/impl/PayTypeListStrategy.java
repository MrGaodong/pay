/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.factory.TradePayTypeFactory;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version PayTypeListStrategy.java, v 0.1 2017-03-01 16:12
 */
@Data
@Component
@Slf4j
public class PayTypeListStrategy {

  @Resource
  private TradePayTypeFactory tradePayTypeFactory;

  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {
    PayTypeListHandler
        payTypeListHandler =
        tradePayTypeFactory.getTradePayType(payToolConfig.getPayTool());


    if (null == payTypeListHandler) {
      LogUtils
          .warn(log, "tradePayTypeFactory获取payTypeListHandler失败，payToolConfig：{}", payToolConfig);
      return;
    }

    payTypeListHandler.genCashierPayTool(request, payToolConfig, payChannels);
  }

  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request, PayToolConfig payToolConfig
                                    , List<PayChannel> payChannels){
    PayTypeListHandler
        payTypeListHandler =
        tradePayTypeFactory.getTradePayType(payToolConfig.getPayTool());

    if (null == payTypeListHandler) {
      LogUtils
          .warn(log, "获取payTypeListHandler失败，payToolConfig：{}", payToolConfig);
      return;
    }

    payTypeListHandler.genCashierH5PayTool(request, payToolConfig, payChannels);
  }


}
