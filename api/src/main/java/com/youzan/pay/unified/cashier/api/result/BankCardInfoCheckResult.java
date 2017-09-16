package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午5:24
 */
@Data
public class BankCardInfoCheckResult implements Serializable {

  private static final long serialVersionUID = 7757640738849795007L;

  /**
   * 银行卡卡类型
   */
  private String cardType;

  /**
   * 银行名称
   */
  private String bankName;

  /**
   * 银行code码
   */
  private String bankCode;

  /**
   * 银行卡id
   */
  private String cardNo;

  /**
   * 银行卡号是否有效
   */
  private boolean valid;

  /**
   * 渠道，maybe
   */
  private String channel;
}
