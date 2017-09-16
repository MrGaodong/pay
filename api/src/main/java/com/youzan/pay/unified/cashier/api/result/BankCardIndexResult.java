package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/21 Time: 上午9:39
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BankCardIndexResult implements Serializable {

  private static final long serialVersionUID = -7867316947268000095L;

  /**
   * 卡列表
   */
  private BankCardListQueryResult cardList;

  /**
   * 卡预支付数据
   */
  private BindCardPrepayResult bindCard;
}
