/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.request.PayChannel;

import java.util.LinkedList;
import java.util.List;

/**
 * 支付方式列表抽象类
 *
 * @author wulonghui
 * @version AbstractSearchPayTypeHandler.java, v 0.1 2017-03-01 11:54
 */
public abstract class AbstractSearchPayTypeHandler<T, R> extends AbstractHandler<T, R> {

  /**
   * 获取支付方式列表
   */
  protected abstract String getPayTools(T request);

  /**
   * 筛选支付方式
   */
  protected abstract R execute(T request, List<PayToolConfig> payToolconfigs);

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

  /**
   * 支付方式公用方法
   */
  protected R searchPayToolList(T request, List<PayToolConfig> payToolconfigs) {
    return execute(request, payToolconfigs);
  }

  /**
   * 生成交易支持支付方式列表
   */
  protected List<String> gentradePayToolList(T request) {

    List<String> tradePayToolList = new LinkedList<>();

    String tradePayTools = getPayTools(request);

    String[] tradePayToolsArray = tradePayTools.split(",");

    for (String tradePayTool : tradePayToolsArray) {
      tradePayToolList.add(tradePayTool);
    }

    return tradePayToolList;
  }

}
