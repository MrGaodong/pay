/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierH5AcquireOrderResult.java, v 0.1 2017-05-16 14:25
 */
@Data
public class CashierH5AcquireOrderResult implements Serializable{

  private static final long serialVersionUID = 6358951563703377314L;
  /**
   * 收银台创建收单结果
   *
   * @Param 收单号
   **/

  private String unifiedAcquireOrder;

  /**
   * 支付结果页
   *
   * @Param 支付结果地址
   */
  private String needReturnPayResultUrl;

  //private String msg;
}
