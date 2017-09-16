package com.youzan.pay.unified.cashier.intergration.client;

import com.youzan.pay.cardvoucher.api.summarycard.SummaryCardQueryService;
import com.youzan.pay.cardvoucher.api.summarycard.dto.SummaryCardInfoDTO;
import com.youzan.pay.cardvoucher.api.summarycard.request.QuerySummaryCardInfoRequest;
import com.youzan.pay.core.api.model.response.ListResult;
import com.youzan.pay.core.utils.log.LogUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 *@Description: 调用卡券平台的dubbo服务
 * Created by xielina on 2017/8/2.
 */
@Component
@Slf4j
public class SummaryCardQueryServiceClient {
  @Resource
  private SummaryCardQueryService summaryCardQueryService;

/**
 * @Description 调用卡券平台查询用户预付卡信息服务
 */
  public ListResult<SummaryCardInfoDTO>  querySummaryCardInfo(QuerySummaryCardInfoRequest request){
    LogUtils.info(log, "开始调用卡券平台dubbo服务：request＝{}", request);

    ListResult<SummaryCardInfoDTO> result = null;
    try {
      result = summaryCardQueryService.querySummaryCardInfo(request);
    }catch(Exception e){
      LogUtils.warn(e,log, "调用summaryCardQueryService失败");
      return null;
    }

    if (!result.isSuccess()) {
      LogUtils.info(log,"结束调用卡券平台dubbo服务，卡券平台返回用户预付卡信息失败");
      return null;
    }else{
      LogUtils.info(log,"结束调用卡券平台dubbo服务:result={}",result);
    }
    return result;
  }
}
