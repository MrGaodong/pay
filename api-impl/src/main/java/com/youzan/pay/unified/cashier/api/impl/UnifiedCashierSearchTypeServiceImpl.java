/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.UnifiedCashierSearchPayTypeService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.UnifiedCashierSearchPayTypeHandler;
import com.youzan.pay.unified.cashier.api.request.UnifiedSearchPayTypeRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.UnifiedSearchPayTypeResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * 查询支付方式实现类
 *
 * @author wulonghui
 * @version UnifiedCashierSearchTypeServiceImpl.java, v 0.1 2017-01-12 10:55
 */
@Slf4j
@Service(protocol = {"dubbo", "rest"})
@Path("/paytype")
public class UnifiedCashierSearchTypeServiceImpl implements UnifiedCashierSearchPayTypeService {

  /**
   * 处理器工厂
   */
  @Resource
  private HandlerFactory handlerFactory;

  @POST
  @Path("/search")
  public Response<UnifiedSearchPayTypeResult> search(UnifiedSearchPayTypeRequest request) {

    UnifiedCashierSearchPayTypeHandler handler = handlerFactory
        .getHandler(UnifiedCashierSearchPayTypeHandler.class);

    Response<UnifiedSearchPayTypeResult> result = handler.handle(request);

    LogUtils.info(log, "支付查询结果＝{}", result);

    return result;
  }
}
