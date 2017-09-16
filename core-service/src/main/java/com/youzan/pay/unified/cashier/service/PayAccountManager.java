package com.youzan.pay.unified.cashier.service;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/20 Time: 下午3:49
 */
@Component
@Slf4j
public class PayAccountManager {

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;

  /**
   * 查询用户支付id
   */
  public Long queryMchId(String buyerId) {

    // 替换真实用户id，如果有buyerId的话
    if (StringUtils.isNotBlank(buyerId)) {
      UserMerchantSinleRequest sinleRequest = new UserMerchantSinleRequest(Long.valueOf(buyerId));
      UserMerchantDto dto = userMerchantServiceClient.queryMchByUserId(sinleRequest);
      if (dto != null) {
        return dto.getMerchantId();
      }
    }

    // 默认返回0L
    return 0L;
  }
}
