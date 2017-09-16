/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.core.utils.log.LogUtils;
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
import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version EcardPayTypeHandler.java, v 0.1 2017-03-01 16:48
 */
@Slf4j
public class EcardPayTypeHandler extends AbstractPayTypeList implements PayTypeListHandler {
  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {
    //E卡余额为0，不展示E卡
    if (payToolConfig.getBalance() == 0) {
      LogUtils.warn(log, "［E卡有账户,但是账户余额为0，不展示］,buyerId={}", request.getBuyerId());
      return;
    }
    if (request.getPayAmount() <= payToolConfig.getBalance()) {
      payToolConfig
          .setAvailableDesc(AvailableDescEnum.BALANCE.getDesc() + CurrencyEnum.RMB_YUAN.getDesc()
                            + String.format("%.2f", payToolConfig.getBalance() / 100.0));
      setPayChannelsOnEcard(payChannels, payToolConfig,request.getPayAmount());
    } else if (request.getPayAmount() > payToolConfig.getBalance()) {
      payToolConfig.setAvailable(false);
      payToolConfig.setAvailableDesc(AvailableDescEnum.BALANCE_INSUFFICIENT.getDesc()
                                     + CurrencyEnum.RMB_YUAN.getDesc() + String
                                         .format("%.2f", payToolConfig.getBalance() / 100.0));
      setPayChannelsOnEcard(payChannels, payToolConfig,request.getPayAmount());
    }
  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request, PayToolConfig payToolConfig,
                                  List<PayChannel> payChannels) {
    //E卡暂时不需要实现h5的此逻辑
  }

  private void setPayChannelsOnEcard(List<PayChannel> payChannels, PayToolConfig payToolConfig,long payAmount) {
    if (payToolConfig.isVisible()) {
      PayChannel payChannel = new PayChannel();
      payChannel.setPayAmount(String.format("%.2f", payAmount / 100.0));
      payChannel.setPayChannelName(PayToolTypeEnum.getChannelName(payToolConfig.getPayTool()));
      payChannel.setAvailable_desc(payToolConfig.getAvailableDesc());
      payChannel.setAvailable(payToolConfig.isAvailable());
      payChannel.setVisible_desc(payToolConfig.getVisibleDesc());
      payChannel.setVisible(payToolConfig.isVisible());
      payChannel.setPayChannel(payToolConfig.getPayTool());
      payChannel.setNeed_password(true);
      payChannels.add(payChannel);
    }
  }

}
