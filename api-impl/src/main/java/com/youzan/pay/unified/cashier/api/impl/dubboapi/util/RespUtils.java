package com.youzan.pay.unified.cashier.api.impl.dubboapi.util;

import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.core.utils.log.LogUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-09-03
 * Time: 下午3:40
 * <p>响应处理类</p>
 */
@Slf4j
public class RespUtils<T> {

  private static final String QRCODEERRORMSG="生成二维码扫码链接失败";

  private static final String QRCODEERRORCODE="10017001";

  private static final String SUCCESSMSG="处理成功";

  private static final boolean SUCCESSFLAG=true;

  private static final String RESULTCODE="0";

  public static <T> DataResult<T> buildFailResp(){
    DataResult<T> response=new DataResult<T>();
    response.setData(null);
    response.setSuccess(false);
    response.setMessage(QRCODEERRORMSG);
    response.setCode(Integer.valueOf(QRCODEERRORCODE));
    return response;
  }

  public static <T> DataResult<T> buildSuccessResp(T result){
    try {
      LogUtils.info(log, "开始结果转换,result={}",result);
      DataResult<T> resp = new DataResult<T>();
      resp.setData(result);
      resp.setMessage(SUCCESSMSG);
      resp.setSuccess(SUCCESSFLAG);
      resp.setCode(Integer.valueOf(RESULTCODE));
      LogUtils.info(log,"[输出url结果:{}]",resp);
      return resp;
    }catch (Exception e){
      LogUtils.error(e,log,"resp出错了");
      return null;
    }
  }

}