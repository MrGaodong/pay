/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 支付方式类
 *
 * @author wulonghui
 * @version PayChannel.java, v 0.1 2017-01-12 10:33
 */
@Data
public class PayChannel implements Serializable {

  private static final long serialVersionUID = -3624880705904063154L;
  /**
   * 支付渠道名
   */
  private String payChannel;

  /**
   * 渠道是否可用标志
   */
  private boolean available;

  /**
   * 渠道是否可见标志
   */
  private boolean visible;

  /**
   * 是否可用描述
   */
  private String available_desc;

  /**
   * 是否可见描述
   */
  private String visible_desc;

  /**
   * 支付方式名称
   */
  private String payChannelName;

  /**
   * 是否包装展示
   */
  private boolean should_wrap = false;

  /**
   * 是否需要密码
   */
  private boolean need_password = false;
  /**
   * 支付金额 默认为空串
   */
  private String payAmount="";

  /**
   * 预付卡卡号
   */
  private String valueCardNo="";

  /**
   * 确认框
   */
  private boolean need_confirm=false;
}
