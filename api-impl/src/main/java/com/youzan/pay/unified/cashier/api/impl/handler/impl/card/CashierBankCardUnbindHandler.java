package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardUnbindRequest;
import com.youzan.pay.unified.cashier.service.PayAccountManager;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/15 Time: 上午11:25
 */
@Component
@Slf4j
public class CashierBankCardUnbindHandler extends
        AbstractHandler<BankCardUnbindRequest, Boolean> {

  @Resource
  private BankCardManager bankCardManager;

  @Resource
  private PayAccountManager payAccountManager;

  @Override
  protected void validate(BankCardUnbindRequest request) {

    if (StringUtils.isEmpty(request.getBindId())){
      throw new IllegalArgumentException("绑卡ID非法");
    }

    if (StringUtils.isEmpty(request.getBuyerId()) && StringUtils.isEmpty(request.getCustomerId())){
      throw new IllegalArgumentException("用户ID非法");
    }
  }

  @Override
  protected Boolean execute(BankCardUnbindRequest request) throws Exception {

    return bankCardManager.unbindBankCard(request);
  }
}
