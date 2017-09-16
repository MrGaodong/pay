package com.youzan.pay.unified.cashier.service.convert;

import com.youzan.account.api.UserMerchantService;
import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.PayToolsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConvertManager {
  private static final String USERIDTOCUSERNO= "userId_cUserNo_%s";

  @Resource
  private PayToolsService payToolsService;

  @Resource
  private UserMerchantService userMerchantService;

  @Resource
  private RedisCacheManager redisCacheManager;

  public String getUserNo(String buyerId) {

    if (StringUtils.isEmpty(buyerId) || !StringUtils.isNumeric(buyerId)) {
      LogUtils.warn(log, "[buyerId为空或者不是数字格式]");
      return "-1";
    }
    try {
      UserMerchantSinleRequest userMerchantSinleRequest = new UserMerchantSinleRequest();
      userMerchantSinleRequest.setUserId(Long.valueOf(buyerId));

      //1.设置缓存key
      String key=String.format(USERIDTOCUSERNO,buyerId);
      //2.根据key查询客户号
      UserMerchantDto userMerchantDto=redisCacheManager.get(key, UserMerchantDto.class);

      //3.如果查不到就调用账号服务查询客户号，然后加入缓存
      if(userMerchantDto==null) {
        userMerchantDto = userMerchantService.getMerchantIdSingleUserId(userMerchantSinleRequest);
        if (userMerchantDto == null) {
          LogUtils
              .warn(log, "[根据buyerId无法查到对应客户号,buyerId]:{}", userMerchantSinleRequest.getUserId());
          return "-1";
        }
        redisCacheManager.put(key,userMerchantDto);
      }

      return String.valueOf(userMerchantDto.getMerchantId());
    }catch (Exception e){
      LogUtils.warn(e,log,"[userMerchantService调用失败,userId={}]",buyerId);
      return "-1";
    }
  }

}
