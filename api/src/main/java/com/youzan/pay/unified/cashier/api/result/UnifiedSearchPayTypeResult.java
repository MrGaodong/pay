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
 * 查询支付方式请求类
 *
 * @author wulonghui
 * @version UnifiedSearchPayTypeResult.java, v 0.1 2017-01-12 10:29
 */
@Data
public class UnifiedSearchPayTypeResult implements Serializable {

  /**
   * 渠道查询结果
   */
  private List<PayChannel> payChannels = new ArrayList<>();

}
