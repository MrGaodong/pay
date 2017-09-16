package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-09-03
 * Time: 下午1:50
 * <p></p>
 */
@Data
public class CashierOrderResult implements Serializable{

  private static final long serialVersionUID = 5404932123388664155L;

  /**
   * 收银台创建收单结果
   *
   * @Param 收单号
   **/

  private String unifiedAcquireOrder;

  /**
   * 支付结果页
   *
   * @Param 支付结果地址
   */
  private String needReturnPayResultUrl;

}