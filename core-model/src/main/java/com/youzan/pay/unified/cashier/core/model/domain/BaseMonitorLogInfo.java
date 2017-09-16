package com.youzan.pay.unified.cashier.core.model.domain;


import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 监控日志基类。
 *
 * @author saber
 * @version BaseLogInfo.java, v 0.1 2017-08-03 19:38
 */
@Data
public class BaseMonitorLogInfo implements Serializable {

  private String api;

  private long duration;

  //结果：F  or  S
  private String result;


  public static String SUCCESS_RESULT = "S";

  public static String FAIL_RESULT = "F";

  //幂等结果
  public static String IDEMPOTENT_RESULT = "I";

}
