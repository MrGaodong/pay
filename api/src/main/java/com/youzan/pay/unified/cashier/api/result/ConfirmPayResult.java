package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author tao.ke Date: 2017/6/15 Time: 下午5:59
 */
@Data
public class ConfirmPayResult implements Serializable {

  private static final long serialVersionUID = 1019183541926113330L;

  /**
   * 是否确认支付成功
   */
  private boolean paySuccess;

  /**
   * 支付单号
   */
  private String payOrderNo;

  /**
   * 外部渠道交易号
   */
  private String outTradeNo;

  /**
   * 银行缩写代码
   */
  private String bankCode;

  /**
   * 银行卡号后四位
   */
  private String last4Code;

  /**
   * 银行遗留电话
   */
  private String phone;

}
