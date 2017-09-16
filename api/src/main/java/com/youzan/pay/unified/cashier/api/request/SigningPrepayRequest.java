package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午8:33
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SigningPrepayRequest extends BaseCardPayRequest implements Serializable {

  private static final long serialVersionUID = -748566603453019983L;

  /**
   * 信用卡六要素
   */
  private CardSixElementsDTO sixElements;

  /**
   * 银行名称
   */
  private String bankName;

  /**
   * 银行代码
   */
  private String bankCode;

}
