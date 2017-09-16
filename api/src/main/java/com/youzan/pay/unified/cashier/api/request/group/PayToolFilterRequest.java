/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request.group;

import com.youzan.pay.unified.cashier.api.request.PayChannel;

import lombok.Data;

/**
 * @author wulonghui
 * @version PayToolFilterRequest.java, v 0.1 2017-06-16 11:09
 */
@Data
public class PayToolFilterRequest extends PayChannel {
  //支付环境
  private String payEnviorment;
}
