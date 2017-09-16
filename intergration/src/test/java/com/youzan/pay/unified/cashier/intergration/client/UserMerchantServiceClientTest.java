package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.account.api.UserMerchantService;
import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.intergration.BaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author tao.ke Date: 2017/6/27 Time: 下午9:52
 */
public class UserMerchantServiceClientTest extends BaseTest {

  @InjectMocks
  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;

  @Mock
  private UserMerchantService userMerchantService;

  @Test
  public void queryMchByUserId() throws Exception {

    UserMerchantDto dto = new UserMerchantDto(100L, 151221211556360485L, "testdev");
    Mockito.when(userMerchantService.getMerchantIdSingleUserId(Matchers.anyObject())).thenReturn(dto);

    UserMerchantSinleRequest request = new UserMerchantSinleRequest(100L);
    UserMerchantDto dto1 = userMerchantServiceClient.queryMchByUserId(request);
    Assert.assertEquals(151221211556360485L,dto1.getMerchantId());

  }

  @Test(expected = CardBaseException.class)
  public void queryMchByUserIdEx() throws Exception {

    Mockito.when(userMerchantService.getMerchantIdSingleUserId(Matchers.anyObject())).thenThrow(
        CardBaseException.class);

    UserMerchantSinleRequest request = new UserMerchantSinleRequest(100L);
    UserMerchantDto dto1 = userMerchantServiceClient.queryMchByUserId(request);

  }


}