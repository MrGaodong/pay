/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.unified.cashier.api.request.CashierOrderRequest;
import com.youzan.pay.unified.cashier.api.request.CreateUnifiedCashierUrlRequest;
import com.youzan.pay.unified.cashier.api.request.GetUnifiedCashierUrlRequest;
import com.youzan.pay.unified.cashier.api.result.CashierOrderResult;
import com.youzan.pay.unified.cashier.api.result.CreateUnifiedCashierUrlResult;
import com.youzan.pay.unified.cashier.api.result.GetUnifiedCashierUrlResult;

/**
 * @author wulonghui
 * @version CreateUnifiedCashierUrlService.java, v 0.1 2017-08-30 15:55
 * 创建普通列表收银台链接服务，支持dubbo和rest
 */
public interface CreateUnifiedCashierUrlService {

  DataResult<CreateUnifiedCashierUrlResult> create(
      CreateUnifiedCashierUrlRequest createUnifiedCashierUrlRequest)
      throws Exception;

  DataResult<CashierOrderResult> createOrder(CashierOrderRequest request);

  DataResult<GetUnifiedCashierUrlResult> get(GetUnifiedCashierUrlRequest request);

}
