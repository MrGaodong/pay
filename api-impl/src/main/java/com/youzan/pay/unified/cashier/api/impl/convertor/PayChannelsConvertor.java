/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.convertor;

import com.youzan.pay.unified.cashier.api.request.PayChannel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 支付方式重排
 *
 * @author wulonghui
 * @version PayChannelsConvertor.java, v 0.1 2017-03-01 11:27
 */
public class PayChannelsConvertor {

  /**
   * 支付方式重排
   */
  public static List<PayChannel> convert(List<PayChannel> payChannels) {

    List<PayChannel> payChannels1 = new LinkedList<>();

    for (Iterator<PayChannel> iterator = payChannels.iterator(); iterator.hasNext(); ) {
      PayChannel payChannel = iterator.next();
      if (!payChannel.isAvailable()) {
        setPayChannels(iterator, payChannels1, payChannel);
      }
    }

    /**
     * list合并
     */
    payChannels.addAll(payChannels1);

    return payChannels;
  }

  /**
   * 不可用的支付方式放入队尾
   */
  private static void setPayChannels(Iterator<PayChannel> iterator, List<PayChannel> payChannels,
                                     PayChannel payChannel) {
    iterator.remove();
    payChannels.add(payChannel);
  }

}
