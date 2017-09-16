package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.assetcenter.api.UnifiedPayService;
import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;
import com.youzan.pay.unified.cashier.intergration.common.CashierHandleTemplate;
import com.youzan.pay.unified.cashier.intergration.common.CashierProcessCallBack;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/14 Time: 下午1:52
 */
@Component
@Slf4j
public class AssetCenterUnifiedPayServiceClient {

  @Resource
  private UnifiedPayService unifiedPayService;

  /**
   * 收单中心的多次支付接口封装
   */
  public MultiPayResult multiPay(MultiPayRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<MultiPayResult>() {
      @Override
      public MultiPayResult process() {

        Response<MultiPayResult> response = unifiedPayService.multiPay(request);

        if (response == null) {
          throw new CardBaseException(CardErrorCode.ASSET_CENTER_ORDER_PAY, "");
        }

        if (response.isSuccess()) {
          return response.getResult();
        } else {
          LogUtils.warn(log, "调用收单发起支付错误，result:{}", response);
          throw new CardBaseException(CardErrorCode.ASSET_CENTER_ORDER_PAY, response.getMsg());
        }

      }

      @Override
      public void succ(MultiPayResult result, long execTime) {
        LogUtils.info(log, "调用收单发起支付成功，cost:{},result:{}", execTime, result);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用收单发起支付失败");
        throw new CardBaseException(CardErrorCode.ASSET_CENTER_ORDER_PAY, "");
      }
    });

  }
}
