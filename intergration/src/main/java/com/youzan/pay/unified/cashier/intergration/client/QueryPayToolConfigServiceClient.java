/**
 * Youzan.com Inc. Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.account.api.UserMerchantService;
import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.PayToolsService;
import com.youzan.pay.customer.api.request.QueryBaseWithBuyerRequest;
import com.youzan.pay.customer.api.request.QueryConfigRequest;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取支付方式列表具体实现
 *
 * @author wulonghui
 * @version QueryPayToolConfigServiceClientImpl.java, v 0.1 2017-02-19 17:44
 */
@Component
@Slf4j
public class QueryPayToolConfigServiceClient {

  @Resource
  private PayToolsService payToolsService;

  @Resource
  private UserMerchantService userMerchantService;

  @Resource
  private RedisCacheManager redisCacheManager;

  public Response<ConfigInfo> queryConfig(QueryConfigRequest queryConfigRequest) {

    LogUtils.info(log, "[查询商户平台支付方式request]:{}", queryConfigRequest);

    Response<ConfigInfo> result=null;

    String buyerUserNo = getUserNo(queryConfigRequest);


    try {
      QueryBaseWithBuyerRequest
          queryBaseWithBuyerRequest =
          getQueryBaseWithBuyerRequest(queryConfigRequest, buyerUserNo);
      LogUtils.info(log, "[从商户平台查询支付方式request]:{}", queryBaseWithBuyerRequest);
      ListResult<PayToolConfig>
          listResult =
          payToolsService.getAvailablePayToolList(queryBaseWithBuyerRequest);
      LogUtils.info(log, "[从商户平台查询支付方式结果]：{}", listResult);
      result = buildResult(listResult);
    } catch (Exception e) {
      throw new RuntimeException("merchantConfigService调用失败", e);
    }

    if (result == null || !result.isSuccess() || result.getResult() == null
        || result.getResult().getPayTools().isEmpty()) {
      throw new RuntimeException("支付方式查询结果为空,req=" + queryConfigRequest);
    }

    return result;

  }

  private String getUserNo(QueryConfigRequest queryConfigRequest) {
    if(queryConfigRequest.getBuyerId()==null){
      return "-1";
    }
    if (queryConfigRequest.getBuyerId().isEmpty() || !isNumeric(queryConfigRequest.getBuyerId())) {
      LogUtils.warn(log, "[buyerId为空或者不是数字格式]");
      return "-1";
    }
    try {
      UserMerchantSinleRequest userMerchantSinleRequest = new UserMerchantSinleRequest();
      userMerchantSinleRequest.setUserId(Long.valueOf(queryConfigRequest.getBuyerId()));

      //1.设置缓存key
      String key="userId_cUserNo_"+queryConfigRequest.getBuyerId();
      //2.根据key查询客户号
      UserMerchantDto userMerchantDto=redisCacheManager.get(key,UserMerchantDto.class);

      //3.如果查不到就调用账号服务查询客户号，然后加入缓存
      if(userMerchantDto==null) {
        userMerchantDto = userMerchantService.getMerchantIdSingleUserId(userMerchantSinleRequest);
        if (userMerchantDto == null) {
          LogUtils
              .warn(log, "[根据buyerId无法查到对应客户号,buyerId]:{}", userMerchantSinleRequest.getUserId());
          return "-1";
        }
        redisCacheManager.put("userId_cUserNo_"+queryConfigRequest.getBuyerId(),userMerchantDto);
      }

      return String.valueOf(userMerchantDto.getMerchantId());
    }catch (Exception e){
      LogUtils.warn(e,log,"[userMerchantService调用失败,userId={}]",queryConfigRequest.getBuyerId());
      return "-1";
    }
  }

  private Response<ConfigInfo> buildResult(ListResult<PayToolConfig> listResult) {
    Assert.notNull(listResult, "listResult不能为空");
    Assert.notNull(listResult.getData(), "listResult.getData()不能为空");
    List<PayToolConfig> payToolConfigs = listResult.getData();
    Response<ConfigInfo> response = buildResponse(payToolConfigs);
    return response;
  }

  private Response<ConfigInfo> buildResponse(List<PayToolConfig> payToolConfigs) {
    Response<ConfigInfo> response = new Response<>();
    ConfigInfo configInfo = new ConfigInfo();
    configInfo.setPayTools(payToolConfigs);
    response.setMsg("处理成功");
    response.setResultCode("0");
    response.setSuccess(true);
    response.setResult(configInfo);
    return response;
  }


  private QueryBaseWithBuyerRequest getQueryBaseWithBuyerRequest(
      QueryConfigRequest queryConfigRequest, String buyerUserNo) {
    QueryBaseWithBuyerRequest queryBaseWithBuyerRequest = new QueryBaseWithBuyerRequest();
    queryBaseWithBuyerRequest.setPartnerId(Long.valueOf(queryConfigRequest.getPartnerId()));
    queryBaseWithBuyerRequest.setUserNo(Long.valueOf(queryConfigRequest.getMchId()));
    queryBaseWithBuyerRequest.setBuyerUserNo(Long.valueOf(buyerUserNo));
    return queryBaseWithBuyerRequest;
  }

  public boolean isNumeric(String str) {
    if(str.isEmpty()){
      return false;
    }
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

}
