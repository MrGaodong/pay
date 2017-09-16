/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils.enums;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.Getter;

/**
 * @author twb
 * @version CorrectionLevel.java, v 0.1 2017-05-24 18:03
 */
public enum CorrectionLevel {

  /** L = ~7% correction */
  L1(1, ErrorCorrectionLevel.L),
  /** M = ~15% correction */
  L2(2, ErrorCorrectionLevel.M),
  /** Q = ~25% correction */
  L3(3, ErrorCorrectionLevel.Q),
  /** H = ~30% correction */
  L4(4, ErrorCorrectionLevel.H);

  @Getter
  private int code;

  @Getter
  private ErrorCorrectionLevel level;

  CorrectionLevel(int code, ErrorCorrectionLevel level) {
    this.code = code;
    this.level = level;
  }

  public static ErrorCorrectionLevel getLevelByCode(int code) {
    ErrorCorrectionLevel level = null;
    for (CorrectionLevel value : values()) {
      if (value.getCode() == code) {
        level = value.getLevel();
        break;
      }
    }
    if (level != null) {
      return level;
    } else {
      throw new IllegalArgumentException();
    }
  }
}
