package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午8:20
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BankCardPaySmsSendRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = -2269832959588411819L;

  /**
   * 支付流水号
   */
  private String targetId;

  /**
   * 验证码类型：默认为1，短信验证；2位语音验证码
   */
  private String smsType;
}
