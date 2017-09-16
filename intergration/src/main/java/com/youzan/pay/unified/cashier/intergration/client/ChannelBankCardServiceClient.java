package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.fundchannel.open.api.BankCardService;
import com.youzan.pay.fundchannel.open.model.BaseResponse;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmResult;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsResult;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;
import com.youzan.pay.unified.cashier.intergration.common.CashierHandleTemplate;
import com.youzan.pay.unified.cashier.intergration.common.CashierProcessCallBack;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import static com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult.Result;

/**
 * @author tao.ke Date: 2017/6/12 Time: 上午10:04
 */
@Slf4j
@Component
public class ChannelBankCardServiceClient {

  @Resource
  private BankCardService bankCardService; // 渠道对外开放的银行卡服务

  /**
   * 调用银行卡校验接口，获取银行卡基本信息
   */
  public BankCardCheckResult checkBankCard(BankCardCheckRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<BankCardCheckResult>() {
      @Override
      public BankCardCheckResult process() {

        BaseResponse<BankCardCheckResult> response = bankCardService.checkCard(request);

        if (response == null) {
          throw new CardBaseException(CardErrorCode.CHANNEL_CARD_CHECK, "");
        }
        if (response.isSuccess() && response.getBizResult() != null) {
          return response.getBizResult();
        } else {
          LogUtils.warn(log,"调用渠道获取银行卡校验信息返回,response:{}",response);
          throw new CardBaseException(CardErrorCode.CHANNEL_CARD_CHECK, response.getErrorMsg());
        }
      }

      @Override
      public void succ(BankCardCheckResult result, long execTime) {
        LogUtils.info(log, "调用渠道获取银行卡校验信息成功，cost:{},result:{}", execTime, result);
      }


      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用渠道获取银行卡校验信息失败.");
        throw new CardBaseException(CardErrorCode.CHANNEL_CARD_CHECK, "");
      }

    });

  }

  /**
   * 请求渠道发送短信验证码
   */
  public SendSmsResult sendConfirmSms(SendSmsRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<SendSmsResult>() {
      @Override
      public SendSmsResult process() {

        BaseResponse<SendSmsResult> response = bankCardService.sendSms(request);

        if (response == null) {

          throw new CardBaseException(CardErrorCode.CHANNEL_SEND_SMS, "");
        }

        if (response.isSuccess() && response.getBizResult() != null) {
          return response.getBizResult();
        } else {
          throw new CardBaseException(CardErrorCode.CHANNEL_SEND_SMS, response.getErrorMsg());
        }

      }

      @Override
      public void succ(SendSmsResult result, long execTime) {
        LogUtils.info(log, "调用渠道请求发送短信验证码成功，cost:{},result:{},request:{}", execTime, result,request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用渠道请求发送短信验证码失败，请求参数:{}", request);
        throw new CardBaseException(CardErrorCode.CHANNEL_SEND_SMS, "");
      }

    });

  }

  /**
   * 完成最后支付确认过程的请求
   */
  public BankPayConfirmResult payConfirm(BankPayConfirmRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<BankPayConfirmResult>() {
      @Override
      public BankPayConfirmResult process() {

        BaseResponse<BankPayConfirmResult> response = bankCardService.payConfirm(request);

        if (response == null) {
          throw new CardBaseException(CardErrorCode.CHANNEL_CONFIRM_PAY, "");
        }

        if (response.isSuccess() && response.getBizResult() != null) {
          return response.getBizResult();
        } else {
          throw new CardBaseException(CardErrorCode.CHANNEL_CONFIRM_PAY, response.getErrorMsg());
        }
      }

      @Override
      public void succ(BankPayConfirmResult result, long execTime) {
        LogUtils.info(log, "调用渠道完成确认支付成功，cost:{},result:{},request:{}", execTime, result,request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用渠道完成确认支付失败，请求参数:{}", request);
        throw new CardBaseException(CardErrorCode.CHANNEL_CONFIRM_PAY, "");
      }

    });
  }

  /**
   * 校验绑卡id的合法性
   */
  public List<Result> checkBindIds(BankBindCheckRequest request) {

    return CashierHandleTemplate.execute(new CashierProcessCallBack<List<Result>>() {
      @Override
      public List<Result> process() {

        BaseResponse<BankBindCheckResult> checkResult = bankCardService.checkBankBinding(request);

        if (checkResult == null) {
          throw new CardBaseException(CardErrorCode.CHANNEL_CHECK_BIND, "");
        }

        if (checkResult.isSuccess() && checkResult.getBizResult() != null
            && checkResult.getBizResult().getResult() != null) {
          return checkResult.getBizResult().getResult();
        } else {
          throw new CardBaseException(CardErrorCode.CHANNEL_CHECK_BIND, checkResult.getErrorMsg());
        }
      }

      @Override
      public void succ(List<Result> result, long execTime) {
        LogUtils.info(log, "调用渠道检查绑卡ID有效性成功，cost:{},result:{},request:{}", execTime, result,request);
      }

      @Override
      public void fail(Exception e) {
        LogUtils.error(e, log, "调用渠道检查绑卡ID有效性失败，请求参数:{}", request);
        throw new CardBaseException(CardErrorCode.CHANNEL_CHECK_BIND, "");
      }
    });
  }
}
