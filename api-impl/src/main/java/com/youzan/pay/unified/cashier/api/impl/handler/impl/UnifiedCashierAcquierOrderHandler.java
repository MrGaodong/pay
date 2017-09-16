/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus;
import com.youzan.pay.assetcenter.api.request.UnifiedOrderCreatingRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderCreatingResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.unified.cashier.api.impl.constant.Loggers;
import com.youzan.pay.unified.cashier.api.impl.convertor.UnifiedCashierAcquireConvertor;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.core.model.domain.MchBizLogInfo;
import com.youzan.pay.unified.cashier.api.request.UnifiedAcquireOrderRequest;
import com.youzan.pay.unified.cashier.api.result.UnifiedAcquireOrderResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedOrderServiceClient;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import static com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus.BUYER_PAIED;
import static com.youzan.pay.assetcenter.api.enums.AcquireQueryStatus.SUCCESS;

/**
 * 微商城收银台具体业务逻辑执行类
 *
 * @author wulonghui
 * @version UnifiedCashierAcquierOrderHandler.java, v 0.1 2017-01-10 16:49
 */
@Component
public class UnifiedCashierAcquierOrderHandler
    extends AbstractHandler<UnifiedAcquireOrderRequest, UnifiedAcquireOrderResult> {

  //性能摘要日志
  protected Logger monitorLog = Loggers.MCH_LOGGER;

  @Resource
  private UnifiedOrderServiceClient unifiedOrderServiceClient;

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  protected UnifiedAcquireOrderResult execute(UnifiedAcquireOrderRequest request) {

    String key = null;

    QuerySignKeyRequest querySignKeyRequest = new QuerySignKeyRequest();

    querySignKeyRequest.setPartnerId(Long.valueOf(request.getPartnerId()));

    querySignKeyRequest.setUserNo(request.getMchId());

    try {
      key = signKeyServiceClient.getSignKey(querySignKeyRequest);
    } catch (Exception e) {
      throw new BaseException(CreateOrderErrorCode.KEYFAIL, e);
    }

    //todo mock wx_owner do not forget
    try {
      if (!SignCheckUtils.check(request, request.getSign(), key, null)) {
        throw new BaseException(CreateOrderErrorCode.SIGNERROR);
      }
    } catch (NoSuchMethodException e) {
      throw new BaseException(CreateOrderErrorCode.SYSTEM_ERROR, e);
    } catch (IllegalAccessException e) {
      throw new BaseException(CreateOrderErrorCode.SYSTEM_ERROR, e);
    } catch (InvocationTargetException e) {
      throw new BaseException(CreateOrderErrorCode.SYSTEM_ERROR, e);
    }

    UnifiedOrderCreatingRequest
        createOrderReq =
        UnifiedCashierAcquireConvertor.convertToRequest(request);

    Response<UnifiedOrderCreatingResult>
        unifiedOrderCreatingResult = doCreate(createOrderReq);

    UnifiedAcquireOrderResult unifiedAcquireOrderResult = new UnifiedAcquireOrderResult();

    unifiedAcquireOrderResult
        .setUnifiedAquireOrder(unifiedOrderCreatingResult.getResult().getAcquireNo());

    LogUtils.info(log, "[创建收单],结果={}", unifiedAcquireOrderResult);

    return unifiedAcquireOrderResult;
  }

  protected Response<UnifiedOrderCreatingResult> doCreate(UnifiedOrderCreatingRequest createOrder) {
    Response<UnifiedOrderCreatingResult> resp = null;
    try {
      //创建收单
      resp = unifiedOrderServiceClient.create(createOrder);

    } catch (Exception e) {
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL, e);
    } finally {
      //记录创建收单监控日志
      this.logMonitorAcquireOrder(createOrder, resp);
    }

    //结果检查
    if (resp == null || !resp.isSuccess() || resp.getResult() == null) {
      LogUtils.error(log, "［创建收单]结果失败:{}", resp);
      throw new BaseException(CreateOrderErrorCode.CREATEFAIL);
    }

    //检查收单状态： TODO 对于失败的订单呢？
    AcquireQueryStatus status = resp.getResult().getStatus();
    if (status != null && status.isIn(BUYER_PAIED,SUCCESS)) {
      LogUtils.warn(log, "［创建收单],收单状态不允许再支付：{}", status);
      throw new BaseException(CreateOrderErrorCode.PAYFINISH);
    }

    return resp;
  }

  //记录创建收单监控日志
  private void logMonitorAcquireOrder(UnifiedOrderCreatingRequest createOrder,
                                      Response<UnifiedOrderCreatingResult> resp) {
    if (resp != null && resp.getResult() != null) {
      MchBizLogInfo bizLogInfo = new MchBizLogInfo();
      bizLogInfo.setApi("CreateNewAcquireOrder");
      bizLogInfo.setPartnerId(createOrder.getPartnerId());
      bizLogInfo.setMchId(createOrder.getMchId());
      bizLogInfo.setPayMethod("");

      String result = resp.isSuccess() ? bizLogInfo.SUCCESS_RESULT : bizLogInfo.FAIL_RESULT;
      if (resp.isSuccess() && resp.getResult().isRepeated()) {
        result = bizLogInfo.IDEMPOTENT_RESULT;
      }
      bizLogInfo.setResult(result);

      monitorLog.info("{}", JSON.toJSONString(bizLogInfo));
    }
  }


}
