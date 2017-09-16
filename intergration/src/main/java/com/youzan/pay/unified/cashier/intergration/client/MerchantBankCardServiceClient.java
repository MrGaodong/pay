package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.api.common.response.BaseResult;
import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.BankCardService;
import com.youzan.pay.customer.api.request.BindCardRequest;
import com.youzan.pay.customer.api.request.QueryBoundCardRequest;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;
import com.youzan.pay.unified.cashier.intergration.common.CashierHandleTemplate;
import com.youzan.pay.unified.cashier.intergration.common.CashierProcessCallBack;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 下午5:00
 */
@Slf4j
@Component
public class MerchantBankCardServiceClient {

  @Resource
  private BankCardService merchantBankCardService;

  /**
   * 从商户中心获取卡列表数据
   */
  public List<BoundBankCardResult> queryCardList(QueryBoundCardRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<List<BoundBankCardResult>>() {
      @Override
      public List<BoundBankCardResult> process() {

        ListResult<BoundBankCardResult>
            response = merchantBankCardService.queryBoundQuickPayCard(request);

        if (response == null) {
          throw new CardBaseException(CardErrorCode.MERCHANT_CARD_LIST, "");
        }

        if (response.isSuccess()) {
          return response.getData();
        } else {
          //throw new CardBaseException(CardErrorCode.MERCHANT_CARD_LIST, response.getMessage());
          LogUtils.warn(log, "调用商户中心获取卡列表错误，请求参数:{}", request);
          return new ArrayList<>();
        }
      }

      @Override
      public void succ(List<BoundBankCardResult> result, long execTime) {
        LogUtils.info(log, "调用商户中心获取卡列表成功，cost:{},result:{},request:{}", execTime, result, request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用商户中心获取卡列表失败，请求参数:{}", request);
        throw new CardBaseException(CardErrorCode.MERCHANT_CARD_LIST, "");
      }
    });
  }


  /**
   * 调用商户中心解绑卡
   */
  public Boolean unbindCard(QueryBoundCardRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<Boolean>() {
      @Override
      public Boolean process() {

        BaseResult response = merchantBankCardService.unbindQuickPayCard(request);

        if (response == null) {
          throw new CardBaseException(CardErrorCode.MERCHANT_CARD_UNBIND, "");
        }

        if (response.isSuccess()) {
          return true;
        } else {
          LogUtils.warn(log, "调用商户中心解绑卡错误，请求参数:{}", request);
          throw new CardBaseException(CardErrorCode.MERCHANT_CARD_UNBIND, response.getMessage());
        }

      }

      @Override
      public void succ(Boolean result, long execTime) {
        LogUtils.info(log, "调用商户中心解绑卡成功，cost:{},result:{},request:{}", execTime, result, request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用商户中心解绑卡失败，请求参数:{}", request);
        throw new CardBaseException(CardErrorCode.MERCHANT_CARD_UNBIND, "");
      }
    });

  }

  /**
   * 绑定银行卡到商户平台
   */
  public BoundBankCardResult bindBankCard(BindCardRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<BoundBankCardResult>() {
      @Override
      public BoundBankCardResult process() {

        DataResult<BoundBankCardResult> result = merchantBankCardService.bindQuackPayCard(request);

        if (result == null) {
          throw new CardBaseException(CardErrorCode.MERCHANT_CARD_BIND, "");
        }

        if (result.isSuccess()) {
          return result.getData();
        } else {
          LogUtils.warn(log, "调用商户中心绑卡错误，请求参数:{},result={}", JSON.toJSONString(request), result);
          throw new CardBaseException(CardErrorCode.MERCHANT_CARD_BIND, result.getMessage());
        }
      }

      @Override
      public void succ(BoundBankCardResult result, long execTime) {
        LogUtils.info(log, "调用商户中心绑卡成功，cost:{},result:{},request:{}", execTime, result, request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用商户中心绑卡失败，请求参数:{}", request);
        throw new CardBaseException(CardErrorCode.MERCHANT_CARD_BIND, "");
      }
    });

  }

}
