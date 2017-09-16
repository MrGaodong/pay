/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.filter;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**支付方式责任链基类
 * @author wulonghui
 * @version FilterChain.java, v 0.1 2017-06-15 11:39
 */
@Data
public   class FilterChain<T,R extends Filter> {

  private List<Filter> filters=new ArrayList<>();

  public FilterChain() {
  }

  public FilterChain(List<Filter> filters){
    this.filters=filters;

  }


  public  boolean handleRequest(T request){

    for(Filter filter:filters){


     boolean result= filter.doFilter(request);
      if(result){
        return result;
      }
    }
    return false;
  };
}
