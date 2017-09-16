/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wulonghui
 * @version AbstractPayTypeList.java, v 0.1 2017-03-01 16:30
 */
public abstract class AbstractPayTypeList {

  /**
   * 组合支付方式
   */
  protected void setPayChannels(List<PayChannel> payChannels, PayToolConfig payToolConfig) {
    if (payToolConfig.isVisible()) {
      PayChannel payChannel = new PayChannel();
      payChannel.setPayChannelName(PayToolTypeEnum.getChannelName(payToolConfig.getPayTool()));
      payChannel.setAvailable_desc(payToolConfig.getAvailableDesc());
      payChannel.setAvailable(payToolConfig.isAvailable());
      payChannel.setVisible_desc(payToolConfig.getVisibleDesc());
      payChannel.setVisible(payToolConfig.isVisible());
      payChannel.setPayChannel(payToolConfig.getPayTool());
      payChannels.add(payChannel);
    }
  }

  protected List<String> gentradePayToolList(UnifiedSearchPayTypeRequest request) {

    List<String> tradePayToolList = new LinkedList<>();

    String tradePayTools = getPayTools(request);

    String[] tradePayToolsArray = tradePayTools.split(",");

    for (String tradePayTool : tradePayToolsArray) {
      tradePayToolList.add(tradePayTool);
    }

    return tradePayToolList;
  }

  protected String getPayEviorment(UnifiedSearchPayTypeRequest request) {
    return request.getPayEnviorment();
  }

  protected String getPayTools(UnifiedSearchPayTypeRequest request) {
    return request.getPayTools();
  }

  protected void setDesc(PayToolConfig payToolConfig) {
    if (payToolConfig.isAvailable()) {
      payToolConfig.setAvailableDesc("");
    }
    if (payToolConfig.isVisible()) {
      payToolConfig.setVisibleDesc("");
    }
  }

}
