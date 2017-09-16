/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 支付详情结果
 *
 * @author saber
 * @version PayDetailResult.java, v 0.1 2017-01-10 17:37
 */
@Data
public class PayDetailResult implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = 6845244456836219170L;
  /**
   * 支付号
   */
  private String assetDetailNo;

  /**
   * 渠道清算流水号,即支付渠道单据号。例如使用支付宝,则为支付宝返回的支付流水号
   */
  private String channelSettleNo;

  /**
   * 支付完成时间
   */
  private Date payDate;

  /**
   * 实际支付金额
   */
  private long payAmount;

  /**
   * 支付结果状态枚举,默认是已经创建状态
   */
  private String status;

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
   * "client": "alipay.wap.create.direct.pay.by.user",
   * "total_fee": "0.01",
   * "sign_type": "RSA",
   * "seller_id": "2088311028700xxx"
   * }
   * </code>
   */
  private Map<String, Object> deepLinkInfo = new HashMap<>();

  /**
   * 扩展字段
   */
  private Map<String, String> extendInfo = new HashMap<String, String>();

}
