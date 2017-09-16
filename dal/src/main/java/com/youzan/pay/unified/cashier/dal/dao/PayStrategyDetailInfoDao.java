/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.dal.dao;

import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wulonghui
 * @version PayStrategyDetailInfoDao.java, v 0.1 2017-07-22 16:41
 */
public interface PayStrategyDetailInfoDao {

  public PayStrategyDo selectByBizStrategyAndBizCode(@Param("bizStrategy") String bizStrategy,
                                       @Param("bizCode") String bizCode);
  public List<PayStrategyDo> selectAllPayStrategy(@Param("bizStrategy") String bizStrategy);
}
