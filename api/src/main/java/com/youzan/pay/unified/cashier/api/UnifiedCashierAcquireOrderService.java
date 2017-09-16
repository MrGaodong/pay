/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.UnifiedAcquireOrderResult;

/**
 * @author wulonghui
 * @version CashierCreateOrderService.java, v 0.1 2017-01-10 13:40
 */
public interface UnifiedCashierAcquireOrderService {

  /**
   * 收银台创建收单接口
   *
   * @Param:收银台创建收单请求
   **/
  public Response<UnifiedAcquireOrderResult> create(
      UnifiedAcquireOrderRequest unifiedAcquireOrderRequest);

  public String heatbeat();
}
