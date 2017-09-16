package com.youzan.pay.unified.cashier.api.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tao.ke Date: 2017/6/21 Time: 上午10:30
 */
@ToString(callSuper = true)
@Getter
@Setter
public class BaseCardPayRequest extends BaseCashierRequest implements Serializable {

  private static final long serialVersionUID = 1152365288119461506L;

  @NotNull(message = "支付方式不能为空")
  private String payTool;

  @NotNull(message = "收单流水号不能为空")
  private String acquireNo;

  private int acceptPrice;

  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;

  @NotNull(message = "商品描述不能为空")
  private String tradeDesc;

  @Min(value = 0, message = "支付金额不能小于0")
  private long payAmount = 0;

  @Min(value = 0, message = "参与折扣金额不能小于0")
  private long discountableAmount = 0;

  @Min(value = 0, message = "不参与折扣金额不能小于0")
  private long undiscountableAmount = 0;

  private String partnerNotifyUrl;

  private String partnerReturnUrl;

  private String authCode;

  private String memo;

  private String wxSubOpenId;

  // 改价后的价格
  private long newPrice;


}
