package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.account.api.UserMerchantService;
import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;
import com.youzan.pay.unified.cashier.intergration.common.CashierHandleTemplate;
import com.youzan.pay.unified.cashier.intergration.common.CashierProcessCallBack;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/20 Time: 下午3:23
 */
@Component
@Slf4j
public class UserMerchantServiceClient {

  @Resource
  private UserMerchantService userMerchantService;

  /**
   * 查询单用户的对应支付id
   */
  public UserMerchantDto queryMchByUserId(UserMerchantSinleRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<UserMerchantDto>() {
      @Override
      public UserMerchantDto process() {
        UserMerchantDto dto = userMerchantService.getMerchantIdSingleUserId(request);
        LogUtils.info(log, "调用用户中心获取支付用户成功，result:{}", dto);
        return dto;
      }

      @Override
      public void succ(UserMerchantDto result, long execTime) {
        LogUtils.info(log, "调用用户中心获取支付用户成功，cost:{},result:{},request:{}", execTime, result,request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用用户中心获取支付用户失败，请求参数:{}", request);
        throw new BaseException(CardErrorCode.ACCOUNT_MERCHANT_USERNO);
      }
    });


  }

}
