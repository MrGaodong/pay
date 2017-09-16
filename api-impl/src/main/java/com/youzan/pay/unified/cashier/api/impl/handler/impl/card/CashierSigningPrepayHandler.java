package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.core.utils.validate.ValidationResult;
import com.youzan.pay.core.utils.validate.ValidationUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CardSixElementsDTO;
import com.youzan.pay.unified.cashier.api.request.SigningPrepayRequest;
import com.youzan.pay.unified.cashier.api.result.SigningPrepayResult;
import com.youzan.pay.unified.cashier.service.card.BankCardPayManager;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 下午8:06
 */
@Component
@Slf4j
public class CashierSigningPrepayHandler
    extends AbstractHandler<SigningPrepayRequest, SigningPrepayResult> {

  /**
   * 手机号码长度，最大最小
   */
  private static final int PHONE_LENGTH = 11;

  /**
   * CVV码长度
   */
  private static final int CVV_LENGTH = 3;

  /**
   * 有效期码长度
   */
  private static final int VALID_DATE_LENGTH = 5;

  /**
   * 身份证最小，最大长度
   */
  private static final int ID_CARD_LENGTH = 18;


  /**
   * 当前支持的卡类型
   */
  private static final Set<String> CURRENT_SUPPORT_CARD_TYPES =
      Sets.newHashSet(BankCardType.CREDIT_CARD.getDesc(), BankCardType.DEBIT_CARD.getDesc());

  @Resource
  private BankCardPayManager bankCardPayManager;

  @Override
  protected void preProcessParams(SigningPrepayRequest request) {

    CardSixElementsDTO sixElements = request.getSixElements();
    if (request.getSixElements() == null) {
      throw new IllegalArgumentException("绑卡要素为空");
    }

    // 清除前端用户输入的空格
    sixElements.setName(StringUtils.trim(sixElements.getName()));
    sixElements.setCcvCode(StringUtils.deleteWhitespace(sixElements.getCcvCode()));
    sixElements.setPhone(StringUtils.deleteWhitespace(sixElements.getPhone()));
    sixElements.setIdCardNo(StringUtils.deleteWhitespace(sixElements.getIdCardNo()));
    sixElements.setCardNo(StringUtils.deleteWhitespace(sixElements.getCardNo()));

  }

  @Override
  protected void validate(SigningPrepayRequest request) {

    CardSixElementsDTO sixElements = request.getSixElements();

    if (sixElements == null || StringUtils.isEmpty(sixElements.getCardType())
        || !CURRENT_SUPPORT_CARD_TYPES.contains(sixElements.getCardType())) {
      throw new IllegalArgumentException("当前只支持" + Joiner.on(',').join(CURRENT_SUPPORT_CARD_TYPES));
    }

    if (StringUtils.isEmpty(sixElements.getName())) {
      throw new IllegalArgumentException("请输入正确的姓名");
    }

    if (StringUtils.isEmpty(sixElements.getPhone())) {
      throw new IllegalArgumentException("请输入正确的手机号码");
    }

    if (sixElements.getPhone().length() != PHONE_LENGTH) {
      throw new IllegalArgumentException("请输入正确的手机号码");
    }

    if (StringUtils.isNotBlank(sixElements.getCcvCode()) && sixElements.getCcvCode().length() != CVV_LENGTH) {
      throw new IllegalArgumentException("请输入3位CVV码");
    }

    if (StringUtils.isNotBlank(sixElements.getValidDate())
        && sixElements.getValidDate().length() != VALID_DATE_LENGTH) {
      throw new IllegalArgumentException("请输入4位有效期");
    }

    String month = StringUtils.substring(sixElements.getValidDate(), 0, 2);
    if (!StringUtils.isNumeric(month) || Integer.valueOf(month) > 12){
      throw new IllegalArgumentException("请输入正确的[月份/年份]有效期");
    }

    if (StringUtils.isBlank(sixElements.getIdCardNo())) {
      throw new IllegalArgumentException("请输入正确的身份证号");
    }

    if (sixElements.getIdCardNo().length() != ID_CARD_LENGTH) {
      throw new IllegalArgumentException("请输入正确的身份证号");
    }

    ValidationResult result = ValidationUtils.validate(request);
    if (!result.isSuccess()) {
      throw new IllegalArgumentException(result.getMessage());
    }
  }

  @Override
  protected void doBefore(SigningPrepayRequest request) {
    super.doBefore(request);

    // 如果是储蓄卡，则修正paytool
    if (BankCardType.DEBIT_CARD.getDesc().equals(request.getSixElements().getCardType())) {
      request.setPayTool(BankCardType.DEBIT_CARD.name());
    }
  }

  @Override
  protected SigningPrepayResult execute(SigningPrepayRequest request) throws Exception {

    return bankCardPayManager.signingPrepay(request);
  }
}
