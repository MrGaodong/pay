package com.youzan.pay.unified.cashier.api.impl.handler.impl.card;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.RiskAbstractHandler;
import com.youzan.pay.unified.cashier.api.request.BindCardConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.CardSixElementsDTO;
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
 * @author tao.ke Date: 2017/6/13 Time: 下午8:05
 */
@Component
@Slf4j
public class CashierBindPayConfirmHandler
    extends AbstractHandler<BindCardConfirmPayRequest, ConfirmPayResult> {

  /**
   * CVV码长度
   */
  private static final int CVV_LENGTH = 3;

  /**
   * 有效期码长度
   */
  private static final int VALID_DATE_LENGTH = 5;

  @Resource
  private BankCardPayManager bankCardPayManager;

  @Resource
  private PayCardRecordCache payCardRecordCache;

  @Resource
  private PayInfoSendDataGroupManager payInfoSendDataGroupManager;

  @Override
  protected void validate(BindCardConfirmPayRequest request) {

    if (StringUtils.isEmpty(request.getBindId())) {
      throw new IllegalArgumentException("绑卡ID非法");
    }

    if (StringUtils.isEmpty(request.getTargetId())) {
      throw new IllegalArgumentException("支付单ID非法");
    }

    if (StringUtils.isEmpty(request.getVerificationCode())) {
      throw new IllegalArgumentException("验证码非法");
    }

    if (StringUtils.isEmpty(request.getOrderNo())) {
      throw new IllegalArgumentException("外部订单号非法");
    }

    CardSixElementsDTO sixElements = request.getSixElements();

    if (sixElements != null){
      if (StringUtils.isNotBlank(sixElements.getCcvCode()) && sixElements.getCcvCode().length() != CVV_LENGTH){
        throw new IllegalArgumentException("请输入3位CVV码");
      }

      if (StringUtils.isNotBlank(sixElements.getValidDate()) && sixElements.getValidDate().length() != VALID_DATE_LENGTH){
        throw new IllegalArgumentException("请输入4位有效期");
      }
    }
  }

  @Override
  protected ConfirmPayResult execute(BindCardConfirmPayRequest request) throws Exception {

    // 发起确认支付
    ConfirmPayResult payResult = bankCardPayManager.bindCardPayConfirm(request);

    // 增加本次支付选卡缓存
    String key = payCardRecordCache
        .buildKey(request.getBuyerId(), request.getCustomerId(), request.getCustomerType());

    if (key != null) {
      payCardRecordCache.putBindId(key, request.getBindId());
    }

    // 发送数据到数据组
    payInfoSendDataGroupManager
        .sendInfoToDG(request.getOrderNo(), request.getUserIp(), payResult.getPhone(), PayMethodType.EEPAY);

    return payResult;

  }
}
