/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.constant;

/**
 * @author twb
 * @version Constant.java, v 0.1 2017-06-19 14:49
 */
public class Constant {

  public final static String signKey = "eeq5JL*vdhuKM@s!MWP9$rYK&TXAUV20*EFxZ8#oozsvg2oD";
  public final static String signType = "MD5";
  public final static String PAY_URL_PREFIX = "https://cashier.youzan.com/pay/qRCodePay";
  public final static String RECHARGE_URL_PREFIX = "https://cashier.youzan.com/pay/qRCodeRecharge";
  public final static long TIME_TO_LIVE = 15 * 60 * 1000;
  public final static String CASHIER_URL="https://cashier.youzan.com/pay/cashier?sign=";
}
