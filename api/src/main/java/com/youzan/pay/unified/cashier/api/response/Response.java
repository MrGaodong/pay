package com.youzan.pay.unified.cashier.api.response;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by twb on 17/1/9.
 */
@Data
public class Response<T> implements Serializable {

  private static final long serialVersionUID = 252293958539357168L;

  private boolean success;

  private String resultCode;

  private String msg;

  private T result;

  private String respId;
}
