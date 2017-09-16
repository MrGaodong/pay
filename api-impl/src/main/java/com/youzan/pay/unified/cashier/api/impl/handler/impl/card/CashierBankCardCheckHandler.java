package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BankCardInfoCheckRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardInfoCheckResult;
import com.youzan.pay.unified.cashier.service.card.BankCardManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 上午10:55
 */
@Component
@Slf4j
public class CashierBankCardCheckHandler
    extends AbstractHandler<BankCardInfoCheckRequest, BankCardInfoCheckResult> {

  /**
   * 最大最小银行卡位数
   */
  private static final int MIN_CARD_NO_LENGTH = 16;
  private static final int MAX_CARD_NO_LENGTH = 21;

  @Resource
  private BankCardManager bankCardManager;

  @Override
  protected void preProcessParams(BankCardInfoCheckRequest request){

    // 清除前端空格
    request.setCardNo(StringUtils.deleteWhitespace(request.getCardNo()));

  }

  @Override
  protected void validate(BankCardInfoCheckRequest request) {

    if (StringUtils.isEmpty(request.getCardNo())) {
      throw new IllegalArgumentException("输入16-21位的银行卡数字卡号");
    }

    if (!StringUtils.isNumeric(request.getCardNo())) {
      throw new IllegalArgumentException("输入16-21位的银行卡数字卡号");
    }

    if (request.getCardNo().length() < MIN_CARD_NO_LENGTH
        || request.getCardNo().length() > MAX_CARD_NO_LENGTH) {
      throw new IllegalArgumentException("输入16-21位的银行卡数字卡号");
    }

    {
      if (StringUtils.isEmpty(request.getPartnerId())) {
        throw new IllegalArgumentException("合作伙伴编码非法");
      }
    }

  }

  @Override
  protected BankCardInfoCheckResult execute(BankCardInfoCheckRequest request) throws Exception {

    return bankCardManager.queryCardValidatorInfo(request);
  }

}
