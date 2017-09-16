package com.youzan.pay.unified.cashier.service;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/26 Time: 下午2:29
 */
public class PayAccountManagerTest extends BaseTest {

  @InjectMocks
  @Resource
  private PayAccountManager payAccountManager;

  @Mock
  private UserMerchantServiceClient userMerchantServiceClient;

  @Test
  public void queryMchIdByUserId() throws Exception {

    UserMerchantDto dto = new UserMerchantDto(123L, 321L, "signkey");

    Mockito.when(userMerchantServiceClient.queryMchByUserId(Matchers.anyObject())).thenReturn(dto);

    long ret = payAccountManager.queryMchId("123");
    Assert.assertEquals(321L, ret);

  }

}