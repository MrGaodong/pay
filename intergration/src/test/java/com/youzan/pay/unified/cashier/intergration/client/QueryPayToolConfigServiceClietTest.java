/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.account.api.UserMerchantService;
import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.customer.api.PayToolsService;
import com.youzan.pay.customer.api.request.QueryBaseWithBuyerRequest;
import com.youzan.pay.customer.api.request.QueryConfigRequest;
import com.youzan.pay.customer.api.result.ConfigInfo;
import com.youzan.pay.customer.api.result.PayToolConfig;
import com.youzan.pay.customer.api.result.Response;
import com.youzan.pay.unified.cashier.intergration.BaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * @author wulonghui
 * @version QueryPayToolConfigServiceClietTest.java, v 0.1 2017-08-02 10:34
 */
public class QueryPayToolConfigServiceClietTest extends BaseTest{

  @InjectMocks
  @Resource
  private QueryPayToolConfigServiceClient queryPayToolConfigServiceClient;

  @Mock
  private PayToolsService payToolsService;

  @Mock
  private UserMerchantService userMerchantService;

  @Test
  public void queryConfigTest(){
    QueryConfigRequest queryConfigRequest=new QueryConfigRequest();
    queryConfigRequest.setMchId("123456");
    queryConfigRequest.setPartnerId("1434234234242");
    queryConfigRequest.setBuyerId("343434");
    queryConfigRequest.setAppId(null);

    UserMerchantDto userMerchantDto=new UserMerchantDto();
    userMerchantDto.setUserId(123332);
    userMerchantDto.setMerchantId(1231313131);

    com.youzan.pay.core.api.model.response.ListResult<PayToolConfig> payToolConfigListResult=new ListResult<>();

    List<PayToolConfig> payToolConfigs=new ArrayList<>();
    PayToolConfig payToolConfig=new PayToolConfig();
    payToolConfig.setAvailable(true);
    payToolConfig.setVisible(true);
    payToolConfig.setPayTool("ECARD");
    payToolConfigs.add(payToolConfig);
    payToolConfigListResult.setData(payToolConfigs);

    UserMerchantSinleRequest userMerchantSinleRequest=new UserMerchantSinleRequest();

    QueryBaseWithBuyerRequest queryBaseWithBuyerRequest=new QueryBaseWithBuyerRequest();

    Mockito.when(userMerchantService.getMerchantIdSingleUserId(Mockito.anyObject())).thenReturn(userMerchantDto);

    Mockito.when(payToolsService.getAvailablePayToolList(Mockito.anyObject())).thenReturn(payToolConfigListResult);

    Response<ConfigInfo> response= queryPayToolConfigServiceClient.queryConfig(queryConfigRequest);

    Assert.assertEquals(1,response.getResult().getPayTools().size());
  }


}
