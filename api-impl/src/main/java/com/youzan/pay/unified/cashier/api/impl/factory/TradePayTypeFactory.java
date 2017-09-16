/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.factory;

import com.youzan.pay.unified.cashier.api.impl.strategy.PayTypeListHandler;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author wulonghui
 * @version TradePayTypeFactory.java, v 0.1 2017-03-01 15:27
 */
@Data
@Component
public class TradePayTypeFactory {

  private Map<String, PayTypeListHandler> tradePayTypeMap = new HashMap<>();

  public PayTypeListHandler getTradePayType(String payType) {

    if (CollectionUtils.isEmpty(tradePayTypeMap)) {
      return null;
    }

    return tradePayTypeMap.get(payType);
  }
}
