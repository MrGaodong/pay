package com.youzan.pay.unified.cashier.api.impl.model.filter;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;

import java.util.List;

import lombok.Data;

/**
 * Created by xielina on 2017/8/2.
 */
@Data
public class PayToolDoRequest {

  private CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest;
  private PayToolConfig payToolConfig;
  private List<PayChannel> payChannelList;

  public PayToolDoRequest(CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest,
                            List<PayChannel> payChannelList) {
    this.payChannelList = payChannelList;
    this.cashierH5SearchPayToolsRequest = cashierH5SearchPayToolsRequest;
  }
}
