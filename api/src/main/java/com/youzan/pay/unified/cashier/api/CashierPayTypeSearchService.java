/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.CashierPayTypeSearchRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierH5SearchPayToolsResult;
import com.youzan.pay.unified.cashier.api.result.CashierPayTypeSearchResult;

/**
 * @author wulonghui
 * @version CashierPayTypeSearchService.java, v 0.1 2017-04-12 14:46
 */
public interface CashierPayTypeSearchService {

  /**
   * 根据合作方id查询商户支持支付方式
   */
  Response<CashierPayTypeSearchResult> payTypeSearch(
      CashierPayTypeSearchRequest cashierPayTypeSearchRequest);

  /**
   * 查询H5收银台支持的支付方式
   * @param cashierH5SearchPayToolsRequest
   * @return
   */
  public Response<CashierH5SearchPayToolsResult> payTypeSearchForH5(
      CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest);
}
