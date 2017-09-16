/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.filter.impl;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.enums.PayEviormentEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.impl.filter.PayFilter;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.CashierH5SearchPayToolsHandler;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.group.PayToolFilterRequest;

/**
 * @author wulonghui
 * @version AliPayEnvFilter.java, v 0.1 2017-06-15 14:44
 */
public class AliPayEnvFilter implements Filter<PayToolFilterRequest> {

  @Override
  public boolean doFilter(PayToolFilterRequest request) {
    if(request.getPayEnviorment().equals(PayEviormentEnum.ALIPAYWAP.name())){
      if(request.getPayChannel().equals(PayToolTypeEnum.WX_JS.name())
      ||request.getPayChannel().equals(PayToolTypeEnum.WX_H5.name())
          ){
        return true;
      }
    }
    return false;
  }
}
