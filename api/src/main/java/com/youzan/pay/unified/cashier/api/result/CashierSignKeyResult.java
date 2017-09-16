/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierSignKeyResult.java, v 0.1 2017-05-04 11:36
 */
@Data
public class CashierSignKeyResult implements Serializable {

  private static final long serialVersionUID = -1237850439486020L;

  String signKey;
}
