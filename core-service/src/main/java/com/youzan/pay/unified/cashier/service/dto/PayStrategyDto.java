/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.service.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author wulonghui
 * @version PayStrategyDto.java, v 0.1 2017-07-23 19:47
 */
@Data
public class PayStrategyDto implements Serializable{

  private static final long serialVersionUID = 2138385334077497501L;

  private Long id;

  private String bizStrategy;

  private String bizCode;

  private String payChannel;

  private Integer sort;

  private Integer available;

  private Integer visible;

  private Date gmtCreate;

  private Date gmtModify;
}
