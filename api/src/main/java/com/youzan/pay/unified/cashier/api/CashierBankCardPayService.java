package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.BankCardPaySmsSendRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardPrepayRequest;
import com.youzan.pay.unified.cashier.api.request.SigningConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.SigningPrepayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.api.result.SigningPrepayResult;

/**
 * 银行卡支付相关操作接口
 *
 * @author tao.ke Date: 2017/6/8 Time: 下午8:04
 */
public interface CashierBankCardPayService {

  /**
   * 绑卡支付第一步：发起绑卡支付，经过到收单系统，同时从渠道具体路由到的bindId给发起支付使用
   *
   * @param request 请求参数
   * @return 收单系统会返回一个收单id，作为后面确认支付的orderId
   */
  Response<BindCardPrepayResult> prepayForBindCardPay(BindCardPrepayRequest request);

  /**
   * 绑卡支付第二部：确认支付。
   *
   * @param request 主要是验证收到的验证码，完成支付扣款请求
   * @return 成功/失败原因。如果成功，记录此次选择的银行卡到redis中
   */
  Response<ConfirmPayResult> confirmForBindCardPay(BindCardConfirmPayRequest request);

  /**
   * 发送验证码短信请求（需要check 60s之后才能第二次获取验证码）
   *
   * @param request 根据发起绑卡支付/签约支付逻辑，使用绑卡id和/或支付订单号请求第三方发送短信
   * @return 成功/失败
   */
  Response sendSms(BankCardPaySmsSendRequest request);

  /**
   * 签约支付之预支付方法
   *
   * @param request 六要素及订单数据
   * @return 支付订单号
   */
  Response<SigningPrepayResult> prepayForSigningPay(SigningPrepayRequest request);

  /**
   * 签约支付第二步：确认支付。
   *
   * @param request 主要是验证收到的验证码，完成支付扣款请求
   * @return 签约id等数据由各内部系统操作，如果成功，收银台只需要单纯true即可。如果成功，记录此次选择的银行卡到redis中
   */
  Response<ConfirmPayResult> confirmForSigningPay(SigningConfirmPayRequest request);
}
