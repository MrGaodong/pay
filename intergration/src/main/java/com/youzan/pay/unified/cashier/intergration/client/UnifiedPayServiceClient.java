package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.assetcenter.api.UnifiedOrderQueryService;
import com.youzan.pay.assetcenter.api.UnifiedPayService;
import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.request.SinglePayRequest;
import com.youzan.pay.assetcenter.api.request.UnifiedOrderQueryRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.SinglePayResult;
import com.youzan.pay.assetcenter.api.result.UnifiedOrderQueryResult;
import com.youzan.pay.core.utils.log.LogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: clong
 * @date: 2017-04-14
 */
@Component
public class UnifiedPayServiceClient {

  private static final Logger logger = LoggerFactory.getLogger(UnifiedPayServiceClient.class);

  @Resource
  private UnifiedPayService unifiedPayService;

  @Resource
  private UnifiedOrderQueryService unifiedOrderQueryService;

  public SinglePayResult singlePay(SinglePayRequest request) {
    LogUtils.info(logger, "unifiedPayService.singlePay request: {}", request);

    Response<SinglePayResult> response = null;
    try {
      response = unifiedPayService.singlePay(request);
    } catch (Exception e) {
      LogUtils.error(logger, "调用UnifiedPayService失败", e);
      throw new RuntimeException("调用UnifiedPayService失败");
    }

    if (!response.isSuccess() || response.getResult() == null) {
      LogUtils.error(logger, "[singlePay request is not success]");
      throw new RuntimeException("singlePay request is not success");
    }

    return response.getResult();
  }

  public Response<MultiPayResult> multiPay(MultiPayRequest request) {

    LogUtils.info(logger, "开始调用支付dubbo服务：request＝{}", request);

    Response<MultiPayResult> response = null;
    try {
      response = unifiedPayService.multiPay(request);
    } catch (Exception e) {
      LogUtils.info(logger, "调用支付dubbo服务失败：request＝{}", request);
      throw new RuntimeException(e.getMessage());
    }

    LogUtils.info(logger, "调用支付dubbo服务结束：response＝{}", response);

    return response;
  }

  public long getPayAmount(String acquireNo) {
    LogUtils.info(logger, "开始调用查询收单信息dubbo服务：request＝{}", acquireNo);
    long payAmount = -1;
    UnifiedOrderQueryRequest request = new UnifiedOrderQueryRequest();
    request.setAcquireNo(acquireNo);
    Response<UnifiedOrderQueryResult> response = unifiedOrderQueryService.queryById(request);
    if (response.isSuccess() == true) {
      UnifiedOrderQueryResult result = response.getResult();
      if (result != null) {
        payAmount = result.getPayAmount();
      }
    }
    LogUtils.info(logger, "调用查询收单信息dubbo服务结束：response＝{}", response);
    return payAmount;
  }

}
