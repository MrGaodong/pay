/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result.recharge;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierReChargePayResult.java, v 0.1 2017-06-26 14:47
 */
@Data
public class CashierReChargePayResult implements Serializable{
  private String operation;

  private String deepLinkInfo;

  private long newPrice;

  public CashierReChargePayResult() {
  }

  public CashierReChargePayResult(String operation) {
    this.operation = operation;
  }

  public CashierReChargePayResult(String operation, long newPrice) {
    this.operation = operation;
    this.newPrice = newPrice;
  }
}
