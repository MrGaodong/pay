package com.youzan.pay.unified.cashier.api.result;

import java.io.Serializable;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-08-30
 * Time: 下午5:53
 * <p>创建收银台请求链接返回结果</p>
 */
@Data
public class CreateUnifiedCashierUrlResult implements Serializable{

  private static final long serialVersionUID = -8499642741137477743L;

  /**
   * 掉漆收银台的url
   */
  private String url;
}