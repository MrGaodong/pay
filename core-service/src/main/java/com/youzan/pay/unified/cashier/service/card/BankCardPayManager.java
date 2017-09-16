package com.youzan.pay.unified.cashier.service.card;

import com.youzan.account.api.dto.merchant.UserMerchantDto;
import com.youzan.account.api.dto.merchant.UserMerchantSinleRequest;
import com.youzan.pay.assetcenter.api.request.MultiPayRequest;
import com.youzan.pay.assetcenter.api.request.model.PayDetailInfo;
import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.assetcenter.service.spi.constants.ExtraKeyConstants;
import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.customer.api.request.BindCardRequest;
import com.youzan.pay.customer.api.request.QueryBoundCardRequest;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankPayConfirmResult;
import com.youzan.pay.fundchannel.open.model.bankcard.SendSmsRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardPaySmsSendRequest;
import com.youzan.pay.unified.cashier.api.request.BaseCardPayRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.BindCardPrepayRequest;
import com.youzan.pay.unified.cashier.api.request.CardSixElementsDTO;
import com.youzan.pay.unified.cashier.api.request.QRCodeRechargeRequest;
import com.youzan.pay.unified.cashier.api.request.SigningConfirmPayRequest;
import com.youzan.pay.unified.cashier.api.request.SigningPrepayRequest;
import com.youzan.pay.unified.cashier.api.result.BindCardPrepayResult;
import com.youzan.pay.unified.cashier.api.result.ConfirmPayResult;
import com.youzan.pay.unified.cashier.api.result.SigningPrepayResult;
import com.youzan.pay.unified.cashier.core.utils.common.StringNumProcessor;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CardErrorCode;
import com.youzan.pay.unified.cashier.intergration.client.AssetCenterUnifiedPayServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.UserMerchantServiceClient;
import com.youzan.pay.unified.cashier.service.PayAccountManager;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/13 Time: 上午11:49
 */
@Component
@Slf4j
public class BankCardPayManager {

  /**
   * 银行卡号
   */
  private static final String BANK_CARD_NO = "BANK_CARD_NO";
  /**
   * 持卡人姓名
   */
  private static final String IDCARD_NAME = "IDCARD_NAME";
  /**
   * 身份证号
   */
  private static final String IDCARD_NO = "IDCARD_NO";
  /**
   * 银行卡对应手机号
   */
  private static final String BANK_CARD_PHONE = "BANK_CARD_PHONE";
  /**
   * 信用卡CVV2
   */
  private static final String CREDIT_CARD_CVV2 = "CREDIT_CARD_CVV2";
  /**
   * 信用卡有效期
   */
  private static final String CREDIT_CARD_VALIDTHRU = "CREDIT_CARD_VALIDTHRU";
  /**
   * 银行卡记录流水号
   */
  private static final String BANK_CARD_WATER_NO = "BANK_CARD_WATER_NO";
  /**
   * 用户IP地址
   */
  private static final String USER_IP = "USER_IP";

  /**
   * 最后4位卡尾数
   */
  private static final int LAST_FOUR_POSITION = 4;


  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Resource
  private AssetCenterUnifiedPayServiceClient assetCenterUnifiedPayServiceClient;

  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @Resource
  private PayAccountManager payAccountManager;

  @Resource
  private UserMerchantServiceClient userMerchantServiceClient;


  /**
   * 签约支付之预支付，通过收单系统交互
   */
  public SigningPrepayResult signingPrepay(SigningPrepayRequest request) {

    MultiPayRequest payRequest = buildPrepayCommonParams(request);

    // 卡支付六要素
    Map<String, String> outBizContext = payRequest.getOutBizContext();
    CardSixElementsDTO sixElementsDTO = request.getSixElements();
    outBizContext.put(BANK_CARD_NO, sixElementsDTO.getCardNo());
    outBizContext.put(IDCARD_NAME, sixElementsDTO.getName());
    outBizContext.put(IDCARD_NO, sixElementsDTO.getIdCardNo());
    outBizContext.put(BANK_CARD_PHONE, sixElementsDTO.getPhone());
    outBizContext.put(CREDIT_CARD_CVV2, sixElementsDTO.getCcvCode());
    outBizContext.put(CREDIT_CARD_VALIDTHRU, validDateFormat(sixElementsDTO.getValidDate()));
    outBizContext.put(USER_IP, request.getUserIp());

    MultiPayResult payResult = assetCenterUnifiedPayServiceClient.multiPay(payRequest);
    SigningPrepayResult prepayResult = new SigningPrepayResult();
    prepayResult.setOutBizNo(payResult.getOutBizNo());
    PayDetailResult detailResult = payResult.getPayDetailResult().get(0);
    prepayResult.setTargetId(detailResult.getAssetDetailNo());

    // 操作绑卡，如果失败，则忽略；继续后面操作
    try {
      prepayResult.setBindId(bindCard(request, sixElementsDTO));
    } catch (Exception e) {
      // ... 忽略异常不处理
      LogUtils.warn(e, log, "调用绑卡操作失败,request:{}", request);
    }
    return prepayResult;
  }

  /**
   * 绑卡操作，出错抛出异常，调用决定是否失败,返回bindId
   */
  private String bindCard(SigningPrepayRequest request, CardSixElementsDTO sixElementsDTO) {

    BindCardRequest cardRequest = new BindCardRequest();
    // 存储银行code，不使用银行名
    cardRequest.setBankCode(request.getBankCode());
    cardRequest.setBindType("QUICK");
    cardRequest.setCardholder(sixElementsDTO.getName());
    cardRequest.setCardNo(sixElementsDTO.getCardNo());

    cardRequest.setFansId(Long.valueOf(request.getCustomerId()));
    cardRequest.setFansType(Integer.valueOf(request.getCustomerType()));
    cardRequest.setMobile(sixElementsDTO.getPhone());
    cardRequest.setCardType(BankCardType.selectByDesc(sixElementsDTO.getCardType()).name());
    cardRequest.setCardNoLastFour(sixElementsDTO.getCardNo().substring(
        sixElementsDTO.getCardNo().length() - LAST_FOUR_POSITION));
    cardRequest.setPartnerId(Long.valueOf(request.getPartnerId()));
    cardRequest.setUserNo(payAccountManager.queryMchId(request.getBuyerId()));
    //cardRequest.setBuyerId(StringNumProcessor.convertToLong(request.getBuyerId()));

    BoundBankCardResult result = merchantBankCardServiceClient.bindBankCard(cardRequest);
    return String.valueOf(result.getCardBindId());

  }

  /**
   * 绑卡支付之预支付，发起绑卡支付请求
   *
   * @param request 卡id，路由由渠道处理
   * @return 返回应该包含补全信用卡信息
   */
  public BindCardPrepayResult bindCardPrepay(BindCardPrepayRequest request) {

    // 调用商户中心获取手机号(手机号安全模糊处理)
    QueryBoundCardRequest cardRequest = new QueryBoundCardRequest();
    cardRequest.setBindID(Long.valueOf(request.getBindId()));
    List<BoundBankCardResult> cardResults =
        merchantBankCardServiceClient.queryCardList(cardRequest);
    if (CollectionUtils.isEmpty(cardResults)) {
      LogUtils.warn(log, "不存在对应的绑卡Id.requestL{}", request);
      throw new CardBaseException(CardErrorCode.ILLEGAL_ARGUMENTS, "绑卡ID不存在");
    }

    String phone = cardResults.get(0).getMobile();

    MultiPayRequest payRequest = buildPrepayCommonParams(request);

    Map<String, String> outBizContext = payRequest.getOutBizContext();
    outBizContext.put(USER_IP, request.getUserIp());
    outBizContext.put(BANK_CARD_PHONE, phone);
    outBizContext.put(BANK_CARD_WATER_NO, request.getBindId());

    MultiPayResult payResult = assetCenterUnifiedPayServiceClient.multiPay(payRequest);

    BindCardPrepayResult result = new BindCardPrepayResult();
    result.setTargetId(payResult.getPayDetailResult().get(0).getAssetDetailNo());

    Map<String, Object> deepLinkInfo = payResult.getPayDetailResult().get(0).getDeepLinkInfo();
    if (deepLinkInfo != null && deepLinkInfo.containsKey("missedParams")) {
      result.setCompleteProperties((ArrayList<String>) deepLinkInfo.get("missedParams"));
    }

    return result;

  }

  /**
   * 有赞预支付请求参数中相同部分
   */
  private MultiPayRequest buildPrepayCommonParams(BaseCardPayRequest request) {

    MultiPayRequest payRequest = new MultiPayRequest();

    payRequest.setAcquireNo(request.getAcquireNo());
    payRequest.setOutBizNo(request.getOutBizNo());
    payRequest.setPayerId(request.getBuyerId());
    payRequest.setMchId(request.getMchId());
    payRequest.setPartnerId(request.getPartnerId());
    payRequest.setPayAmount(request.getPayAmount());

    List<PayDetailInfo> payDetailInfos = new ArrayList<>();
    PayDetailInfo payDetailInfo = new PayDetailInfo();

    payDetailInfo.setPayTool(request.getPayTool());
    payDetailInfo.setPayAmount(request.getPayAmount());
    payDetailInfo.setDiscountableAmount(request.getDiscountableAmount());
    payDetailInfo.setUndiscountableAmount(request.getUndiscountableAmount());
    payDetailInfo.setMemo(request.getMemo());
    payDetailInfo.setGoodsDesc(request.getTradeDesc());
    payDetailInfo.setAuthCode(request.getAuthCode());
    payDetailInfo.setWxSubOpenId(request.getWxSubOpenId());
    payDetailInfo.setPartnerNotifyUrl(request.getPartnerNotifyUrl());
    payDetailInfo.setPartnerReturnUrl(request.getPartnerReturnUrl());
    payDetailInfo.setExtendInfo(getExtendInfo(request));
    payDetailInfos.add(payDetailInfo);

    payRequest.setPayDetailInfos(payDetailInfos);

    return payRequest;
  }

  private Map<String,String> getExtendInfo(BaseCardPayRequest request) {
    Map<String,String> map= Maps.newHashMap();
    String userNo=getUserNo(request.getBuyerId());
    map.put(ExtraKeyConstants.BUYER_USER_NO, userNo);
    return map;
  }

  private String getUserNo(String userId){
    if(!isNumeric(userId)){
      return "-1";
    }
    UserMerchantSinleRequest userMerchantSinleRequest=new UserMerchantSinleRequest();
    userMerchantSinleRequest.setUserId(Long.valueOf(userId));
    UserMerchantDto
        userMerchantDto=userMerchantServiceClient.queryMchByUserId(userMerchantSinleRequest);
    if(userMerchantDto==null){
      return "-1";
    }
    return String.valueOf(userMerchantDto.getMerchantId());
  }

  private boolean isNumeric(String str) {
    if(str.isEmpty()){
      return false;
    }
    Pattern pattern = Pattern.compile("[0-9]+");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

  /**
   * 签约确认支付
   */
  public ConfirmPayResult signingPayConfirm(SigningConfirmPayRequest request) {

    BankPayConfirmRequest confirmRequest = new BankPayConfirmRequest();
    confirmRequest.setPayOrderNo(request.getTargetId());
    confirmRequest.setValidateCode(request.getVerificationCode());
    confirmRequest.setBizPartnerCode(request.getPartnerId());
    confirmRequest.setWaterNo(request.getBindId());

    BankPayConfirmResult confirmResult = channelBankCardServiceClient.payConfirm(confirmRequest);

    ConfirmPayResult payResult = new ConfirmPayResult();
    payResult.setBankCode(confirmResult.getBankCode());
    payResult.setLast4Code(confirmResult.getCardLast());
    payResult.setOutTradeNo(confirmResult.getOutTradeNo());
    payResult.setPayOrderNo(confirmResult.getPayOrderNo());
    payResult.setPaySuccess(confirmResult.isPaySuccess());
    payResult.setPhone(confirmResult.getPhone());

    return payResult;
  }

  /**
   * 绑卡确认支付
   */
  public ConfirmPayResult bindCardPayConfirm(BindCardConfirmPayRequest request) {

    BankPayConfirmRequest confirmRequest = new BankPayConfirmRequest();
    confirmRequest.setPayOrderNo(request.getBindId());
    confirmRequest.setValidateCode(request.getVerificationCode());
    confirmRequest.setBizPartnerCode(request.getPartnerId());
    confirmRequest.setPayOrderNo(request.getTargetId());
    confirmRequest.setWaterNo(request.getBindId());

    // 缺少的六要素
    if (request.getSixElements() != null) {

      BankPayConfirmRequest.BankCardInfo cardInfo = new BankPayConfirmRequest.BankCardInfo();
      // 如果不需要传，调用方入参应该为空，所以这里不需做判断
      cardInfo.setCcv2(request.getSixElements().getCcvCode());
      cardInfo.setIdCardNo(request.getSixElements().getIdCardNo());
      cardInfo.setIdCardType(request.getSixElements().getIdCardType());
      cardInfo.setName(request.getSixElements().getName());
      cardInfo.setValidThru(validDateFormat(request.getSixElements().getValidDate()));
      cardInfo.setPhone(request.getSixElements().getPhone());
      confirmRequest.setCardInfo(cardInfo);
    }

    BankPayConfirmResult confirmResult = channelBankCardServiceClient.payConfirm(confirmRequest);

    ConfirmPayResult payResult = new ConfirmPayResult();
    payResult.setBankCode(confirmResult.getBankCode());
    payResult.setLast4Code(confirmResult.getCardLast());
    payResult.setOutTradeNo(confirmResult.getOutTradeNo());
    payResult.setPayOrderNo(confirmResult.getPayOrderNo());
    payResult.setPaySuccess(confirmResult.isPaySuccess());
    payResult.setPhone(confirmResult.getPhone());

    return payResult;
  }

  /**
   * 请求外部渠道发送短信验证码，提供给用户完成确认扣款支付交易
   */
  public void sendConfirmSms(BankCardPaySmsSendRequest request) {

    SendSmsRequest smsRequest = new SendSmsRequest();
    smsRequest.setPayOrderNo(request.getTargetId());
    smsRequest.setSmsType(request.getSmsType());
    smsRequest.setBizPartnerCode(request.getPartnerId());

    channelBankCardServiceClient.sendConfirmSms(smsRequest);

  }

  /**
   * 将前端的有效期（12/21）转换成服务端需要的格式(1221)
   */
  private String validDateFormat(String viewValidDate) {
    return StringUtils.remove(viewValidDate, '/');
  }

}
