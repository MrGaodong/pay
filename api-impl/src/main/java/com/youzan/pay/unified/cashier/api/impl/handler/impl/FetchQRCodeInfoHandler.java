/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.handler.impl;

import com.youzan.pay.unified.cashier.api.impl.handler.AbstractHandler;
import com.youzan.pay.unified.cashier.api.request.FetchQRCodeInfoRequest;
import com.youzan.pay.unified.cashier.api.request.QRCodeInfo;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.service.cache.CacheService;
import com.youzan.pay.unified.cashier.service.resultcode.errorcode.QRCodePayErrorCode;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twb
 * @version FetchQRCodeInfoHandler.java, v 0.1 2017-06-23 11:41
 */

@Component
@Slf4j
public class FetchQRCodeInfoHandler extends AbstractHandler<FetchQRCodeInfoRequest, QRCodeInfo> {

  @Resource
  private CacheService cacheService;

  @Override
  protected QRCodeInfo execute(FetchQRCodeInfoRequest request) throws Exception {

    QRCodeInfo info = cacheService.get(request.getImageNo(), QRCodeInfo.class);

    if (info == null) {
      throw new BaseException(QRCodePayErrorCode.NO_CACHE_ERROR);
    }

    return info;
  }
}
