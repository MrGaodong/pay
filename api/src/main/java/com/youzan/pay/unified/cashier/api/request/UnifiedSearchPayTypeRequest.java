/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 查询支付方式请求类
 *
 * @author wulonghui
 * @version UnifiedSearchPayTypeRequest.java, v 0.1 2017-01-12 10:29
 */
@Data
public class UnifiedSearchPayTypeRequest implements Serializable {

  /**
   * 支付环境
   *
   * @See
   */
  private String payEnviorment;

  /**
   * 交易支持支付方式
   */
  @NotNull(message = "支付方式不能为空")
  private String payTools;

  /**
   * 支付金额
   */
    /* 支付金额不能为0 */
  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount;

  /**
   * 买家id
   */
  private String buyerId;

  /* 收款方账号不能为空 */
  @NotNull(message = "收款方账号不能为空")
  private String mchId;

  /**
   * 合作方账号
   */
  private String partnerId;

  /**
   * 新交易排除支付方式列表
   */
  private String excludePayTools;
}
