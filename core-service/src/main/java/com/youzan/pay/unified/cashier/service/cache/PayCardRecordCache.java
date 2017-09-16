package com.youzan.pay.unified.cashier.service.cache;

import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 记录用户最近一次使用的银行卡信息
 *
 * @author tao.ke Date: 2017/6/13 Time: 下午4:34
 */
@Slf4j
@Component
public class PayCardRecordCache {

    private static final String CARD_RECORD_REDIS_KEY_TEMPLATE = "C_PCR_%s";

    @Resource
    private RedisCacheManager redisCacheManager;

    /**
     * 保存用户最后一次支付卡数据到redis中
     *
     * @param key 用户id
     */
    public boolean putBindId(String key, String bindId) {

        try {
            redisCacheManager.put(key, bindId);
            return true;
        } catch (Exception e) {
            LogUtils.warn(e, log, "[保存用户最后支付银行卡数据异常]key:{},value:{}", key, bindId);
            return false;
        }
    }

    /**
     * 获取用户最近一次使用的支付卡数据，如果redis获取异常，则返回""
     */
    public String getBindId(String key) {

        try {
            if (StringUtils.isNotBlank(key)) {
                return redisCacheManager.get(key, String.class);
            }
        } catch (Exception e) {
            LogUtils.warn(e, log, "[获取用户最后支付银行卡数据异常]key:{}", key);
        }
        return null;
    }

    /**
     * 构建redis查询bindId的key
     */
    public String buildKey(String userNo, String customerId, String customerType) {

        if (StringUtils.isNotBlank(userNo)) {
            return String.format(CARD_RECORD_REDIS_KEY_TEMPLATE, userNo);
        } else if (StringUtils.isNotBlank(customerId) && !"0".equals(customerId)) {
            return String.format(CARD_RECORD_REDIS_KEY_TEMPLATE, customerId + "_" + customerType);
        }

        return null;
    }

}
