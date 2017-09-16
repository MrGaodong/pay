/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.impl.strategy.AbstractPayTypeList;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 储蓄卡支付方式处理类
 *
 * @author wulonghui
 * @version WxPayTypeHandler.java, v 0.1 2017-03-01 16:28
 */
@Slf4j
public class DebitCardHandler extends AbstractPayTypeList implements PayTypeListHandler {

  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {


      setDesc(payToolConfig);
      setPayChannels(payChannels, payToolConfig);


  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request,
                                  PayToolConfig payToolConfig, List<PayChannel> payChannels) {

  }
}
