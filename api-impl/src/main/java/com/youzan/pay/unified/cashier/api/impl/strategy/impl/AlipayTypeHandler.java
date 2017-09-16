/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.enums.PayEviormentEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.TradePayToolEnum;
import com.youzan.pay.unified.cashier.api.impl.strategy.AbstractPayTypeList;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.platform.util.lang.StringUtil;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version AlipayTypeHandler.java, v 0.1 2017-03-01 16:45
 */
@Slf4j
public class AlipayTypeHandler extends AbstractPayTypeList implements PayTypeListHandler {

  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {

    List<String> tradePayToolList = gentradePayToolList(request);

    String payEnviorment = request.getPayEnviorment();

    if (StringUtil.isBlank(payEnviorment)) {
      payEnviorment = "";
    }

    if (payEnviorment.equals(PayEviormentEnum.WX_JS.getCode())) {
      LogUtils.warn(log, "[当前是微信js支付环境，不支持支付宝]");
      return;
    }

    if (payEnviorment.equals(PayEviormentEnum.QQWAP.getCode())) {
      LogUtils.warn(log, "[当前是QQwap支付环境，不支持支付宝]");
      return;
    }

    if (tradePayToolList.contains(TradePayToolEnum.NOW_PAY.getCode())) {
      setDesc(payToolConfig);
      setPayChannels(payChannels, payToolConfig);
    }
  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request, PayToolConfig payToolConfig,
                                  List<PayChannel> payChannels) {

    String payEnviorment = request.getPayEnviorment();

    if (StringUtil.isBlank(payEnviorment)) {
      payEnviorment = "";
    }

    if (payEnviorment.equals(PayEviormentEnum.WX_JS.getCode())) {
      LogUtils.warn(log, "[当前是微信js支付环境，不支持支付宝]");
      return;
    }

    if (payEnviorment.equals(PayEviormentEnum.QQWAP.getCode())) {
      LogUtils.warn(log, "[当前是QQwap支付环境，不支持支付宝]");
      return;
    }

    setDesc(payToolConfig);
    setPayChannels(payChannels, payToolConfig);

  }
}
