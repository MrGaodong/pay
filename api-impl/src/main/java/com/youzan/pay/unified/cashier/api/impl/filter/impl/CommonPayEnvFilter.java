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
 * @version CommonPayEnvFilter.java, v 0.1 2017-07-07 17:02
 */
public class CommonPayEnvFilter implements Filter<PayToolFilterRequest> {

  @Override
  public boolean doFilter(PayToolFilterRequest request) {
    if(request.getPayEnviorment().equals("")){
      if(request.getPayChannel().equals(PayToolTypeEnum.WX_JS.name())){
        return true;
      }
    }
    return false;
  }
}
