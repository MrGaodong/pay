package com.youzan.pay.unified.cashier.core.utils.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author tao.ke Date: 2017/6/29 Time: 上午10:21
 */
public class StringNumProcessor {

  /**
   * 将字符串转换为数字，如果为空，则返回0
   */
  public static Long convertToLong(String num) {

    if (StringUtils.isBlank(num)) {
      return 0L;
    }
    return Long.valueOf(num);
  }

}
