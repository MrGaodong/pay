package com.youzan.pay.unified.cashier.api.impl.filter.dochain;

import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.unified.cashier.api.constants.CashierPayTypeConstants;
import com.youzan.pay.unified.cashier.api.impl.enums.AvailableDescEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.CurrencyEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.filter.Filter;
import com.youzan.pay.unified.cashier.api.impl.model.filter.PayToolDoRequest;
import com.youzan.pay.unified.cashier.api.request.CashierH5SearchPayToolsRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;


import lombok.Data;


/**
 * Created by xielina on 2017/8/15.
 */
@Data
public class CheckCommon implements Filter<PayToolDoRequest> {

  @Override
  public boolean doFilter(PayToolDoRequest request) {
    PayChannel
        payChannel =
        buildPayChannel(request.getPayToolConfig(), request.getCashierH5SearchPayToolsRequest());
    request.getPayChannelList().add(payChannel);
    return true;
  }

  private PayChannel buildPayChannel(PayToolConfig payToolConfig,
                                     CashierH5SearchPayToolsRequest cashierH5SearchPayToolsRequest) {
    PayChannel payChannel = new PayChannel();
    payChannel.setPayChannelName(PayToolTypeEnum.getChannelName(payToolConfig.getPayTool()));
    payChannel.setPayChannel(payToolConfig.getPayTool());
    payChannel.setVisible(payToolConfig.isVisible());
    payChannel.setVisible_desc(payToolConfig.getVisibleDesc());
    payChannel.setAvailable(payToolConfig.isAvailable());
    payChannel.setAvailable_desc(payToolConfig.getAvailableDesc());

    //设置支付余额
    payChannel.setPayAmount(
        String.format("%.2f", cashierH5SearchPayToolsRequest.getPayAmount() / 100.0));
    if (checkCardPassWord(payToolConfig)) {
      payChannel.setNeed_password(true);
      if (cashierH5SearchPayToolsRequest.getPayAmount() <= payToolConfig.getBalance()) {
        payChannel.setAvailable_desc(AvailableDescEnum.BALANCE.getDesc()
                                     + CurrencyEnum.RMB_YUAN.getDesc() + String
                                         .format("%.2f", payToolConfig.getBalance() / 100.0));
      } else if (cashierH5SearchPayToolsRequest.getPayAmount() > payToolConfig.getBalance()) {
        payChannel.setAvailable(false);
        payChannel.setAvailable_desc(AvailableDescEnum.BALANCE_INSUFFICIENT.getDesc()
                                     + CurrencyEnum.RMB_YUAN.getDesc()
                                     + String.format("%.2f", payToolConfig.getBalance() / 100.0));
      }
    }
    return payChannel;
  }

  private boolean checkCardPassWord(PayToolConfig payToolConfig) {
    boolean flag = false;
    switch (payToolConfig.getPayTool()) {
      case CashierPayTypeConstants.BALANCE:
        flag = true;
        break;
      case CashierPayTypeConstants.ECARD:
        flag = true;
        break;
      case CashierPayTypeConstants.GIFT_CARD:
        flag = true;
        break;
    }
    return flag;
  }
}
