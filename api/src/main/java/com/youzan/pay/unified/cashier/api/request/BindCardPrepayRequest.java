package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 发起绑卡预支付请求参数 http://doc.qima-inc.com/pages/viewpage.action?pageId=15143737
 *
 * @author tao.ke Date: 2017/6/8 Time: 下午4:55
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BindCardPrepayRequest extends BaseCardPayRequest implements Serializable {

  private static final long serialVersionUID = -2887147282406451376L;

  /**
   * 绑卡id
   */
  private String bindId;

  /**
   * 手机号
   */
  private String phone;

  /**
   * 卡类型
   */
  private String cardType;


}
