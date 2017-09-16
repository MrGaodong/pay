/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.depository.impl;

import com.youzan.pay.core.utils.LogUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CommonErrorCode;
import com.youzan.pay.unified.cashier.dal.dao.PayStrategyDetailInfoDao;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;
import com.youzan.pay.unified.cashier.service.depository.PayStrategyDepositoryService;
import com.youzan.pay.unified.cashier.service.dto.PayStrategyDto;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 */
@Slf4j
@Component
public class PayStrategyDepositoryServiceImpl implements PayStrategyDepositoryService {

  @Resource
  private PayStrategyDetailInfoDao payStrategyDetailInfoDao;

  @Override
  public PayStrategyDto loadAll(String bizStrategy,String bizCode) {
    return convertToDto(payStrategyDetailInfoDao.selectByBizStrategyAndBizCode(bizStrategy,bizCode));
  }

  @Override
  public List<PayStrategyDo> loaPayAll(String bizStrategy) {
    return payStrategyDetailInfoDao.selectAllPayStrategy(bizStrategy);
  }

  private PayStrategyDto convertToDto(PayStrategyDo payStrategyDo) {
    PayStrategyDto payStrategyDto = new PayStrategyDto();
    addToDto(payStrategyDo, payStrategyDto);
    return payStrategyDto;
  }

  private void addToDto(PayStrategyDo payStrategyDo, PayStrategyDto payStrategyDto) {
    transform(payStrategyDo,payStrategyDto);
  }

  private PayStrategyDto transform(PayStrategyDo payStrategyDo,PayStrategyDto payStrategyDto) {
    try {
      BeanUtils.copyProperties(payStrategyDto, payStrategyDo);
    } catch (IllegalAccessException e) {
      LogUtils.warn(log, "［没有办法，转化出错了,payStrategyDo:{}］", payStrategyDo);
    } catch (InvocationTargetException e) {
      LogUtils.warn(log, "［没有办法，转化出错了,payStrategyDo:{}］", payStrategyDo);
    }
    return payStrategyDto;
  }


}
