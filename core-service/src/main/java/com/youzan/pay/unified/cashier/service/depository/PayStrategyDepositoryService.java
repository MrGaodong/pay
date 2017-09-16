/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.depository;

import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.service.dto.PayStrategyDto;

import java.util.List;

/**
 * @author wulonghui
 */
public interface PayStrategyDepositoryService {
  public PayStrategyDto loadAll(String bizStrategy,String bizCode);
  public List<PayStrategyDo> loaPayAll(String bizStrategy);

}
