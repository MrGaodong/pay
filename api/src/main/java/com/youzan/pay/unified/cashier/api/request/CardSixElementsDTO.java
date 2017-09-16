package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 银行卡六要素(包含四要素)
 *
 * @author tao.ke Date: 2017/6/8 Time: 下午7:38
 */
@Data
@ToString(exclude = {"cardNo", "validDate", "ccvCode", "idCardNo"})
public class CardSixElementsDTO implements Serializable {

  private static final long serialVersionUID = 663989998507324066L;

  /**
   * 银行卡号
   */
  private String cardNo;

  /**
   * 手机号
   */
  private String phone;

  /**
   * 信用卡有效期
   */
  private String validDate;

  /**
   * 信用卡ccv码
   */
  private String ccvCode;

  /**
   * 身份证号
   */
  private String idCardNo;

  /**
   * 姓名
   */
  private String name;

  /**
   * 证件类型。 com.youzan.pay.fundchannel.open.enums.IdCardType
   */
  private String idCardType;

  /**
   * 卡类型：储蓄卡/信用卡
   */
  private String cardType;

}
