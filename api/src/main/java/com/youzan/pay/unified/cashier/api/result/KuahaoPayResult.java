package com.youzan.pay.unified.cashier.api.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liumenKuahaoPayResultg on 2017/7/12.
 */
@Data
public class KuahaoPayResult implements Serializable {

    /**
     * 二维码
     */
    private QRCodeInfoResult qrCodeInfoResult;
}
