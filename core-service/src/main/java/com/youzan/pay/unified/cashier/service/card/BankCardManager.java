package com.youzan.pay.unified.cashier.service.card;

import com.youzan.pay.core.common.model.enums.BankCardType;
import com.youzan.pay.core.common.model.enums.BankCodeInfoType;
import com.youzan.pay.customer.api.request.QueryBoundCardRequest;
import com.youzan.pay.customer.api.result.BoundBankCardResult;
import com.youzan.pay.fundchannel.open.enums.ChannelBindStatus;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankBindCheckResult;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckRequest;
import com.youzan.pay.fundchannel.open.model.bankcard.BankCardCheckResult;
import com.youzan.pay.unified.cashier.api.request.BankCardInfoCheckRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardListQueryRequest;
import com.youzan.pay.unified.cashier.api.request.BankCardUnbindRequest;
import com.youzan.pay.unified.cashier.api.result.BankCardInfoCheckResult;
import com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult;
import com.youzan.pay.unified.cashier.core.utils.common.BankIconConfig;
import com.youzan.pay.unified.cashier.core.utils.common.DataMixProcessor;
import com.youzan.pay.unified.cashier.core.utils.common.MyCollector;
import com.youzan.pay.unified.cashier.core.utils.common.StringNumProcessor;
import com.youzan.pay.unified.cashier.intergration.client.ChannelBankCardServiceClient;
import com.youzan.pay.unified.cashier.intergration.client.MerchantBankCardServiceClient;
import com.youzan.pay.unified.cashier.service.PayAccountManager;
import com.youzan.pay.unified.cashier.service.cache.PayCardRecordCache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import static com.youzan.pay.unified.cashier.api.result.BankCardListQueryResult.BankCard;

/**
 * @author tao.ke Date: 2017/6/12 Time: 下午2:20
 */
@Slf4j
@Component
public class BankCardManager {

  /**
   * 银行卡可支付状态
   */
  private static final int BANK_CARD_CAN_PAY = 1;

  @Resource
  private ChannelBankCardServiceClient channelBankCardServiceClient;

  @Resource
  private MerchantBankCardServiceClient merchantBankCardServiceClient;

  @Resource
  private PayCardRecordCache payCardRecordCache;

  @Resource
  private PayAccountManager payAccountManager;


  /**
   * 商户返回卡列表数据，把其中最近一次使用的卡放到第一位
   */
  public BankCardListQueryResult queryCardList(BankCardListQueryRequest request) {

    BankCardListQueryResult queryResult = new BankCardListQueryResult();

    // 1. 如果用户没登录，则直接返回空
    if (StringUtils.isEmpty(request.getBuyerId()) && StringUtils.isEmpty(request.getCustomerId())) {
      return queryResult;
    }

    // 2. 设置参数，查询已经绑定的银行卡列表数据
    List<BoundBankCardResult> results = queryBindCards(request);

    if (CollectionUtils.isEmpty(results)) {
      return queryResult;
    }

    // 3. 从缓存获取最近一次银行卡
    String key = payCardRecordCache.buildKey(request.getBuyerId(), request.getCustomerId(), request.getCustomerType());
    String cardValue = payCardRecordCache.getBindId(key);

    //4. 提取bindId到list中查询可用信息，调用渠道进行有效性校验
    List<Long> bindIds = results.stream().map(BoundBankCardResult::getCardBindId).collect(Collectors.toList());
    Map<Long, ChannelBindStatus> checkResultMap = queryBindIdAccessStatus(request, bindIds);

    //5. 遍历结果，从map中获取key，如果存在则按照access来返回是否有效
    List<BankCard> cards = convertToBankCards(results, cardValue, checkResultMap);

    queryResult.setCardList(cards);
    return queryResult;
  }

  /**
   * 从商户中心那里获取绑卡列表
   */
  private List<BoundBankCardResult> queryBindCards(BankCardListQueryRequest request) {

    QueryBoundCardRequest baseRequest = new QueryBoundCardRequest();

    baseRequest.setUserNo(payAccountManager.queryMchId(request.getBuyerId()));
    baseRequest.setFansId(StringNumProcessor.convertToLong(request.getCustomerId()));
    baseRequest.setFansType(StringNumProcessor.convertToLong(request.getCustomerType()));
    baseRequest.setPartnerId(StringNumProcessor.convertToLong(request.getPartnerId()));

    return merchantBankCardServiceClient.queryCardList(baseRequest);
  }

  /**
   * 将返回的绑卡结果列表进行转换，过滤无效的卡id，并且获取最近使用的卡，放到第一个
   */
  private List<BankCard> convertToBankCards(List<BoundBankCardResult> results, String cardValue,
                                            Map<Long, ChannelBindStatus> checkResultMap) {

    BankCard firstCard = null;
    List<BankCard> cards = new ArrayList<>();

    for (BoundBankCardResult result : results) {

      BankCardListQueryResult.BankCard card = new BankCardListQueryResult.BankCard();
      ChannelBindStatus bindStatus = checkResultMap.get(result.getCardBindId());

      // 1 首先校验是否有效
      if (bindStatus == null || bindStatus == ChannelBindStatus.UNBIND_CARD) {
        continue;
      } else if (bindStatus == ChannelBindStatus.BIND_AVAILABLE) {
        card.setValid(true);
      } else {
        card.setValid(false);
      }

      // 2 转换到返回结果结构
      card.setBankName(BankCodeInfoType.valueOf(result.getBankCode()).getBankName());
      card.setBankCode(result.getBankCode());
      card.setBindId(String.valueOf(result.getCardBindId()));
      card.setLast4Code(result.getCardNoLastFour());
      card.setPhone(DataMixProcessor.phoneNumMix(result.getMobile()));
      //card.setPhone(result.getMobile());//明文吐出

      // 信用卡储蓄卡等
      card.setCardName(BankCardType.valueOf(result.getCardType()).getDesc());
      BankCodeInfoType codeInfoType = BankCodeInfoType.valueOf(result.getBankCode());
      if (codeInfoType != null) {
        card.setLogo(codeInfoType.getIcon());
      }

      // 3. 匹配缓存，确认第一个卡位置
      if (StringUtils.equals(cardValue, card.getBindId()) && card.isValid()) {
        firstCard = card;
        continue;
      }

      // 4 放到返回列表中
      cards.add(card);
    }

    //5 对结构的首位卡进行替换成 用户最近使用的卡
    if (firstCard != null) {
      cards.add(0, firstCard);
    }
    return cards;
  }

  /**
   * 去渠道查询bindIds是否有效
   */
  private Map<Long, ChannelBindStatus> queryBindIdAccessStatus(BankCardListQueryRequest request,
                                                               List<Long> bindIds) {
    BankBindCheckRequest checkRequest = new BankBindCheckRequest();
    checkRequest.setWaterNoList(bindIds);
    checkRequest.setBizPartnerCode(request.getPartnerId());
    List<BankBindCheckResult.Result>
        checkResult = channelBankCardServiceClient.checkBindIds(checkRequest);

    if (CollectionUtils.isEmpty(checkResult)) {
      return new HashMap<>();
    }
    return checkResult.stream().collect(MyCollector
                                            .listToMap(BankBindCheckResult.Result::getWaterNo,
                                                       BankBindCheckResult.Result::getBindStaus));
  }

  /**
   * 校验银行卡，然后获取银行卡校验信息
   */
  public BankCardInfoCheckResult queryCardValidatorInfo(BankCardInfoCheckRequest request) {

    BankCardCheckRequest channelParams = new BankCardCheckRequest();
    channelParams.setCardNo(request.getCardNo());
    channelParams.setBizPartnerCode(request.getPartnerId());

    BankCardCheckResult result = channelBankCardServiceClient.checkBankCard(channelParams);

    BankCardInfoCheckResult cardInfo = new BankCardInfoCheckResult();
    cardInfo.setBankCode(result.getBankCode());
    cardInfo.setBankName(result.getBankName());
    cardInfo.setCardNo(result.getCardNo());
    cardInfo.setCardType(BankCardType.selectByCode(result.getCardType()).getDesc());
    cardInfo.setValid("1".equals(result.getValid()));

    return cardInfo;
  }

  /**
   * 解绑卡请求到商户中心
   */
  public boolean unbindBankCard(BankCardUnbindRequest request) {

    QueryBoundCardRequest cardRequest = new QueryBoundCardRequest();

    //设置参数
    cardRequest.setPartnerId(Long.valueOf(request.getPartnerId()));

    if (StringUtils.isNotBlank(request.getBuyerId())) {
      cardRequest.setUserNo(payAccountManager.queryMchId(request.getBuyerId()));
    } else {
      cardRequest.setFansType(Long.valueOf(request.getCustomerType()));
      cardRequest.setFansId(Long.valueOf(request.getCustomerId()));
    }

    cardRequest.setBindID(Long.valueOf(request.getBindId()));

    return merchantBankCardServiceClient.unbindCard(cardRequest);

  }

}
