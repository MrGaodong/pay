package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.BankCardIndexRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardInfoCheckRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardUnbindRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.BankCardIndexResult;
import com.youzan.pay.unified.cashier.api.result.BankCardInfoCheckResult;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;

/**
 * 银行卡相关接口：比如查询卡，绑定卡等
 *
 * @author tao.ke Date: 2017/6/8 Time: 上午11:42
 */
public interface CashierBankCardService {

  /**
   * 银行卡支付首页请求
   */
  Response<BankCardIndexResult> index(BankCardIndexRequest request);

  /**
   * 查询银行卡列表，会按照一定规则排序之后返回给调用方
   */
  Response<BankCardListQueryResult> queryBankCardList(BankCardListQueryRequest request);

  /**
   * 查询银行卡类型属性，
   */
  Response<BankCardInfoCheckResult> queryBankCardCheckInfo(BankCardInfoCheckRequest request);

  /**
   * 解绑银行卡
   */
  Response unbindBankCard(BankCardUnbindRequest request);

}
