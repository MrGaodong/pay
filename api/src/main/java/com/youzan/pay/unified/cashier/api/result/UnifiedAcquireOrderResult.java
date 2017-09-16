/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version OrderCreateResult.java, v 0.1 2017-01-10 13:42
 */
@Data
public class UnifiedAcquireOrderResult implements Serializable {

  /**
   * 收银台创建收单结果
   *
   **/

  private String unifiedAquireOrder;

  /**
   * 支付结果页
   *
   */
  private String needReturnPayResultUrl;

  // private String msg;
}
