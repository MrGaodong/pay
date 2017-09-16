package com.youzan.pay.unified.cashier.api.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author: clong
 * @date: 2017-04-14
 */
@Data
public class CashierSinglePayResponse implements Serializable {

  private static final long serialVersionUID = -1039899785247592224L;
  /**
   * 外部订单号
   */
  private String outBizNo;

  /**
   * 收单单据号
   */
  private String acquireNo;

  /**
   * 唤起三方支付渠道H5收银台信息：目前支持（如微信、支付宝） 微信返回数据示例： <code>
   * {
   * "appId”:”xx”,
   * "timeStamp”:”xxx”,
   * "nonceStr":”xxx”,
   * "package”:”prepay_id=xxxx”
   * "signType","MD5”,
   * "paySign”:”xxx”
   * }
   * </code>
   *
   * 支付宝返回数据示例： <code>
   * {
   * "submit_url": "https://mapi.alipay.com/gateway.do?_input_charset=utf-8",
   * "submit_data": {
   * "_input_charset": "utf-8",
   * "sign": "xxx",
   * "it_b_pay": "1d",
   * "notify_url": "https://open.koudaitong.com/gw/payment/aliwap.pay/1.0.0/notify",
   * "payment_type": "1",
   * "partner": "2088311028700xxx",
   * "service": "alipay.wap.create.direct.pay.by.user",
   * "total_fee": "0.01",
   * "sign_type": "RSA",
   * "seller_id": "2088311028700xxx"
   * }
   * </code>
   */
  private Map<String, Object> deepLinkInfo = new HashMap<>();
}
