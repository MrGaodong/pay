/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.filter.impl;

import com.youzan.pay.unified.cashier.api.enums.PayEviormentEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.group.PayToolFilterRequest;

/**
 * @author wulonghui
 * @version WxPayEnvFilter.java, v 0.1 2017-06-15 14:18
 */
public class WxPayEnvFilter implements Filter<PayToolFilterRequest>{

  @Override
  public boolean doFilter(PayToolFilterRequest request) {
    if(request.getPayEnviorment().equals(PayEviormentEnum.WX_JS.name())){
      if(request.getPayChannel().equals(PayToolTypeEnum.ALIPAY_WAP.name())||
          request.getPayChannel().equals(PayToolTypeEnum.WX_H5.name())){
        return true;
      }
    }
    return false;
  }
}
