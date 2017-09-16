/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierH5SearchPayToolsRequest.java, v 0.1 2017-05-18 14:32
 */
@Data
public class CashierH5SearchPayToolsRequest implements Serializable{

  private static final long serialVersionUID = 89786927701434L;

  /**
   * 支付环境
   *
   * @See
   */
  private String payEnviorment;


  /**
   * 支付金额
   */
   /*支付金额不能为0*/
  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount;

  /**
   * 买家id
   */
  private String buyerId;

  /*收款方账号不能为空*/
  @NotNull(message = "收款方账号不能为空")
  private String mchId;

  /**
   * 合作方账号
   */
  private String partnerId;

  //预付卡客户号
  private String customerId;

  /**
   * 新交易排除支付方式列表
   */
  private String excludePayTools;

}
