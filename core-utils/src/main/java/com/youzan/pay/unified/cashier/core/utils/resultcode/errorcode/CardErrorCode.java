package com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode;

import com.youzan.pay.unified.cashier.core.utils.resultcode.ModuleCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.SystemCode;

/**
 * @author tao.ke Date: 2017/6/13 Time: 上午10:18
 */
public enum CardErrorCode implements ResultCode {

  SUCCESS("000", "处理成功", "处理成功"),

  FAIL("001", "处理失败", "处理失败"),

  UNKNOWN_ERROR("002", "未知异常","请刷新重试"),

  SYSTEM_ERROR("003", "系统异常","请刷新重试"),

  ILLEGAL_ARGUMENTS("004", "非法参数","请检查参数信息"),


  CHANNEL_CARD_CHECK("101", "渠道调用-银行卡校验-异常","请稍后刷新重试"),

  CHANNEL_SEND_SMS("102", "渠道调用-发送短信请求-异常","请稍后刷新重试"),

  CHANNEL_CONFIRM_PAY("103", "渠道调用-确认支付-异常","请稍后刷新重试"),

  CHANNEL_CHECK_BIND("104", "渠道调用-绑卡ID校验-异常","请稍后刷新重试"),


  ASSET_CENTER_ORDER_PAY("201", "收单调用-发起支付-异常","请稍后刷新重试"),

  MERCHANT_CARD_LIST("301", "商户调用-卡表查询-异常","请稍后刷新重试"),

  MERCHANT_CARD_UNBIND("302", "商户调用-解绑卡-异常","请稍后刷新重试"),

  MERCHANT_CARD_BIND("303", "商户调用-绑卡-异常","请稍后刷新重试"),


  ACCOUNT_MERCHANT_USERNO("401", "用户中心调用-支付账户查询-异常","请稍后刷新重试"),;

  private String errorCode;

  private String desc;

  /**
   * 对外展示的信息
   */
  private String viewInfo;

  CardErrorCode(String errorCode, String desc, String viewInfo) {
    this.errorCode = errorCode;
    this.desc = desc;
    this.viewInfo = viewInfo;
  }

  @Override
  public ModuleCode getModuleCode() {
    return ModuleCode.CHANNEL_CARD;
  }

  @Override
  public String getCode() {
    return ResultCode.build(SystemCode.UNIFIED_CASHIER, getModuleCode(), this.errorCode);
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

  public String getViewInfo() {
    return viewInfo;
  }
}
