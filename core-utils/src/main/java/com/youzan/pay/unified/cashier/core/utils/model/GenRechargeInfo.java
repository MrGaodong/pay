/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.model;

import lombok.Data;

/**
 * @author wulonghui
 * @version GenRechargeInfo.java, v 0.1 2017-06-22 15:51
 */
@Data
public class GenRechargeInfo extends CacheTradeInfo {
  private String cardNo;
  private String customerId;
  private int rechargePayType;
}
