/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result.recharge;

import com.youzan.pay.unified.cashier.api.result.TradeInfoListResult;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierRechargeGetDataResult.java, v 0.1 2017-06-22 17:12
 */
@Data
public class CashierRechargeGetDataResult extends TradeInfoListResult implements Serializable {
  private String cardNo;
  private String customerId;
  private int rechargePayType;
}
