/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author twb
 * @version QRCodeRechargeResult.java, v 0.1 2017-06-16 11:39
 */
@Data
public class QRCodeRechargeResult implements Serializable {

  private static final long serialVersionUID = -1559456895535192677L;

  /**
   * 外部订单号
   */
  private String outBizNo;

  /**
   * 收单单据号
   */
  private String acquireNo;

  /**
   *
   */
  private String deepLinkInfo;
}
