/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result.recharge;

import com.youzan.pay.unified.cashier.api.result.TradeInfoResult;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierReChargeGenUrlResult.java, v 0.1 2017-06-22 15:37
 */
@Data
public class CashierReChargeGenUrlResult extends TradeInfoResult implements Serializable {
  private String cashierUrl;
}
