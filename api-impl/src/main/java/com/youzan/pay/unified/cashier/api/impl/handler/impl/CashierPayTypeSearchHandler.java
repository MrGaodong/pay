/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.QueryConfigRequest;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.api.enums.PayEviormentEnum;
import com.youzan.pay.unified.cashier.api.impl.enums.PayToolTypeEnum;
import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.CashierPayTypeSearchRequest;
import com.youzan.pay.unified.cashier.api.request.PayChannel;
import com.youzan.pay.unified.cashier.api.result.CashierPayTypeSearchResult;
import com.youzan.pay.unified.cashier.core.utils.SignCheckUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CreateOrderErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.QueryPayToolConfigServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;

import com.alibaba.dubbo.common.utils.Assert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 查询支付方式处理类
 *
 * @author wulonghui
 * @version CashierPayTypeSearchHandler.java, v 0.1 2017-04-12 15:26
 */
@Slf4j
@Component
public class CashierPayTypeSearchHandler
    extends AbstractHandler<CashierPayTypeSearchRequest, CashierPayTypeSearchResult> {

  @Resource
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  @Override
  protected CashierPayTypeSearchResult execute(CashierPayTypeSearchRequest request)
      throws Exception {

    LogUtils.info(log, "[查询商户支持支付方式handler开始],request:{}", request);

    /**
     * 1.验证签名
     */
    checkSign(request);

    /**
     * 2.获得合作方支付方式集合
     */
    List<PayToolConfig> payToolConfigs = getPayToolConfigs(request);

    LogUtils.info(log, "[支付工具集合]:{}", payToolConfigs);

    /**
     * 3.排除不需要的支付方式
     */
    checkPayEnvironmentAndRemove(payToolConfigs, request);

    /**
     * 4.最终支付方式集合
     */
    return buildCashierPayTypeSearchResult(payToolConfigs);

  }

  private CashierPayTypeSearchResult buildCashierPayTypeSearchResult(
      List<PayToolConfig> payToolConfigs) {
    CashierPayTypeSearchResult result = new CashierPayTypeSearchResult();
    genCashierPayTypeSearchResult(payToolConfigs, result);
    return result;
  }

  /**
   * 获得商户支持支付方式
   */
  private List<PayToolConfig> getPayToolConfigs(CashierPayTypeSearchRequest request) {
    QueryConfigRequest queryConfigRequest = new QueryConfigRequest();
    genMerPayTypeSearchRequest(request, queryConfigRequest);
    Response<ConfigInfo> payToolConfigResponse = new Response<ConfigInfo>();
    payToolConfigResponse = queryPayToolConfigServiceClient.queryConfig(queryConfigRequest);
    if (payToolConfigResponse.getResult() == null) {
      LogUtils.warn(log, "[根据partnerId没有查到对应的支付方式]");
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

    if (payToolConfigResponse.getResult() != null
        && payToolConfigResponse.getResult().getPayTools() != null) {
      LogUtils.info(log, "[解析结果数据结束，payToolConfigs]:{}",
                    payToolConfigResponse.getResult().getPayTools());
      return payToolConfigResponse.getResult().getPayTools();
    } else {
      LogUtils.warn(log, "[没有查询到对应的支付方式，payToolConfig]:{}",
                    payToolConfigResponse.getResult().getPayTools());
      throw new BaseException(CreateOrderErrorCode.SEARCHPAYTOOLSFAIL);
    }

  }

  /**
   * 验证签名
   */
  private void checkSign(CashierPayTypeSearchRequest request) throws Exception {
    Assert.notNull(request, "CashierPayTypeSearchRequest can not be null");

    /**
     * 生成md5签名
     */
    String md5Sign = buildMd5Sign(request);
    if (!StringUtils.equals(md5Sign, request.getSign())) {
      LogUtils.warn(log, "[签名认证失败],outBizNo:{}", request.getOutBizNo());
      throw new BaseException(CreateOrderErrorCode.SIGNERROR);
    }

  }

  private String buildMd5Sign(CashierPayTypeSearchRequest request) throws Exception {
    /**
     * 生成待签名字符串
     */
    return buildSignStr(request);
  }

  private String buildSignStr(CashierPayTypeSearchRequest request) throws Exception {
    QuerySignKeyRequest querySignKeyRequest = buildQuerySignKeyRequest(request);
    String key = signKeyServiceClient.getSignKey(querySignKeyRequest);
    return SignCheckUtils.buildMd5Sign(request, key);
  }

  private QuerySignKeyRequest buildQuerySignKeyRequest(CashierPayTypeSearchRequest request) {
    QuerySignKeyRequest querySignKeyRequest = new QuerySignKeyRequest();
    querySignKeyRequest.setPartnerId(Long.parseLong(request.getPartnerId()));
    return querySignKeyRequest;
  }

  private void genMerPayTypeSearchRequest(CashierPayTypeSearchRequest cashierPayTypeSearchRequest,
                                          QueryConfigRequest queryConfigRequest) {

    queryConfigRequest.setPartnerId(cashierPayTypeSearchRequest.getPartnerId());

    queryConfigRequest.setMchId(cashierPayTypeSearchRequest.getMchId());

  }

  private void genCashierPayTypeSearchResult(List<PayToolConfig> payToolConfigs,
                                             CashierPayTypeSearchResult cashierPayTypeSearchResult) {
    List<PayChannel> payChannels = new LinkedList<>();

    for (PayToolConfig payToolConfig : payToolConfigs) {
      PayChannel payChannel = new PayChannel();
      payChannel.setPayChannel(payToolConfig.getPayTool());
      payChannel.setVisible(payToolConfig.isVisible());
      payChannel.setAvailable(payToolConfig.isAvailable());
      payChannel.setVisible_desc(payToolConfig.getVisibleDesc());
      payChannel.setAvailable_desc(payToolConfig.getAvailableDesc());
      payChannel.setPayChannelName(PayToolTypeEnum.getChannelName(payToolConfig.getPayTool()));
      payChannels.add(payChannel);
    }

    cashierPayTypeSearchResult.setPayChannels(payChannels);

  }

  private void checkPayEnvironmentAndRemove(List<PayToolConfig> payToolConfigs,
                                            CashierPayTypeSearchRequest request) {
    String env = request.getPayEnviorment();
    payToolConfigs.removeIf(payToolConfig -> isWxJsPayTool(env, payToolConfig));
  }

  private boolean isWxJsPayTool(String env, PayToolConfig payToolConfig) {
    return PayToolTypeEnum.WX_JS.name().equals(payToolConfig.getPayTool())
           && !PayEviormentEnum.WX_JS.getCode().equals(env);
  }
}
