package com.youzan.pay.unified.cashier.core.model.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商户监控日志类
 *
 * @author saber
 * @version ApiDigestLogInfo.java, v 0.1 2017-08-03 18:46
 */
@Data
@ToString(callSuper = true)
public class MchBizLogInfo extends BaseMonitorLogInfo {


  private String partnerId;

  private String mchId;

  private String payMethod;


}
