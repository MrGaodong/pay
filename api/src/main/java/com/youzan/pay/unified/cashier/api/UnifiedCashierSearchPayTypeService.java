/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.UnifiedSearchPayTypeResult;

/**
 * 查询支付方式接口
 *
 * @author wulonghui
 * @version UnifiedCashierSearchPayTypeService.java, v 0.1 2017-01-12 10:25
 */
public interface UnifiedCashierSearchPayTypeService {

  public Response<UnifiedSearchPayTypeResult> search(UnifiedSearchPayTypeRequest request);
}
