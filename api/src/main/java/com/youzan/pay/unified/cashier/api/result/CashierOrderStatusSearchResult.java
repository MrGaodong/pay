/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * 订单状态查询结果
 *
 * @author wulonghui
 * @version CashierOrderStatusSearchResult.java, v 0.1 2017-04-12 16:04
 */
@Data
public class CashierOrderStatusSearchResult implements Serializable {

  private static final long serialVersionUID = 5812017638247224504L;
  /**
   * 收单状态
   */
  private String acquireQueryStatus;

  /**
   * 收单号
   */
  private String acquireNo;

//支付金额
  private long payAmount;

  //支付工具
  private String payTool;

}
