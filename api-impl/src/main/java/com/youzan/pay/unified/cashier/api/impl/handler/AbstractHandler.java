/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.tracer.TracerContextUtils;
import com.youzan.pay.core.utils.validate.ValidationResult;
import com.youzan.pay.core.utils.validate.ValidationUtils;
import com.youzan.pay.unified.cashier.api.impl.constant.Loggers;
import com.youzan.pay.unified.cashier.core.model.domain.ApiDigestLogInfo;
import com.youzan.pay.unified.cashier.api.impl.utils.ResponseBuilder;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.CommonErrorCode;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author twb
 * @version AbstractHandler.java, v 0.1 2017-01-10 11:20
 */
public abstract class AbstractHandler<T, R> implements Handler<T, R> {

  //日志：（使用的是子类的class)
  protected Logger log = LoggerFactory.getLogger(this.getClass());
  //性能摘要日志
  protected Logger digestLog = Loggers.DIGEST_LOGGER;


  @Override
  public Response<R> handle(T request) {

    long start = System.nanoTime();
    boolean success = false;

    try {

      RpcContext.getContext().setAttachment("async", "false");

      TracerContextUtils.genTracerId();

      doBefore(request);

      R result = doExecute(request);

      doAfter(request, result);

      success = true;

      return ResponseBuilder.buildSuccessResp(result);

    } catch (IllegalArgumentException e) {

      LogUtils.warn(e, log, "[处理器]-参数异常,req={}", request);
      return ResponseBuilder.buildFailureResp(CommonErrorCode.ILLEGAL_ARGUMENTS);

    } catch (CardBaseException e) {

      LogUtils.error(e, log, "[处理器]-业务异常,request={}", request.toString());
      return ResponseBuilder.buildFailureResp(e.getCode(), e.getDesc());

    }catch (BaseException e) {

      LogUtils.error(e, log, "[处理器]-业务异常,request={}", request);
      return ResponseBuilder.buildFailureResp(e.getResultCode());

    } catch (Exception e) {

      LogUtils.error(e, log, "[处理器]-未知异常,req={}", request);
      return ResponseBuilder.buildFailureResp(CommonErrorCode.UNKNOWN_ERROR);

    } finally {

      long duration = (System.nanoTime() - start) / 1000000;
      //监控日志
      logMonitor(duration, success);

      TracerContextUtils.clearTracerId();
    }
  }

  /**
   * 具体业务由子类实现
   */
  protected abstract R execute(T request) throws Exception;

  protected void doBefore(T request) {

    LogUtils.info(log, "前置处理处理，request={}", request);
    //参数处理
    preProcessParams(request);
    // 参数校验
    validate(request);

  }
  /**
   * 对内部参数进行处理
   *
   * @param request 请求的参数对象
   */
  protected void preProcessParams(T request) {

  }

  protected void doAfter(T request, R result) {

    LogUtils.debug(log, "后置处理，request={}", request);
  }

  protected void validate(T request) {
    ValidationResult result = ValidationUtils.validate(request);
    if (!result.isSuccess()) {
      throw new IllegalArgumentException(result.getMessage());
    }
  }

  protected R doExecute(T request) throws Exception {
    return execute(request);
  }

  //监控日志
  private void logMonitor(long duration, boolean success) {
    String className = this.getClass().getSimpleName();
    ApiDigestLogInfo logInfo = new ApiDigestLogInfo();
    logInfo.setApi(className);
    logInfo.setDuration(duration);
    logInfo.setResult(success ? logInfo.SUCCESS_RESULT : logInfo.FAIL_RESULT);
    digestLog.info("{}", JSON.toJSONString(logInfo));
  }
}
