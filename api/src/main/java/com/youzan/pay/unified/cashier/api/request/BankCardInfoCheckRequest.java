package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午5:21
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BankCardInfoCheckRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = 7206467385308730583L;

  /**
   * 银行卡号
   */
  private String cardNo;

}
