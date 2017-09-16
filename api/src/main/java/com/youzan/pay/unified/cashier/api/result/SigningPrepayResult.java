package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 签约支付中发起绑卡并收单的返回结果，由于第一次信用卡绑卡支付并不会返回bindId，所以这里和正常落单一样
 * <p/>
 * 为了扩展，这里单独拉出一个类表示绑卡返回
 *
 * @author tao.ke Date: 2017/6/8 Time: 下午8:35
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SigningPrepayResult extends PayResult implements Serializable {

  private static final long serialVersionUID = 132334886748475563L;

  /**
   * 外部业务订单号
   */
  private String outBizNo;

  /**
   * 支付流水号
   */
  private String targetId;

  /**
   * 绑卡id
   */
  private String bindId;

}
