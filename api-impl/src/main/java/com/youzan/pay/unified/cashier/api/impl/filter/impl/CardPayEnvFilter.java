/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.filter.impl;

import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.request.group.PayToolFilterRequest;

/**
 * @author wulonghui
 * @version CardFPayFilter.java, v 0.1 2017-06-15 14:19
 */
public class CardPayEnvFilter implements Filter<PayToolFilterRequest>{

  @Override
  public boolean doFilter(PayToolFilterRequest request) {
    if(request.getPayChannel().equals(PayToolTypeEnum.BALANCE.name())){
      return true;
    }
    return false;
  }
}
