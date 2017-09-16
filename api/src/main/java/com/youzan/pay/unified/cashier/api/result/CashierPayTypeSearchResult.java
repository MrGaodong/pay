/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import com.youzan.pay.unified.cashier.api.request.PayChannel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 支付方式列表
 *
 * @author wulonghui
 * @version CashierPayTypeSearchResult.java, v 0.1 2017-04-12 15:15
 */
@Data
public class CashierPayTypeSearchResult implements Serializable {

  private static final long serialVersionUID = -1237850439486020972L;
  /**
   * 商户支持支付方式列表
   */
  List<PayChannel> payChannels = new ArrayList<>();

}
