package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.result.CreateUnifiedCashierUrlResult;

import java.io.Serializable;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-09-03
 * Time: 下午3:04
 * <p></p>
 */

@Data
public class GetUnifiedCashierUrlRequest implements Serializable{

  private static final long serialVersionUID = 539728368482248873L;

  private String sign;
}