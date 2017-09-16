package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 收银台基础请求类。对于用户的每次请求，都必须带上以下参数，标识一次请求支付双方
 *
 * @author tao.ke Date: 2017/6/9 Time: 上午10:20
 */
@Data
public class BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = -3868033082135137852L;

  /**
   * 用户id
   */
  private String buyerId;

  /**
   * 对应php中的fansId
   */
  private String customerId;

  /**
   * 对应php中的fansType
   */
  private String customerType;

  /**
   * 合作方id
   */
  private String partnerId;
  /**
   * 用户ip
   */
  private String userIp;
  /**
   * 商户id
   */
  private String mchId;

}
