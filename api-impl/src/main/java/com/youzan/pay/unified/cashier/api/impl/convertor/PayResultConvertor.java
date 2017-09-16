/**
 * Youzan.com Inc. Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.api.impl.convertor;

import com.youzan.pay.assetcenter.api.result.MultiPayResult;
import com.youzan.pay.assetcenter.api.result.model.PayDetailResult;
import com.youzan.pay.unified.cashier.api.result.PayResult;
import com.youzan.pay.unified.cashier.core.utils.exception.BaseException;
import com.youzan.pay.unified.cashier.core.utils.resultcode.errorcode.DispatcherPayErrorCode;

import com.alibaba.fastjson.JSON;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author twb
 * @version PayResultConvertor.java, v 0.1 2017-01-11 11:35
 */
public class PayResultConvertor {

  public static PayResult convert(MultiPayResult result) {

    List<PayDetailResult> payDetailResults;

    if (result != null && CollectionUtils
        .isNotEmpty(payDetailResults = result.getPayDetailResult())) {

      PayDetailResult payDetailResult = payDetailResults.get(0);

      PayResult payResult = new PayResult();
      payResult.setDeepLinkInfo(JSON.toJSONString(payDetailResult.getDeepLinkInfo()));

      return payResult;
    } else {
      throw new BaseException(DispatcherPayErrorCode.PAY_RESULT_ERROR);
    }
  }
}
