/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.CashierH5AcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierH5AcquireOrderResult;

/**
 * @author wulonghui
 * @version CashierCreateAcquireOrderService.java, v 0.1 2017-06-02 17:52
 */
public interface CashierCreateAcquireOrderService {

  /**
   *H5收银台创建收单
   * @param request
   * @return
   */
  public Response<CashierH5AcquireOrderResult> create(CashierH5AcquireOrderRequest request);

}
