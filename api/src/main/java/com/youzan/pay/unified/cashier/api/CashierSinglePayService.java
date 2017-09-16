package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.CashierSinglePayRequest;
import com.youzan.pay.unified.cashier.api.response.CashierSinglePayResponse;
import com.youzan.pay.unified.cashier.api.response.Response;

/**
 * @author: clong
 * @date: 2017-04-14
 */
public interface CashierSinglePayService {

  /**
   * 收银台单次支付
   */
  Response<CashierSinglePayResponse> singlePay(CashierSinglePayRequest request);

}
