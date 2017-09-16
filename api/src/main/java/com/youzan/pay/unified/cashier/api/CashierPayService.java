package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.PayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.PayResult;

/**
 * Created by twb on 17/1/9.
 */
public interface CashierPayService {

  Response<PayResult> dispatcherPay(PayRequest request);
}
