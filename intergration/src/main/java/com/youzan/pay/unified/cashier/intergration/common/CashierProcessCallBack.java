package com.youzan.pay.unified.cashier.intergration.common;

/**
 * @author tao.ke Date: 2017/6/12 Time: 上午10:32
 */
public abstract class CashierProcessCallBack<T> {

  /**
   * 执行预处理操作，比如限流降级等动作，如果预处理需要快速失败，则抛出异常
   */
  public void beforeProcess() {
  }

  /**
   * 对参数进行检查，如果检查失败，则抛出异常
   */
  public void checkParams() {
  }

  /**
   * 核心的处理逻辑
   */
  public abstract T process();

  /**
   * 调用成功需要执行的方法，一般如果有需求会打印关键业务日志，增加时间次数监控等
   */
  public void succ(T result, long execTime) {
  }

  /**
   * 当业务步骤执行失败抛出异常的时候，该方法进行catch并进行返回封装处理
   */
  public void fail(Exception e) {

  }

  /**
   * 在finally模块中对一些资源进行释放
   */
  public void afterProcess() {
  }
}
