/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 收银台订单查询请求
 *
 * @author wulonghui
 * @version CashierOrderStatusSearchRequest.java, v 0.1 2017-04-12 15:59
 */
@Data
public class CashierOrderStatusSearchRequest implements Serializable {

  private static final long serialVersionUID = 3L;

  /**
   * 收单号
   */
  private String acquireNo;

  /**
   * 外部订单号
   */
  private String outBizNo;
}
