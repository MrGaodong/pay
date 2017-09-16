package com.youzan.pay.unified.cashier.core.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author tao.ke Date: 2017/6/28 Time: 下午9:53
 */
public class DataMixProcessor {

  /**
   * 电话号码模糊的位数
   */
  private static final int PHONE_MIX_DIGIT = 4;

  /**
   * 电话号码模糊，一般模糊中间4位，例如18667179836--> 186****9836
   */
  public static String phoneNumMix(String phone) {

    int len = phone.length();
    if (len <= PHONE_MIX_DIGIT) {
      return phone;
    }

    int leftPos = (len - PHONE_MIX_DIGIT) / 2;
    char[] chars = phone.toCharArray();

    for (int i = leftPos; i < leftPos + PHONE_MIX_DIGIT; i++) {
      chars[i] = '*';
    }

    return new String(chars);
  }

}
