package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;
import com.youzan.pay.unified.cashier.service.PayAccountManager;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 上午11:02
 */
@Component
@Slf4j
public class CashierBankCardListHandler
    extends AbstractHandler<BankCardListQueryRequest, BankCardListQueryResult> {

  @Resource
  private BankCardManager bankCardManager;

  @Resource
  private PayAccountManager payAccountManager;

  @Override
  protected void validate(BankCardListQueryRequest request) {

    if (StringUtils.isEmpty(request.getBuyerId()) && StringUtils.isEmpty(request.getCustomerId())){
      throw new IllegalArgumentException("请求用户ID不存在");
    }
  }

  @Override
  protected BankCardListQueryResult execute(BankCardListQueryRequest request) throws Exception {

    return bankCardManager.queryCardList(request);
  }
}
