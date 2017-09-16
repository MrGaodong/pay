/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wulonghui
 * @version CashierSignKeyRequest.java, v 0.1 2017-05-04 11:40
 */
@Data
public class CashierSignKeyRequest implements Serializable {

  private static final long serialVersionUID = 89786927L;

  private String partnerId;
}
