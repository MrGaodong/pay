package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.SigningConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.service.cache.PayCardRecordCache;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;
import com.youzan.pay.unified.cashier.service.http.PayInfoSendDataGroupManager;
import com.youzan.pay.unified.cashier.service.http.PayMethodType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 下午8:06
 */
@Component
@Slf4j
public class CashierSigningPayConfirmHandler
    extends AbstractHandler<SigningConfirmPayRequest, ConfirmPayResult> {

  @Resource
  private BankCardPayManager bankCardPayManager;

  @Resource
  private PayCardRecordCache payCardRecordCache;

  @Resource
  private PayInfoSendDataGroupManager payInfoSendDataGroupManager;

  @Override
  protected void preProcessParams(SigningConfirmPayRequest request){

    // 清除前端空格
    request.setVerificationCode(StringUtils.deleteWhitespace(request.getVerificationCode()));

  }

  @Override
  protected void validate(SigningConfirmPayRequest request) {

    // bindId这里可能没有

    if (StringUtils.isEmpty(request.getTargetId())) {
      throw new IllegalArgumentException("支付流水号非法");
    }

    if (StringUtils.isEmpty(request.getVerificationCode())) {
      throw new IllegalArgumentException("验证码非法");
    }

    if (StringUtils.isEmpty(request.getOrderNo())) {
      throw new IllegalArgumentException("外部订单号非法");
    }
  }

  @Override
  protected ConfirmPayResult execute(SigningConfirmPayRequest request) throws Exception {

    ConfirmPayResult result = bankCardPayManager.signingPayConfirm(request);

    String key = payCardRecordCache
        .buildKey(request.getBuyerId(), request.getCustomerId(), request.getCustomerType());

    if (key != null) {
      payCardRecordCache.putBindId(key, request.getBindId());
    }

    // 发送数据到数据组
    payInfoSendDataGroupManager.sendInfoToDG(request.getOrderNo(), request.getUserIp(), result.getPhone(),
                                             PayMethodType.EEPAY);

    return result;
  }
}
