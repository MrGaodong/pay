package com.youzan.pay.unified.cashier.api.request.nsq;

import lombok.Data;

/**
 * Created by xielina on 2017/7/27.
 */

public interface RiskIpInfo {
   String getAcquireNo();

   String getOutBizNo();

   String getUserAgentIp();

   String getMchId();

}