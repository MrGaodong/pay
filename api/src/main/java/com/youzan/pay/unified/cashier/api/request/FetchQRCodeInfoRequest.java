/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取redis中的二维码信息请求
 *
 * @author twb
 * @version FetchQRCodeInfoRequest.java, v 0.1 2017-06-23 11:37
 */
@Data
public class FetchQRCodeInfoRequest implements Serializable {

  private static final long serialVersionUID = -495144090880125666L;

  @NotNull(message = "cache imageNo can not be empty")
  private String imageNo;
}
