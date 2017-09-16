/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.request.GoodsInfoRequest;
import com.youzan.pay.unified.cashier.api.request.TradeInfoListRequest;
import com.youzan.pay.unified.cashier.api.request.TradeInfoRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.GoodsInfoResult;
import com.youzan.pay.unified.cashier.api.result.TradeInfoListResult;
import com.youzan.pay.unified.cashier.api.result.TradeInfoResult;

/**
 * @author wulonghui
 * @version TradeInfoCacheService.java, v 0.1 2017-06-20 00:02
 */
public interface TradeInfoCacheService {
  Response<TradeInfoResult> insertTradeInfo(TradeInfoRequest tradeInfoRequest);
  Response<TradeInfoListResult> getTradeInfo(TradeInfoListRequest tradeInfoListRequest);
  Response<GoodsInfoResult> getGoodsInfo(GoodsInfoRequest goodsInfoRequest);
}
