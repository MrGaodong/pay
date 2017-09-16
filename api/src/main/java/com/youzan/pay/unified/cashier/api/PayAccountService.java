package com.youzan.pay.unified.cashier.api;

import com.youzan.pay.unified.cashier.api.response.Response;

/**
 * @author tao.ke Date: 2017/6/20 Time: 下午3:53
 */
public interface PayAccountService {

  /**
   * 根据用户id获取支付内部账户id
   */
  Response<Long> queryMchIdByBuyerId(Long buyerId);

}
