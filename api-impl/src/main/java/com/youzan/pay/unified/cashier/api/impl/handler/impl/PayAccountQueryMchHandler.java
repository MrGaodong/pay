package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.service.PayAccountManager;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/20 Time: 下午3:57
 */
@Component
public class PayAccountQueryMchHandler extends AbstractHandler<Long, Long> {

  @Resource
  private PayAccountManager payAccountManager;

  @Override
  protected void validate(Long userId) {

    if (userId == null || userId <= 0) {
      throw new IllegalArgumentException("用户id非法");
    }
  }

  @Override
  protected Long execute(Long request) throws Exception {
    return payAccountManager.queryMchId(String.valueOf(request));
  }
}
