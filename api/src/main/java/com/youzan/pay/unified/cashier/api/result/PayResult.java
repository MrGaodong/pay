/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author twb
 * @version PayResult.java, v 0.1 2016-12-21 17:08
 */
@Data
public class PayResult implements Serializable {

  private String operation;

  // private Map<String, Object> deepLinkInfo = new HashMap<>();
  private String deepLinkInfo;

  private long newPrice;

  public PayResult() {
  }

  public PayResult(String operation) {
    this.operation = operation;
  }

  public PayResult(String operation, long newPrice) {
    this.operation = operation;
    this.newPrice = newPrice;
  }
}
