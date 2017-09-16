package com.youzan.pay.unified.cashier.api.impl.dubboapi;

import com.youzan.pay.core.api.model.response.DataResult;
import com.youzan.pay.core.cache.redis.RedisCacheManager;
import com.youzan.pay.core.utils.log.LogUtils;
import com.youzan.pay.core.utils.validate.ValidationResult;
import com.youzan.pay.core.utils.validate.ValidationUtils;
import com.youzan.pay.customer.api.request.QuerySignKeyRequest;
import com.youzan.pay.unified.cashier.api.CreateUnifiedCashierUrlService;
import com.youzan.pay.unified.cashier.api.impl.constant.Constant;
import com.youzan.pay.unified.cashier.api.impl.dubboapi.util.RespUtils;
import com.youzan.pay.unified.cashier.api.impl.handler.HandlerFactory;
import com.youzan.pay.unified.cashier.api.request.CashierOrderRequest;
import com.youzan.pay.unified.cashier.api.request.CreateUnifiedCashierUrlRequest;
import com.youzan.pay.unified.cashier.api.request.GetUnifiedCashierUrlRequest;
import com.youzan.pay.unified.cashier.api.response.Response;
import com.youzan.pay.unified.cashier.api.result.CashierOrderResult;
import com.youzan.pay.unified.cashier.api.result.CreateUnifiedCashierUrlResult;
import com.youzan.pay.unified.cashier.api.result.GetUnifiedCashierUrlResult;
import com.youzan.pay.unified.cashier.core.utils.model.CacheTradeInfo;
import com.youzan.pay.unified.cashier.intergration.client.SignKeyServiceClient;
import com.youzan.pay.unified.cashier.service.sign.SignService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-08-30
 * Time: 下午6:01
 * <p>创建收银台链接dubboapi实现类</p>
 */

@Path("/url")
@Service(protocol = {"dubbo","rest"},registry = {"haunt"})
@Slf4j
public class CreateUnifiedCashierUrlServiceImpl implements CreateUnifiedCashierUrlService {

  @Resource
  private HandlerFactory handlerFactory;

  @Resource
  private RedisCacheManager redisCacheManager;

  @Resource
  private SignKeyServiceClient signKeyServiceClient;

  @Path("/create")
  @POST
  @Override
  public DataResult<CreateUnifiedCashierUrlResult> create(
      CreateUnifiedCashierUrlRequest createUnifiedCashierUrlRequest)
      throws Exception {
    //1.校验参数
    LogUtils.info(log,"[login createUnifiedCashierUrlRequest={}]",createUnifiedCashierUrlRequest);
    checkParams(createUnifiedCashierUrlRequest);
    //2.商户平台获取秘钥,组装签名

    String sign=getSignByMerchantId(createUnifiedCashierUrlRequest);

    LogUtils.info(log,"[createUnifiedCashierUrlRequest:sign{}]",sign);

    //3.加入缓存
    CacheTradeInfo cacheTradeInfo=getCacheTradeInfo(createUnifiedCashierUrlRequest);

    try{
      addCache(sign,cacheTradeInfo);
    }catch (Exception e){
      return RespUtils.buildFailResp();
    }

    //4.组装结果，返回
    CreateUnifiedCashierUrlResult result=new CreateUnifiedCashierUrlResult();
    result.setUrl(Constant.CASHIER_URL+sign);
    return RespUtils.buildSuccessResp(result);
  }

  /**
   * 创建收单
   */
  @POST
  @Path("/order/create")
  @Override
  public DataResult<CashierOrderResult> createOrder(CashierOrderRequest request) {
    LogUtils.info(log, "[CashierOrderRequest],requestData={}", request);

    CashierOrderHandler cashierH5AcquireOrderHandler = handlerFactory.getHandler(
        CashierOrderHandler.class);

    DataResult<CashierOrderResult> resp = buildDataResult(cashierH5AcquireOrderHandler.handle(request));

    LogUtils.info(log, "[CashierOrderResult],resp={}", resp);

    return resp;
  }

  /**
   * response转换为dataResult
   * @param resp
   * @return
   */
  private DataResult<CashierOrderResult> buildDataResult(Response<CashierOrderResult> resp) {
    DataResult<CashierOrderResult> dataResult=new DataResult<>();
    dataResult.setCode(0);
    dataResult.setMessage("处理成功");
    dataResult.setSuccess(true);
    dataResult.setRequestId(UUID.randomUUID().toString());
    dataResult.setData(resp.getResult());
    return dataResult;
  }

  /**
   * 获得缓存结果
   * @param request
   * @return
   */
  @POST
  @Path("/getcache")
  @Override
  public DataResult<GetUnifiedCashierUrlResult> get(GetUnifiedCashierUrlRequest request) {
    LogUtils.info(log,"[GetUnifiedCashierUrlRequest:{}]",request);
    CacheTradeInfo cacheTradeInfo=null;
    try{
      cacheTradeInfo=redisCacheManager.get(request.getSign(),CacheTradeInfo.class);
    }catch (Exception e){
      LogUtils.error(e,log,"[redisCacheManager挂了],sign={}",request.getSign());
      return RespUtils.buildFailResp();
    }
    GetUnifiedCashierUrlResult result=buildResult(cacheTradeInfo);
    return RespUtils.buildSuccessResp(result);
  }

  /**
   * 获得缓存结果
   * @param cacheTradeInfo
   * @return
   */
  private GetUnifiedCashierUrlResult buildResult(CacheTradeInfo cacheTradeInfo) {
    GetUnifiedCashierUrlResult getUnifiedCashierUrlResult=new GetUnifiedCashierUrlResult();
    getUnifiedCashierUrlResult.setAppType(cacheTradeInfo.getAppType());
    getUnifiedCashierUrlResult.setBizAction(cacheTradeInfo.getBizAction());
    getUnifiedCashierUrlResult.setBizMode(cacheTradeInfo.getBizMode());
    getUnifiedCashierUrlResult.setBizProd(cacheTradeInfo.getBizProd());
    getUnifiedCashierUrlResult.setCurrencyCode(cacheTradeInfo.getCurrencyCode());
    getUnifiedCashierUrlResult.setDiscountableAmount(cacheTradeInfo.getDiscountableAmount());
    getUnifiedCashierUrlResult.setGoodsDesc(cacheTradeInfo.getGoodsDesc());
    getUnifiedCashierUrlResult.setGoodsName(cacheTradeInfo.getGoodsName());
    getUnifiedCashierUrlResult.setImageUrl(cacheTradeInfo.getImageUrl());
    getUnifiedCashierUrlResult.setIsNeedPopupView(cacheTradeInfo.getIsNeedPopupView());
    getUnifiedCashierUrlResult.setIsNeedSuccessJump(cacheTradeInfo.getIsNeedSuccessJump());
    getUnifiedCashierUrlResult.setMchId(cacheTradeInfo.getMchId());
    getUnifiedCashierUrlResult.setMemo(cacheTradeInfo.getMemo());
    getUnifiedCashierUrlResult.setOutBizNo(cacheTradeInfo.getOutBizNo());
    getUnifiedCashierUrlResult.setPartnerId(cacheTradeInfo.getPartnerId());
    getUnifiedCashierUrlResult.setMchName(cacheTradeInfo.getMchName());
    getUnifiedCashierUrlResult.setPartnerNotifyUrl(cacheTradeInfo.getPartnerNotifyUrl());
    getUnifiedCashierUrlResult.setPayAmount(cacheTradeInfo.getPayAmount());
    getUnifiedCashierUrlResult.setReturnUrl(cacheTradeInfo.getReturnUrl());
    getUnifiedCashierUrlResult.setSignType(cacheTradeInfo.getSignType());
    getUnifiedCashierUrlResult.setSlideText(cacheTradeInfo.getSlideText());
    getUnifiedCashierUrlResult.setTel(cacheTradeInfo.getTel());
    getUnifiedCashierUrlResult.setTradeDesc(cacheTradeInfo.getTradeDesc());
    getUnifiedCashierUrlResult.setUndiscountableAmount(cacheTradeInfo.getUndiscountableAmount());
    return getUnifiedCashierUrlResult;
  }

  /**
   * 加入缓存
   * @param sign
   * @param cacheTradeInfo
   * @throws Exception
   */
  private void addCache(String sign, CacheTradeInfo cacheTradeInfo) throws Exception {
    try{
      redisCacheManager.put(sign,cacheTradeInfo);
      LogUtils.info(log,"[缓存key={}，value={}]",sign,cacheTradeInfo);
    }catch (Exception e){
      LogUtils.error(e,log,"[redis挂了,key={}]",sign);
      throw new Exception("redis挂了",e);
    }
  }


  /**
   * bean 转换
   * @param createUnifiedCashierUrlRequest
   * @return
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  private CacheTradeInfo getCacheTradeInfo(CreateUnifiedCashierUrlRequest createUnifiedCashierUrlRequest)
      throws InvocationTargetException, IllegalAccessException {
    LogUtils.info(log,"[进入转换]，createUnifiedCashierUrlRequest＝",createUnifiedCashierUrlRequest);
    CacheTradeInfo cacheTradeInfo=new CacheTradeInfo();
    BeanUtils.copyProperties(cacheTradeInfo,createUnifiedCashierUrlRequest);
    LogUtils.info(log,"[转换后 cacheTradeInfo＝{}]",cacheTradeInfo);
    return cacheTradeInfo;
  }

  private void checkParams(CreateUnifiedCashierUrlRequest createUnifiedCashierUrlRequest) {
    LogUtils.info(log, "前置处理处理，request={}", createUnifiedCashierUrlRequest);
    // 参数校验
//    validate(createUnifiedCashierUrlRequest);
  }

//  protected void validate(CreateUnifiedCashierUrlRequest request) {
//    ValidationResult result = ValidationUtils.validate(request);
//    if (!result.isSuccess()) {
//      throw new IllegalArgumentException(result.getMessage());
//    }
//  }

  private String getSignByMerchantId(CreateUnifiedCashierUrlRequest createUnifiedCashierUrlRequest)
      throws Exception {

    QuerySignKeyRequest querySignKeyRequest=new QuerySignKeyRequest();
    querySignKeyRequest.setPartnerId(Long.parseLong(createUnifiedCashierUrlRequest.getPartnerId()));
    querySignKeyRequest.setUserNo(createUnifiedCashierUrlRequest.getMchId());
    String key=signKeyServiceClient.getSignKey(querySignKeyRequest);
    return SignService.buildSign(createUnifiedCashierUrlRequest,key,null);
  }


}