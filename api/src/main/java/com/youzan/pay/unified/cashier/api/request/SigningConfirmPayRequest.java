package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午8:42
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SigningConfirmPayRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = -1960600564499304259L;

  /**
   * 支付流水号
   */
  private String targetId;

  /**
   * 签约支付渠道
   */
  private String bindId;

  /**
   * 验证码
   */
  private String verificationCode;

  /**
   * 记录支付数据到数据组使用
   */
  private String orderNo;
}
