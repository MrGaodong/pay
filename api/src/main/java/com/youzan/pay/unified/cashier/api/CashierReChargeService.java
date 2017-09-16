/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.CashierReChargePayRequest;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeCreateRequest;
import com.youzan.pay.unified.cashier.api.request.CashierRechargeGetDataRequest;
import com.youzan.pay.unified.cashier.api.request.group.recharge.CashierReChargeGenUrlRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeCreateResult;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargeGenUrlResult;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierReChargePayResult;
import com.youzan.pay.unified.cashier.api.result.recharge.CashierRechargeGetDataResult;

/**收银台充值接口
 * @author wulonghui
 * @version CashierReChargeService.java, v 0.1 2017-06-06 11:19
 */
public interface CashierReChargeService {

  Response<CashierReChargeCreateResult> create(CashierRechargeCreateRequest cashierRechargeCreateRequest);

  Response<CashierRechargeGetDataResult> getRechargeDataFromRedis(CashierRechargeGetDataRequest cashierRechargeGetDataRequest);


  Response<CashierReChargePayResult> recharge(CashierReChargePayRequest cashierReChargePayRequest);

  Response<CashierReChargeGenUrlResult> genCashier(CashierReChargeGenUrlRequest cashierRechargeCreateRequest);
}
