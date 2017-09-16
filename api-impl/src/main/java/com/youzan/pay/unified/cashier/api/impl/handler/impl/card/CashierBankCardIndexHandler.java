package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.core.utils.validate.ValidationResult;
import com.youzan.pay.core.utils.validate.ValidationUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.RiskAbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardIndexRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardIndexResult;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/21 Time: 上午9:44
 */
@Component
@Slf4j
public class CashierBankCardIndexHandler extends
                                         RiskAbstractHandler<BankCardIndexRequest, BankCardIndexResult> {

  @Resource
  private BankCardManager bankCardManager;

  @Resource
  private BankCardPayManager bankCardPayManager;

  @Override
  protected void validate(BankCardIndexRequest request) {

    ValidationResult result = ValidationUtils.validate(request);
    if (!result.isSuccess()) {
      throw new IllegalArgumentException(result.getMessage());
    }

  }

  @Override
  protected BankCardIndexResult execute(BankCardIndexRequest request) throws Exception {

    BankCardIndexResult indexResult = new BankCardIndexResult();

    // 首先查银行卡列表
    BankCardListQueryRequest cardsQuery = new BankCardListQueryRequest();
    cardsQuery.setBuyerId(request.getBuyerId());
    cardsQuery.setCustomerId(request.getCustomerId());
    cardsQuery.setCustomerType(request.getCustomerType());
    cardsQuery.setPartnerId(request.getPartnerId());
    cardsQuery.setUserIp(request.getUserIp());
    cardsQuery.setMchId(request.getMchId());

    BankCardListQueryResult cardsResult = bankCardManager.queryCardList(cardsQuery);
    if (cardsResult == null || CollectionUtils.isEmpty(cardsResult.getCardList())) {
      return indexResult;
    }

    // 进行绑卡预支付操作
    BankCardListQueryResult.BankCard card = cardsResult.getCardList().get(0);
    request.setBindId(card.getBindId());
    request.setPhone(card.getPhone());
    request.setCardType(card.getCardName());

    // 如果是储蓄卡，则修正paytool
    if (BankCardType.DEBIT_CARD.getDesc().equals(card.getCardName())){
      request.setPayTool(BankCardType.DEBIT_CARD.name());
    }else {
      request.setPayTool(BankCardType.CREDIT_CARD.name());
    }

    BindCardPrepayResult prepayResult = bankCardPayManager.bindCardPrepay(request);

    indexResult.setCardList(cardsResult);
    indexResult.setBindCard(prepayResult);

    return indexResult;

  }

}
