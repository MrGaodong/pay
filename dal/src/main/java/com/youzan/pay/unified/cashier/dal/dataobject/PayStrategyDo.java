/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.dal.dataobject;

import java.util.Date;

import lombok.Data;

/**
 * @author wulonghui
 * @version PayStrategyDo.java, v 0.1 2017-07-23 18:34
 */
@Data
public class PayStrategyDo {

  private Long id;

  private String bizStrategy;

  private String bizCode;

  private String payChannel;

  private Integer sort;

  private Integer available;

  private Integer visible;

  private Date gmtCreate;

  private Date gmtModify;

  public PayStrategyDo(){

  }

  public PayStrategyDo(Long id, String bizStrategy, String bizCode, String payChannel, Integer sort,
                       Integer available, Integer visible, Date gmtCreate, Date gmtModify) {
    this.id=id;
    this.bizStrategy=bizStrategy;
    this.bizCode=bizCode;
    this.payChannel=payChannel;
    this.sort=sort;
    this.available=available;
    this.visible=visible;
    this.gmtCreate=gmtCreate;
    this.gmtModify=gmtModify;
  }
}
