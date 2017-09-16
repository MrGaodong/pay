package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.response.Response;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.KuahaoPayRequest;
import com.youzan.pay.unified.cashier.api.result.KuahaoPayResult;
import com.youzan.pay.unified.cashier.api.result.QRCodeInfoResult;
import com.youzan.pay.unified.cashier.core.utils.QRCodeGenerator;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.DispatcherPayErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.UnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.service.cache.CacheService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by liumeng on 2017/7/12.
 */
@Component
@Slf4j
public class KuahaoPayHandler extends AbstractHandler<KuahaoPayRequest, KuahaoPayResult> {


  /**
   * 查询收单返回扫码URL
   */
  private static final String KUAHAO_SAOMA_URL = "awake_pay_url";

  /**
   * 跨号扫码支付方式
   */
  private static final String KUAHAO_SAOMA_TOOL = "WX_NATIVE";

  /**
   * 跨号扫码base64URL前缀
   */
  private static final String KUAHAO_SAOMA_URL_PRE = "data:image/png;base64,";

    @Resource
    private CacheService cacheService;

    @Resource
    private UnifiedPayServiceClient unifiedPayServiceClient;

    @Override
    public KuahaoPayResult execute(KuahaoPayRequest request) throws Exception {

        final String acquireNo = request.getAcquireNo();
        LogUtils.info(log, "支付订单【{}】跨号扫码支付开始,payRequest={}", acquireNo,request);

        // redis 获取请求参数
        final String kuahaoKey = new StringBuffer("kuahao").append(request.getAcquireNo()).toString();
        MultiPayRequest multiPayRequest = cacheService.get(kuahaoKey, MultiPayRequest.class);
        if(null == multiPayRequest || null == multiPayRequest.getPayDetailInfos() || multiPayRequest.getPayDetailInfos().isEmpty()){
          throw new BaseException(DispatcherPayErrorCode.SYSTEM_ERROR);
        }
        multiPayRequest.getPayDetailInfos().get(0).setPayTool(KUAHAO_SAOMA_TOOL);

        // 收单 获取扫码URL
        Response<MultiPayResult> response = unifiedPayServiceClient.multiPay(multiPayRequest);
        KuahaoPayResult kuahaoPayResult = null;
      LogUtils.info(log, "支付订单【{}】收单 获取扫码URL，response={}", acquireNo, response);

        if (response.isSuccess()) {        // 唤起成功
            kuahaoPayResult = paySuccess(kuahaoPayResult, response, kuahaoKey, acquireNo);
        } else {                           // 唤起失败
            throw new BaseException(DispatcherPayErrorCode.SYSTEM_ERROR);
        }

        LogUtils.info(log, "支付订单【{}】跨号扫码支付结束，result={}", acquireNo, kuahaoPayResult);

        return kuahaoPayResult;
    }

    /**
     * 处理成功结果
     * @param kuahaoPayResult
     * @param response
     * @param acquireNo
     * @return
     */
    private KuahaoPayResult paySuccess(KuahaoPayResult kuahaoPayResult, Response<MultiPayResult> response,
                                 String kuahaoKey, String acquireNo) {
      kuahaoPayResult = new KuahaoPayResult();
      MultiPayResult multiPayResult = response.getResult();
      kuahaoPayResult.setQrCodeInfoResult(convert(multiPayResult, kuahaoKey, acquireNo));
        return  kuahaoPayResult;
    }

    /**
     * 转换结果
     * @param result
     * @param kuahaoKey
     * @param acquireNo
     * @return
     */
    private static QRCodeInfoResult convert(MultiPayResult result, String kuahaoKey, String acquireNo) {

        List<PayDetailResult> payDetailResults;

        if (result != null && CollectionUtils
                .isNotEmpty(payDetailResults = result.getPayDetailResult())) {

            PayDetailResult payDetailResult = payDetailResults.get(0);

            if (null == payDetailResult.getDeepLinkInfo() || null == payDetailResult.getDeepLinkInfo().get(KUAHAO_SAOMA_URL)){
                LogUtils.error(log, "支付订单【{}】调用扫码支付返回信息错误,返回结果={}", acquireNo, result);
                throw new BaseException(DispatcherPayErrorCode.PAY_RESULT_ERROR);
            }

            return buildResult(kuahaoKey, payDetailResult.getDeepLinkInfo().get(KUAHAO_SAOMA_URL).toString());
        } else {
            LogUtils.error(log, "支付订单【{}】调用扫码支付返回信息错误", acquireNo);
            throw new BaseException(DispatcherPayErrorCode.PAY_RESULT_ERROR);
        }
    }

    /**
     * 构建扫码二维码
     * @param key
     * @param url
     * @return
     */
    private static QRCodeInfoResult buildResult(String key,
                                         String url) {

        String urlOfBase64 = QRCodeGenerator.encode(url);

        QRCodeInfoResult result = new QRCodeInfoResult();

        result.setCodeId(key);
        result.setContent(KUAHAO_SAOMA_URL_PRE + urlOfBase64);
        result.setEncoding("BASE64");
        result.setCodeFormat("QR_CODE");
        result.setImageFormat("png");
        result.setWidth(300);
        result.setHeight(300);
        return result;
    }
}
