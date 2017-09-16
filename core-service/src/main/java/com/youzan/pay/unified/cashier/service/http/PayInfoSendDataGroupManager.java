package com.youzan.pay.unified.cashier.service.http;

import com.youzan.http.impl.nio.client.CloseableHttpAsyncClient;
import com.youzan.http.impl.nio.client.HttpAsyncClients;
import com.youzan.pay.core.utils.log.LogUtils;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/23 Time: 上午10:41
 */
@Slf4j
public class PayInfoSendDataGroupManager {

  /**
   * 连接超时1000ms，每次请求读超时100ms,默认池子的空闲超时为1分钟
   */
  private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100).setConnectTimeout(1000)
      .setConnectionRequestTimeout(100).build();


  // 按照默认的线程池配置即可：perRouter=2，maxTotal=20
  private static final CloseableHttpAsyncClient asyncHttpClient =
      HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();

  static {
    asyncHttpClient.start();
  }

  private String dataGroupUrl;

  /**
   * 构造函数，区分环境配置
   *
   * @param dataGroupUrl 数据组不同环境的url地址
   */
  public PayInfoSendDataGroupManager(String dataGroupUrl) {
    this.dataGroupUrl = dataGroupUrl + "/bankcard/info?current_day=%s&order_no=%s&phone=%s&payment_method=%s&ip=%s";
  }

  /**
   * 发送支付客户信息到数据组
   *
   * @param orderNo   订单号
   * @param ip        ip地址
   * @param phone     用户手机号
   * @param payMethod 支付方法
   */
  public void sendInfoToDG(String orderNo, String ip, String phone, PayMethodType payMethod) {

    // 组装需要提交的参数
    String getUrl = String.format(dataGroupUrl,
                                  LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                                  orderNo, phone, payMethod.getCode(), ip);

    HttpGet httpGet = new HttpGet(getUrl);
    asyncHttpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
      @Override
      public void completed(HttpResponse result) {
        LogUtils.info(log, "发送支付用户数据到数据组成功.requestUrl:{}", JSON.toJSONString(getUrl));
      }

      @Override
      public void failed(Exception ex) {
        LogUtils.warn(ex, log, "发送支付用户数据到数据组失败.request:{}", JSON.toJSONString(getUrl));
      }

      @Override
      public void cancelled() {
        LogUtils.warn(log, "发送支付用户数据到数据组被取消.requestUrl:{}", JSON.toJSONString(getUrl));
      }
    });
  }

}
