package com.youzan.pay.unified.cashier.api.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.youzan.pay.unified.cashier.api.UnifiedCashierKuahaoPayService;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.impl.handler.impl.KuahaoPayHandler;
import com.youzan.pay.unified.cashier.api.request.KuahaoPayRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.KuahaoPayResult;
import com.youzan.pay.unified.cashier.api.result.PayResult;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by liumeng on 2017/7/12.
 */
@Path("/kuahaoPay")
@Service(protocol = {"rest","dubbo"}, registry = {"haunt"})
@Slf4j
public class UnifiedCashierKuahaoPayServiceImpl implements UnifiedCashierKuahaoPayService{

    @Resource
    private HandlerFactory handlerFactory;

    @Path("/kuahao")
    @POST
    @Override
    public Response<KuahaoPayResult> kuahaoPay(KuahaoPayRequest request){

        KuahaoPayHandler kuahaoPayHandler = handlerFactory.getHandler(KuahaoPayHandler.class);
        Response<KuahaoPayResult> response = kuahaoPayHandler.handle(request);

        return response;
    }

}
