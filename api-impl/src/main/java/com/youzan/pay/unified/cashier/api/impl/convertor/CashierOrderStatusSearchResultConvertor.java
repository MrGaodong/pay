/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.convertor;

import com.youzan.pay.assetcenter.api.result.AssetPayDetailItem;
import com.youzan.pay.assetcenter.api.result.QueryAcquireResult;
import com.youzan.pay.unified.cashier.api.result.CashierOrderStatusSearchResult;
import com.youzan.platform.util.lang.StringUtil;

import com.alibaba.dubbo.common.utils.Assert;

import java.util.Collections;

/**
 * 支付结果查询转换类
 *
 * @author wulonghui
 * @version CashierOrderStatusSearchResultConvertor.java, v 0.1 2017-04-12 17:50
 */
public class CashierOrderStatusSearchResultConvertor {

  public static CashierOrderStatusSearchResult convert(QueryAcquireResult queryAcquireResult) {

    Assert.notNull(queryAcquireResult, "收单结果查询不能为空");
    CashierOrderStatusSearchResult
        cashierOrderStatusSearchResult =
        new CashierOrderStatusSearchResult();
    if (queryAcquireResult.getStatus() != null) {

      cashierOrderStatusSearchResult
          .setAcquireQueryStatus(queryAcquireResult.getStatus().getCode());

      cashierOrderStatusSearchResult.setAcquireNo(queryAcquireResult.getAcquireNo());
      if(queryAcquireResult.getAssetPayDetailItems()!=null&&!queryAcquireResult.getAssetPayDetailItems().isEmpty()){
        for(AssetPayDetailItem item : queryAcquireResult.getAssetPayDetailItems()){
          if(StringUtil.isNotEmpty(item.getPayChannelApi())){
            cashierOrderStatusSearchResult.setPayTool(getPayTool(item.getPayChannelApi()));
          }
        }
      }
    }

    return cashierOrderStatusSearchResult;
  }

  private static String getPayTool(String payChannelApi) {
    if (payChannelApi.equals("90010003")||payChannelApi.equals("90010001")) {
      return "WXPAY";
    } else if (payChannelApi.equals("90010002")) {
      return "ALIPAY";
    } else if (payChannelApi.equals("90040001")) {
      return "ECARD";
    } else if (payChannelApi.equals("90010005")) {
      return "WXNATIVE";
    } else if(payChannelApi.equals("9003")){
      return "PREPAID_PAY";
    } else if(payChannelApi.equals("90040004")){
      return "VALUE_CARD";
    }
    return "YZPAY";
  }
}
