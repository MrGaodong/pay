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
 * @author tao.ke Date: 2017/6/26 Time: 下午3:54
 */
public class PayCardRecordCacheTest extends BaseTest {

  @InjectMocks
  @Resource
  private PayCardRecordCache payCardRecordCache;

  @Mock
  private RedisCacheManager redisCacheManager;

  @Test
  public void testPutBindId() throws Exception {

    String buyerId = "12345";
    String bindId = "54321";

    boolean ret = payCardRecordCache.putBindId(buyerId, bindId);
    Assert.assertTrue(ret);
  }

  @Test
  public void testPutBindIdEx() throws Exception {

    String buyerId = "12345";
    String bindId = "54321";
    Mockito.doThrow(CardBaseException.class);

    boolean ret = payCardRecordCache.putBindId(buyerId, bindId);
    Assert.assertFalse(ret);
  }

  @Test
  public void testGetBindId() throws Exception {

    String buyerId = "12345";
    String bindId = "54321";
    String key = payCardRecordCache.buildKey(buyerId, "0", "0");

    Mockito.when(redisCacheManager.get(key, String.class)).thenReturn(bindId);

    String ret = payCardRecordCache.getBindId(key);
    Assert.assertEquals(bindId, ret);
  }

  @Test
  public void testGetBindIdEx() throws Exception {

    String buyerId = "12345";
    String bindId = "54321";
    String key = payCardRecordCache.buildKey(buyerId, "0", "0");

    Mockito.doThrow(CardBaseException.class);

    String ret = payCardRecordCache.getBindId(key);
    Assert.assertNull(ret);
  }

  @Test
  public void testBuildKey() throws Exception {

    String buyerId = "12345";
    String customerType = "9";
    String customerId = "12389";
    String key = payCardRecordCache.buildKey(buyerId, customerId,customerType);
    Assert.assertEquals("C_PCR_12345",key);
  }

  @Test
  public void testBuildKeyByCId() throws Exception {

    String buyerId = "";
    String customerType = "9";
    String customerId = "12389";
    String key = payCardRecordCache.buildKey(buyerId, customerId,customerType);
    Assert.assertEquals("C_PCR_12389_9",key);
  }

}