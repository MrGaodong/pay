package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;
import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.core.utils.validate.ValidationResult;
import com.youzan.pay.core.utils.validate.ValidationUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.RiskAbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BindCardPrepayRequest;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 下午8:05
 */
@Component
@Slf4j
public class CashierBindPrepayHandler extends
                                      AbstractHandler<BindCardPrepayRequest, BindCardPrepayResult> {

  @Resource
  private BankCardPayManager bankCardPayManager;

  @Override
  protected void validate(BindCardPrepayRequest request) {

    if (StringUtils.isEmpty(request.getBindId())) {
      throw new IllegalArgumentException("绑卡ID非法");
    }

    ValidationResult result = ValidationUtils.validate(request);
    if (!result.isSuccess()) {
      throw new IllegalArgumentException(result.getMessage());
    }
  }

  @Override
  protected BindCardPrepayResult execute(BindCardPrepayRequest request) throws Exception {

    return bankCardPayManager.bindCardPrepay(request);

  }

  @Override
  protected void doBefore(BindCardPrepayRequest request) {
    super.doBefore(request);

    // 如果是储蓄卡，则修正paytool
    if (BankCardType.DEBIT_CARD.getDesc().equals(request.getCardType())) {
      request.setPayTool(BankCardType.DEBIT_CARD.name());
    }
  }

}
