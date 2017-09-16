/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.TradePayToolEnum;
import com.youzan.pay.unified.cashier.api.impl.strategy.AbstractPayTypeList;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;

import java.util.List;

/**
 * 找人代付和货到付款
 *
 * @author wulonghui
 * @version AgentDeleveryPayTypeHandler.java, v 0.1 2017-03-01 16:51
 */
public class AgentDeleveryPayTypeHandler extends AbstractPayTypeList implements PayTypeListHandler {

  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {

    List<String> tradePayToolList = gentradePayToolList(request);

    if (tradePayToolList.contains(TradePayToolEnum.CASH_ON_DELIVERY.getCode())
        && payToolConfig.getPayTool().equals(PayToolTypeEnum.CASH_ON_DELIVERY.getCode())) {
      setDesc(payToolConfig);
      setPayChannelsOnDelivery(payChannels, payToolConfig);
    }
    if (tradePayToolList.contains(TradePayToolEnum.PEER_PAY.getCode())
        && payToolConfig.getPayTool().equals(PayToolTypeEnum.PEERPAY.getCode())) {
      setDesc(payToolConfig);
      setPayChannels(payChannels, payToolConfig);
    }
  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request, PayToolConfig payToolConfig,
                                  List<PayChannel> payChannels) {
    //暂时不需要实现h5的接口
  }

  private void setPayChannelsOnDelivery(List<PayChannel> payChannels, PayToolConfig payToolConfig) {
    if (payToolConfig.isVisible()) {
      PayChannel payChannel = new PayChannel();
      payChannel.setPayChannelName(PayToolTypeEnum.getChannelName(payToolConfig.getPayTool()));
      payChannel.setAvailable_desc(payToolConfig.getAvailableDesc());
      payChannel.setAvailable(payToolConfig.isAvailable());
      payChannel.setVisible_desc(payToolConfig.getVisibleDesc());
      payChannel.setVisible(payToolConfig.isVisible());
      payChannel.setPayChannel(payToolConfig.getPayTool());
      payChannel.setShould_wrap(true);
      payChannels.add(payChannel);
    }
  }
}
