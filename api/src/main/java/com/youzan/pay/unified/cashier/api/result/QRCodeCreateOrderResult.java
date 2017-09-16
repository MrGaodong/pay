/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author twb
 * @version QRCodeCreateOrderResult.java, v 0.1 2017-06-19 20:49
 */
@Data
public class QRCodeCreateOrderResult implements Serializable {

  private static final long serialVersionUID = -4025541744781077713L;

  // 0 可以支付
  // 1 已经失效
  private int status;

  private String acquireNo;
}
