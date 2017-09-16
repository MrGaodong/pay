/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.CashierOrderStatusSearchRequest;
import com.youzan.pay.unified.cashier.api.request.CashierSignKeyRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierOrderStatusSearchResult;
import com.youzan.pay.unified.cashier.api.result.CashierSignKeyResult;

/**
 * h5收银台订单状态查询请求
 *
 * @author wulonghui
 * @version CashierOrderStatusSearchService.java, v 0.1 2017-04-12 15:54
 */

public interface CashierOrderStatusSearchService {

  /**
   * 收单状态查询结果
   */
  Response<CashierOrderStatusSearchResult> queryOrder(
      CashierOrderStatusSearchRequest cashierOrderStatusSearchRequest);

  /**
   * 查询商户秘钥
   */
  Response<CashierSignKeyResult> querySignKey(CashierSignKeyRequest cashierSignKeyRequest);

  String heartbeat();
}
