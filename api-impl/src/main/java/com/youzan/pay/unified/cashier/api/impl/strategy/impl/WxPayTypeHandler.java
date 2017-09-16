/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.strategy.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.enums.PayEviormentEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.TradePayToolEnum;
import com.youzan.pay.unified.cashier.api.impl.strategy.AbstractPayTypeList;
import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.platform.util.lang.StringUtil;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付方式处理类
 *
 * @author wulonghui
 * @version WxPayTypeHandler.java, v 0.1 2017-03-01 16:28
 */
@Slf4j
public class WxPayTypeHandler extends AbstractPayTypeList implements PayTypeListHandler {

  @Override
  public void genCashierPayTool(UnifiedSearchPayTypeRequest request, PayToolConfig payToolConfig,
                                List<PayChannel> payChannels) {

    List<String> tradePayToolList = gentradePayToolList(request);

    String payEnviorment = request.getPayEnviorment();

    if (StringUtil.isBlank(payEnviorment)) {
      payEnviorment = "WX_H5";
    }

    if (payEnviorment.equals(PayEviormentEnum.ALIPAYWAP.getCode())) {
      LogUtils.info(log, "[支付宝wap支付环境，不支持微信支付]");
      return;
    }

    if (!tradePayToolList.contains(TradePayToolEnum.NOW_PAY.getCode())) {
      LogUtils.warn(log, "[交易没有传nowpay,微信支付需要隐藏,partnerId={},mchId={}]", request.getPartnerId(),
                    request.getMchId());
      return;
    }
    /**
     * wxjs和wxh5支付逻辑
     */
    LogUtils.info(log,"[wxPayTool={}]",payToolConfig.getPayTool());
    if (payToolConfig.getPayTool().equals(PayToolTypeEnum.WX_JS.name())) {
      if(payEnviorment.equals(PayEviormentEnum.WX_JS.getCode())){
        setDesc(payToolConfig);
        setPayChannels(payChannels, payToolConfig);
      }
    } else if (payToolConfig.getPayTool().equals(PayToolTypeEnum.WX_H5.name())) {
      if(payEnviorment.equals(PayEviormentEnum.WX_H5.getCode())){
        setDesc(payToolConfig);
        setPayChannels(payChannels, payToolConfig);
      }
    }
  }

  @Override
  public void genCashierH5PayTool(CashierH5SearchPayToolsRequest request,
                                  PayToolConfig payToolConfig, List<PayChannel> payChannels) {
    String payEnviorment = request.getPayEnviorment();

    if (StringUtil.isBlank(payEnviorment)) {
      payEnviorment = "";
    }

    if (payEnviorment.equals(PayEviormentEnum.ALIPAYWAP.getCode())) {
      LogUtils.info(log, "[支付宝wap支付环境，不支持微信支付]");
      return;
    }
    buildPayChannels(payToolConfig,payChannels);
  }

  private void buildPayChannels(PayToolConfig payToolConfig, List<PayChannel> payChannels) {
    setDesc(payToolConfig);
    setPayChannels(payChannels, payToolConfig);
  }
}
