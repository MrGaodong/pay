/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.utils;

import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.core.utils.resultcode.ResultCode;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CommonErrorCode;

/**
 * @author twb
 * @version ResponseBuilder.java, v 0.1 2017-01-10 12:38
 */
public class ResponseBuilder {

  public static <T> Response<T> buildSuccessResp(T result) {
    return buildResp(CommonErrorCode.SUCCESS, result, true);
  }

  public static <T> Response<T> buildFailureResp(ResultCode resultCode) {
    return buildFailureResp(resultCode.getCode(), resultCode.getDesc());
  }

  public static <T> Response<T> buildFailureResp(String code,String desc) {
    return buildResp(code, desc, null, false);
  }

  public static <T> Response<T> buildResp(ResultCode resultCode, T result, boolean success) {
    return buildResp(resultCode.getCode(), resultCode.getDesc(), result, success);
  }

  public static <T> Response<T> buildResp(String code, String desc, T result, boolean success) {
    Response<T> response = new Response<T>();
    response.setSuccess(success);
    response.setResultCode(code);
    response.setMsg(desc);
    response.setResult(result);
    response.setRespId(getRespId());
    return response;
  }

  private static String getRespId() {
    return TracerContextUtils.getTracerId();
  }
}
