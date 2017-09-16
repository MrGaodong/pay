/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.enums.AvailableDescEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.CurrencyEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.strategy.AbstractPayTypeList;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;

import java.util.List;

/**
 * @author wulonghui
 */
public class PrepaidPayHandler extends AbstractPayTypeList implements PayTypeListHandler {
  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {
    //如果余额为零，直接返回
    if(payToolConfig.getBalance()==0){
      return ;
    }

    if (request.getPayAmount() <= payToolConfig.getBalance()) {
      payToolConfig
          .setAvailableDesc(AvailableDescEnum.BALANCE.getDesc() + CurrencyEnum.RMB_YUAN.getDesc()
                            + String.format("%.2f", payToolConfig.getBalance() / 100.0));
      setPayChannelsOnDepositoryCard(payChannels, payToolConfig);
    } else if (request.getPayAmount() > payToolConfig.getBalance()) {
      payToolConfig.setAvailable(false);
      payToolConfig.setAvailableDesc(AvailableDescEnum.BALANCE_INSUFFICIENT.getDesc()
                                     + CurrencyEnum.RMB_YUAN.getDesc() + String
                                         .format("%.2f", payToolConfig.getBalance() / 100.0));
      setPayChannelsOnDepositoryCard(payChannels, payToolConfig);
    }
  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request, PayToolConfig payToolConfig,
                                  List<PayChannel> payChannels) {
    //礼品卡暂时不需要实现h5的此逻辑
  }

  private  void setPayChannelsOnDepositoryCard(List<PayChannel> payChannels,PayToolConfig payToolConfig){
    if (payToolConfig.isVisible()) {
      PayChannel payChannel = new PayChannel();
      payChannel.setPayChannelName(PayToolTypeEnum.getChannelName(payToolConfig.getPayTool()));
      payChannel.setAvailable_desc(payToolConfig.getAvailableDesc());
      payChannel.setAvailable(payToolConfig.isAvailable());
      payChannel.setVisible_desc(payToolConfig.getVisibleDesc());
      payChannel.setVisible(payToolConfig.isVisible());
      payChannel.setPayChannel(payToolConfig.getPayTool());
      payChannel.setNeed_password(false);
      payChannels.add(payChannel);
    }
  }
}
