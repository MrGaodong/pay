package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author tao.ke Date: 2017/6/8 Time: 下午5:08
 */
@Data
public class BindCardPrepayResult implements Serializable {

  private static final long serialVersionUID = 682040845426124534L;

  /**
   * 渠道绑卡id，渠道返回该id后，后续验证码才能正常发送到对应的渠道做扣款校验
   */
  private String targetId;

  /**
   * 需要补全的属性
   */
  private List<String> completeProperties;
}
