/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.assetcenter.api.RechargeService;
import com.youzan.pay.assetcenter.api.request.RechargeOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.request.RechargePayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.RechargeOrderCreatingResult;
import com.youzan.pay.assetcenter.api.result.RechargePayResult;
import com.youzan.pay.core.utils.log.LogUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wulonghui
 * @version CashierRechargeServiceClient.java, v 0.1 2017-06-18 11:37
 */
@Component
@Slf4j
public class CashierRechargeServiceClient {
  @Resource
  private RechargeService rechargeService;

  public Response<RechargeOrderCreatingResult> create(
      RechargeOrderCreatingRequest rechargeOrderCreatingRequest) {

    Response<RechargeOrderCreatingResult> response = new Response<>();

    try {
      response = rechargeService.create(rechargeOrderCreatingRequest);
      LogUtils.info(log, "[收单服务返回结果response]:{}", response);
    } catch (Exception e) {
      LogUtils.error(log, "[调用收单服务失败]:{}", e);
      throw new RuntimeException("调用收单服务失败");
    }

    if (!response.isSuccess() || response.getResult() == null) {
      LogUtils.error(log, "[收单服务返回结果为空]");
      throw new RuntimeException("[收单服务返回结果为空]");
    }

    return response;
  }

  public Response<RechargePayResult> pay(RechargePayRequest rechargeRequest){
    Response<RechargePayResult> rechargeResultBaseResponse=new Response<>();
    try{
      rechargeResultBaseResponse=rechargeService.rechargePay(rechargeRequest);
    }catch (Exception e){
      LogUtils.error(log, "[调用充值支付服务失败]:{}", e);
      throw new RuntimeException("调用充值支付服务失败");
    }
    if(!rechargeResultBaseResponse.isSuccess()||rechargeResultBaseResponse.getResult()==null){
      LogUtils.error(log, "[调用充值支付服务返回结果为空],response={}", rechargeResultBaseResponse);
      throw new RuntimeException("[调用充值支付服务返回结果为空]");
    }
    return rechargeResultBaseResponse;
  }
}
