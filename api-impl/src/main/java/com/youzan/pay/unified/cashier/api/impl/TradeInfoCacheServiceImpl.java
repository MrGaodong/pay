/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.TradeInfoCacheService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.GoodsInfoHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.TradeInfoCacheHandler;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.TradeInfoCacheSelectHandler;
import com.youzan.pay.unified.cashier.api.request.GoodsInfoRequest;
import com.youzan.pay.unified.cashier.api.request.TradeInfoListRequest;
import com.youzan.pay.unified.cashier.api.request.TradeInfoRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.GoodsInfoResult;
import com.youzan.pay.unified.cashier.api.result.TradeInfoListResult;
import com.youzan.pay.unified.cashier.api.result.TradeInfoResult;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version TradeInfoCacheServiceImpl.java, v 0.1 2017-06-20 00:01
 */
@Slf4j
@Path("/cache")
@Service(protocol = {"dubbo", "rest"},registry = {"haunt"})
public class TradeInfoCacheServiceImpl implements TradeInfoCacheService{

  @Resource
  private HandlerFactory handlerFactory;

  @Override
  @POST
  @Path("insert/tradeInfo")
  public Response<TradeInfoResult> insertTradeInfo(TradeInfoRequest tradeInfoRequest) {

    LogUtils.info(log, "[插入支付商品数据集合到redis],request:{}", tradeInfoRequest);

    TradeInfoCacheHandler tradeInfoCacheHandler=handlerFactory.getHandler(TradeInfoCacheHandler.class);

    return tradeInfoCacheHandler.handle(tradeInfoRequest);

  }

  @Override
  @POST
  @Path("get/tradeInfo")
  public Response<TradeInfoListResult> getTradeInfo(TradeInfoListRequest tradeInfoListRequest) {
    LogUtils.info(log, "[redis查询商品信息列表],request:{}", tradeInfoListRequest);

    TradeInfoCacheSelectHandler handler=handlerFactory.getHandler(TradeInfoCacheSelectHandler.class);

    return handler.handle(tradeInfoListRequest);
  }


  @Override
  @POST
  @Path("get/cashierInfo")
  public Response<GoodsInfoResult> getGoodsInfo(GoodsInfoRequest goodsInfoRequest) {
    LogUtils.info(log,"[收银台商品信息列表]:{}",goodsInfoRequest);
    GoodsInfoHandler handler=handlerFactory.getHandler(GoodsInfoHandler.class);
    return handler.handle(goodsInfoRequest);
  }

}
