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
 * @version QRCodePayResult.java, v 0.1 2017-06-01 14:25
 */
@Data
public class QRCodePayResult implements Serializable {

  private static final long serialVersionUID = -1782206504721800994L;

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
