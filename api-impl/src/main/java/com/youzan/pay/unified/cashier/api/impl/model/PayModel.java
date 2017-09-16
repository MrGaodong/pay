package com.youzan.pay.unified.cashier.api.impl.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xielina on 2017/7/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayModel {
    private long accountId; //商户号

    private String orderNumber; //订单号

    private String payNumber; //支付单号

    private String ip; //用户ip

    private String traceId;

    private String createdTime; //创建时间
}
