package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 银行卡跳转首页相关请求参数,由于涉及到会主动发起预支付操作，所以带上相关参数
 *
 * @author tao.ke Date: 2017/6/21 Time: 上午9:28
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BankCardIndexRequest extends BindCardPrepayRequest implements RiskIpInfo {

  private static final long serialVersionUID = 1814846601387170658L;

  @Override
  public String getUserAgentIp() {
    return getUserIp();
  }

}
