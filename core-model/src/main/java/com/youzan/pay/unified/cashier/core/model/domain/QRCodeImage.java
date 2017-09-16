/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.model.domain;

import java.util.Date;

import lombok.Data;

/**
 * @author twb
 * @version QRCodeImage.java, v 0.1 2017-05-31 16:50
 */
@Data
public class QRCodeImage {

  // 唯一编号
  private long imageNo;

  // 二维码内容，用来支付的url
  private String url;

  // 图片格式
  private String imageFormat;

  // 二维码格式
  private String codeFormat;

  // 错误校正级别
  private int errorCorrectionLevel;

  // 过期时间，millisecond
  private long expireTime;

  // 宽度
  private int width;

  // 高度
  private int height;

  // 创建时间
  private Date createTime;

  private int payStatus;
}
