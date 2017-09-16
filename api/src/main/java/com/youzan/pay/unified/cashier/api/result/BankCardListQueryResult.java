package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 返回给前端的银行卡列表数据结构
 *
 * @author tao.ke Date: 2017/6/8 Time: 上午11:49
 */
@Data
public class BankCardListQueryResult implements Serializable {

  private static final long serialVersionUID = 4815860038428506850L;

  /**
   * 卡列表
   */
  private List<BankCard> cardList;

  @Data
  public static class BankCard implements Serializable {

    private static final long serialVersionUID = 5383181045814138946L;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡名：储蓄卡/行用卡
     */
    private String cardName;

    /**
     * 银行卡后四位
     */
    private String last4Code;

    /**
     * 卡id
     */
    private String bindId;

    /**
     * 电话
     */
    private String phone;

    /**
     * 银行码
     */
    private String bankCode;

    /**
     * 银行logo图标地址
     */
    private String logo;

    /**
     * 当前是否有效
     */
    private boolean valid;

    /**
     * 失效信息
     */
    private String invalidMsg;

  }
}
