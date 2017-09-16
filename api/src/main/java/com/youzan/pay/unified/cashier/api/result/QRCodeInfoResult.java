/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * 支付码信息
 *
 * @author twb
 * @version QRCodeInfoResult.java, v 0.1 2017-05-26 10:49
 */
@Data
public class QRCodeInfoResult implements Serializable {

  private static final long serialVersionUID = -3995234373981460214L;

  /**
   * 唯一标识
   */
  private String codeId;

  /**
   * 内容
   */
  private String content;

  /**
   * 图片格式
   */
  private String imageFormat;

  /**
   * 二维码格式
   */
  private String codeFormat;

  /**
   * 内容编码类型
   */
  private String encoding;

  /**
   * 宽度
   */
  private int width;

  /**
   * 高度
   */
  private int height;
}
