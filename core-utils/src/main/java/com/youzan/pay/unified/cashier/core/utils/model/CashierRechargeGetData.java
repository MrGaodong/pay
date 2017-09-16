/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.model;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierRechargeGetData.java, v 0.1 2017-06-22 18:01
 */
@Data
public class CashierRechargeGetData extends CacheTradeInfo {
  private String cardNo;
  private String customerId;
  private int rechargePayType;
}
