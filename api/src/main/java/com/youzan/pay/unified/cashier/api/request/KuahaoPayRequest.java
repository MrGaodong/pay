package com.youzan.pay.unified.cashier.api.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * Created by liumeng on 2017/7/12.
 */
@Data
public class KuahaoPayRequest implements Serializable {

    private static final long serialVersionUID = 3L;

    @NotNull(message = "收单流水号不能为空")
    private String acquireNo;

}
