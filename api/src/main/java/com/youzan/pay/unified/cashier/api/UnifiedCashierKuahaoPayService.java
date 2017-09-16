package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.KuahaoPayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.KuahaoPayResult;
import com.youzan.pay.unified.cashier.api.result.PayResult;

/**
 * Created by liumeng on 2017/7/11.
 */
public interface UnifiedCashierKuahaoPayService {

    /**
     * 唤起扫码支付
     * @param request
     * @return
     */
    Response<KuahaoPayResult> kuahaoPay(KuahaoPayRequest request);

}
