package com.youzan.pay.unified.cashier.api.impl.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志常量
 *
 * @author saber
 * @version L.java, v 0.1 2017-08-01 17:28
 */
public interface Loggers {

  /**
   * 服务性能日志摘要logger
   **/
  Logger DIGEST_LOGGER = LoggerFactory.getLogger("BIZ_API_DIGEST");

  /**
   * 商户日志摘要logger
   **/
  Logger MCH_LOGGER = LoggerFactory.getLogger("MCH_MONITOR");


}
