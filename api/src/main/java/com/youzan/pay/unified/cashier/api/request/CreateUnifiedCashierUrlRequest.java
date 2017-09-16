package com.youzan.pay.unified.cashier.api.request;

import com.youzan.pay.unified.cashier.api.annotation.SkipSign;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: wulonghui
 * Date: 2017-08-30
 * Time: 下午3:59
 * <p>生成普通收银台链接请求类</p>
 */
@Data
public class CreateUnifiedCashierUrlRequest implements Serializable{

  @SkipSign
  private static final long serialVersionUID = -9210493009972354083L;

  /**
   * 支付行为，默认为"PAY"
   */
  @SkipSign
  private String bizAction="PAY";

  /**
   * 默认为1
   */
  @SkipSign
  private Integer bizProd=1;

  /**
   *签名类型，默认为md5
   */
  @SkipSign
  private String signType="MD5";

  /**
   * 货币类型,默认为CNY
   */
  @SkipSign
  private String currencyCode="CNY";

  /**
   * 参与折扣金额
   */
  @SkipSign
  private Integer discountableAmount=0;

  /**
   * 不参与折扣金额
   */
  @SkipSign
  private Integer undiscountableAmount=0;

  /**
   * 资产信息
   */
  @SkipSign
  private String memo="";

  /**
   * 结算模式，1为担保交易，2为立即结算
   */
  @SkipSign
  private Integer bizMode=2;

  /**
   * app类型
   */
  @SkipSign
  private String appType="WAP";

  /**
   * 交易描述
   */
  @SkipSign
  private String tradeDesc="实时交易";

  /**
   * 商品描述
   */
  @SkipSign
  private String goodsDesc="实时交易";

  /**
   * 支付金额
   */
  @Min(value = 1,message = "支付金额不能小于0")
  private long payAmount;

  /**
   * 合作方信息
   */
  @NotNull(message = "合作方信息不能为空")
  private String partnerId;

  /**
   * 外部订单号
   */
  @NotNull(message = "外部订单号不能为空")
  private String outBizNo;


  /**
   * 子商户类型
   */
  @NotNull(message = "子商户不能为空")
  @Pattern(regexp = "[0-9]{1,19}", message = "收款方帐号非法")
  private long mchId;

  /**
   * 商户名称
   */
  @NotNull(message = "商户名称不能为空")
  private String mchName;

  /**
   * 商品名称
   */
  @NotNull(message = "商品名称不能为空")
  private String goodsName;


  /**
   * 异步回调地址
   */
  private String partnerNotifyUrl;

  /**
   * 商家同步回调地址
   */
  private String returnUrl;


  /**
   * 商家电话号码
   */
  private String tel;

  /**
   * 是否跳转有赞成功页标志
   */
  private Integer isNeedSuccessJump=0;

  //支付宝或微信一种支付方式的时候路由选择收银台页面
  private Integer isNeedPopupView=0;

  //商户自定义图片
  private String imageUrl;

  //收银台窗口滑动文字
  private String slideText;

}