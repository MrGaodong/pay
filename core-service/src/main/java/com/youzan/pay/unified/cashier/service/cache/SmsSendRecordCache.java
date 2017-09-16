package com.youzan.pay.unified.cashier.service.cache;

import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 记录短信发送时间，缓存有效期为50s，存在记录则表明请求在50s内，不允许请求再次发送短信
 *
 * @author tao.ke Date: 2017/6/13 Time: 下午7:14
 */
@Slf4j
@Component
public class SmsSendRecordCache {

  /**
   * 发送短信缓存，key中包含用户id和绑卡id
   */
  private static final String SMS_SEND_RECORD_REDIS_KEY_TEMPLATE = "C_SSR_%s_%s";

  private static final int REDIS_CACHE_EXPIRE_SECONDS = 50;

  @Resource
  private RedisCacheManager redisCacheManager;

  /**
   * 保存用户上次发送验证码请求时间记录
   *
   * @param buyerId 用户id
   * @param orderId targetId支付单号
   */
  public boolean putSmsRecord(String buyerId, String orderId) {

    String key = String.format(SMS_SEND_RECORD_REDIS_KEY_TEMPLATE, buyerId, orderId);
    try {
      redisCacheManager.put(key, System.currentTimeMillis(), REDIS_CACHE_EXPIRE_SECONDS);
      return true;
    } catch (Exception e) {
      LogUtils.warn(e, log, "[保存用户发送短信记录异常]key:{}", key);
      return false;
    }
  }

  /**
   * 查询redis上是否有发送短信记录，如果存在则说明未到时间发送下一次短信
   *
   * @param buyerId 用户id
   * @param orderId targetId支付单号
   */
  public boolean checkNextSendSms(String buyerId, String orderId) {

    String key = String.format(SMS_SEND_RECORD_REDIS_KEY_TEMPLATE, buyerId, orderId);
    try {

      String value = redisCacheManager.get(key, String.class);
      if (StringUtils.isEmpty(value)) {
        return true;
      } else {
        LogUtils.info(log, "[从Redis中获取用户发送短信记录]key:{},value:{}", key, value);
        return false;
      }

    } catch (Exception e) {
      LogUtils.warn(e, log, "[获取用户发送短信记录异常]key:{}", key);
      return true;
    }
  }

}
