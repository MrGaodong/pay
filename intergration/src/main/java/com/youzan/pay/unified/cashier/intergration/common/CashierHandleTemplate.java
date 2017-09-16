package com.youzan.pay.unified.cashier.intergration.common;

import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.unified.cashier.core.utils.exception.CardBaseException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tao.ke Date: 2017/6/12 Time: 上午10:31
 */
@Slf4j
public class CashierHandleTemplate {

  public static <T> T execute(CashierProcessCallBack<T> action) {

    T result = null;

    long startTime = System.currentTimeMillis();

    try {
      {
        action.checkParams();
      }
      {
        result = (T) action.process();

      }
      {
        action.succ(result, System.currentTimeMillis() - startTime);
      }
    } catch (Exception e) {

      // 业务异常，这里不捕获
      if (e instanceof CardBaseException) {
        throw e;
      } else {
        action.fail(e);
      }
    } finally {
      try {

        {
          action.afterProcess();
        }

      } catch (Exception e) {
        LogUtils.error(e, log, "finally中调用方法出现异常！");
      }

    }
    return result;
  }

}
