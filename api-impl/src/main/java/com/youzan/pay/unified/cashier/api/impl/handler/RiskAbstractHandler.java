package com.youzan.pay.unified.cashier.api.impl.handler;

import com.youzan.nsq.client.exception.NSQException;
import com.youzan.pay.core.nsq.NsqProducer;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.impl.model.PayModel;
import com.youzan.pay.unified.cashier.api.request.nsq.RiskIpInfo;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * IP识别商户使用场景发送IP服务
 * Created by xielina on 2017/7/19.
 */
public abstract class RiskAbstractHandler<T extends RiskIpInfo, R> extends AbstractHandler<T, R> {

  @Resource
  private ThreadPoolTaskExecutor ipSceneThreadPoolTaskExecutor;
  @Resource
  private NsqProducer cashierProducer;

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  @Override
  protected void doBefore(T request) {
    super.doBefore(request);
    try{
      PayModel payModel = new PayModel();
      payModel.setPayNumber(request.getAcquireNo());
      payModel.setAccountId(Long.valueOf(request.getMchId()));
      payModel.setOrderNumber(request.getOutBizNo());
      payModel.setIp(request.getUserAgentIp());
      payModel.setTraceId(TracerContextUtils.getTracerId());
      payModel.setCreatedTime(sdf.format(new Date()));
      send(payModel);
    } catch (Exception e) {
      LogUtils.warn(e, log, "[消息发送风控异常,订单号={}]",request.getAcquireNo());
    }
  }

  private void send(PayModel payModel) {
    try {
      ipSceneThreadPoolTaskExecutor.execute(new Runnable() {
        @Override
        public void run() {
          try {
            LogUtils.info(log, "[发送风控消息：],payModel:{}", payModel);
            cashierProducer.send(payModel);
          } catch (NSQException e) {
            LogUtils.warn(e, log, "[消息发送风控异常,订单号={}]", payModel.getOrderNumber());
          }
        }

      });
    }catch (Exception e){
      LogUtils.warn(e, log, "[ip识别商户场景线程池异常]");
    }

  }
}
