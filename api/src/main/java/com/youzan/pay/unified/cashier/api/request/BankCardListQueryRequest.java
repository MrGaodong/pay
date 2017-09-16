package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询用户绑定银行卡列表请求参数
 *
 * @author tao.ke Date: 2017/6/8 Time: 上午11:43
 */
@Getter
@Setter
@ToString(callSuper = true)
public class BankCardListQueryRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = 8031180254011724857L;

}
