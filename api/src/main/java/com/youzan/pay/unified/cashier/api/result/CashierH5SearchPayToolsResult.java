/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import com.youzan.pay.unified.cashier.api.request.PayChannel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierH5SearchPayToolsResult.java, v 0.1 2017-05-18 14:36
 */
@Data
public class CashierH5SearchPayToolsResult implements Serializable{

  private static final long serialVersionUID = 89786927701433443L;

  /**
   * 渠道查询结果
   */
  private List<PayChannel> payChannels = new ArrayList<>();

}
