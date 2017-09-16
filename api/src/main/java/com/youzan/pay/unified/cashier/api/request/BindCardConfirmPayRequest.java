package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午7:47
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BindCardConfirmPayRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = -1201869820586491172L;

  /**
   * 绑卡id
   */
  private String bindId;

  /**
   * 验证码
   */
  private String verificationCode;

  /**
   * 支付流水号
   */
  private String targetId;

  /**
   * 补全六要素其中某些数据
   */
  private CardSixElementsDTO sixElements;

  /**
   * 记录支付数据到数据组使用
   */
  private String orderNo;
}
