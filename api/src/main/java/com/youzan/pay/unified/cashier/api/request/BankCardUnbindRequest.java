package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/15 Time: 上午11:06
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BankCardUnbindRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = 2078118566061316231L;

  /**
   * 绑卡id
   */
  private String bindId;

}
