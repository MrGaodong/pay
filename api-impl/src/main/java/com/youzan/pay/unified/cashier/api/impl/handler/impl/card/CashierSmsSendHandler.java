package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardPaySmsSendRequest;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;
import com.youzan.pay.unified.cashier.service.cache.SmsSendRecordCache;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 下午8:06
 */
@Component
@Slf4j
public class CashierSmsSendHandler extends AbstractHandler<BankCardPaySmsSendRequest, Boolean> {

  @Resource
  private BankCardPayManager bankCardPayManager;

  @Resource
  private SmsSendRecordCache smsSendRecordCache;

  @Override
  protected void validate(BankCardPaySmsSendRequest request) {

    if (StringUtils.isEmpty(request.getTargetId())) {
      throw new IllegalArgumentException("支付流水号非法");
    }
  }

  @Override
  protected Boolean execute(BankCardPaySmsSendRequest request) throws Exception {

    // 1.发送短信间隔时间校验
    boolean canSend =
        smsSendRecordCache.checkNextSendSms(request.getBuyerId(), request.getTargetId());
    if (!canSend) {
      throw new CardBaseException(CardErrorCode.CHANNEL_SEND_SMS, "请求频繁");
    }

    // 2. 短信请求
    bankCardPayManager.sendConfirmSms(request);

    // 3. 如果成功了，再记录这次短信
    smsSendRecordCache.putSmsRecord(request.getBuyerId(), request.getTargetId());

    return true;
  }
}
