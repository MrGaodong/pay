package com.youzan.pay.unified.cashier.core.utils.exception;

import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * @author tao.ke Date: 2017/6/14 Time: 下午3:23
 */
public class CardBaseException extends RuntimeException {

  /**
   * 结果码
   */
  @Getter
  private String code;

  /**
   * 错误描述
   */
  @Getter
  private String desc;

  public CardBaseException(String code, String desc) {
    super(desc);
    this.code = code;
    this.desc = desc;
  }

  public CardBaseException(CardErrorCode code, String desc) {

    super((desc = StringUtils.isEmpty(desc) ? code.getViewInfo() : desc));

    this.code = code.getCode();
    this.desc = desc;
  }

}
