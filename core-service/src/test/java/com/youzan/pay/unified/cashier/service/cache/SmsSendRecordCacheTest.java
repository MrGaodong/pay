package com.youzan.pay.unified.cashier.service.cache;

import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.service.BaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.annotation.Resource;

/**
 * @author tao.ke Date: 2017/6/26 Time: 下午3:35
 */
public class SmsSendRecordCacheTest extends BaseTest {

  @InjectMocks
  @Resource
  private SmsSendRecordCache smsSendRecordCache;

  @Mock
  private RedisCacheManager redisCacheManager;

  @Test
  public void testPutSmsRecord() throws Exception {

    String buyerId = "12345";
    String orderId = "54321";
    String key = String.format("C_SSR_%s_%s", buyerId, orderId);

    boolean ret = smsSendRecordCache.putSmsRecord(buyerId,orderId);
    Assert.assertTrue(ret);
  }

  @Test
  public void testPutSmsRecordEx() throws Exception {

    String buyerId = "12345";
    String orderId = "54321";
    Mockito.doThrow(CardBaseException.class);
    boolean ret = smsSendRecordCache.putSmsRecord(buyerId,orderId);
    Assert.assertFalse(ret);
  }

  @Test
  public void testCheckNextSendSms() throws Exception {

    String buyerId = "12345";
    String orderId = "54321";
    String key = String.format("C_SSR_%s_%s", buyerId, orderId);

    Mockito.when(redisCacheManager.get(key,String.class)).thenReturn("8888888");

    boolean ret = smsSendRecordCache.checkNextSendSms(buyerId,orderId);
    Assert.assertFalse(ret);
  }

  @Test
  public void testCheckNextSendSmsTrue() throws Exception {

    String buyerId = "12345";
    String orderId = "54321";
    String key = String.format("C_SSR_%s_%s", buyerId, orderId);

    Mockito.when(redisCacheManager.get(key,String.class)).thenReturn(null);

    boolean ret = smsSendRecordCache.checkNextSendSms(buyerId,orderId);
    Assert.assertTrue(ret);
  }

}