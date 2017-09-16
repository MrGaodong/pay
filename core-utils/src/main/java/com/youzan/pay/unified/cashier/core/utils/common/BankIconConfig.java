package com.youzan.pay.unified.cashier.core.utils.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 银行code和对应icon的映射配置
 *
 * @author tao.ke Date: 2017/6/22 Time: 下午4:50
 */
public class BankIconConfig {

  private static final Map<String, String> BANK_ICON_CONFIG = new HashMap<>();

  private static final String DEFAULT_BANK_ICON = "";

  static {

    BANK_ICON_CONFIG.put("ABC", "https://img.yzcdn.cn/public_files/2017/06/21/abbe0a4ee9d868d5520af23e73df70d8.png");
    BANK_ICON_CONFIG.put("BOC", "https://img.yzcdn.cn/public_files/2017/06/21/c1c72a105420a2006458d970c753dc86.png");
    BANK_ICON_CONFIG.put("CCB", "https://img.yzcdn.cn/public_files/2017/06/21/72be1db0fc6f1b8b8dda4c59c2471d34.png");
    BANK_ICON_CONFIG.put("CEB", "https://img.yzcdn.cn/public_files/2017/06/21/e165e9538d92f3da7d346b6b74cb99af.png");
    BANK_ICON_CONFIG.put("CIB", "https://img.yzcdn.cn/public_files/2017/06/21/81f01033e8678e2a239812878aee8827.png");
    BANK_ICON_CONFIG.put("ECITIC", "https://img.yzcdn.cn/public_files/2017/06/21/0b511a871405de4f87ae5a14b195c908.png");
    BANK_ICON_CONFIG.put("CMBCHINA", "https://img.yzcdn.cn/public_files/2017/06/21/25decaf1ccbd43438897f1de6331fa02.png");
    BANK_ICON_CONFIG.put("CMBC", "https://img.yzcdn.cn/public_files/2017/06/21/18b1566bde4780c5084382dd02dd7d50.png");
    BANK_ICON_CONFIG.put("COMM", "https://img.yzcdn.cn/public_files/2017/06/21/7898c558a7abc60027e04e9576a9f9ee.png");
    BANK_ICON_CONFIG.put("GDB", "https://img.yzcdn.cn/public_files/2017/06/21/33b790a03e4bdf53749bf7e1d108fdd0.png");
    BANK_ICON_CONFIG.put("HXB", "https://img.yzcdn.cn/public_files/2017/06/21/a42797d2134993be0ee8eb18d4fb0421.png");
    BANK_ICON_CONFIG.put("HZCB", "https://img.yzcdn.cn/public_files/2017/06/21/06546688683a7b7c4542b22aade6fb27.png\n");
    BANK_ICON_CONFIG.put("ICBC", "https://img.yzcdn.cn/public_files/2017/06/21/ccd5fb4fd7aa511e44d202c6d5620cde.png");
    BANK_ICON_CONFIG.put("NBB", "https://img.yzcdn.cn/public_files/2017/06/21/a026e24141bded6071c7e5f1e08bca37.png");
    BANK_ICON_CONFIG.put("POST", "https://img.yzcdn.cn/public_files/2017/06/21/ec5c998140b5e5e5e75702e499f59494.png");
    BANK_ICON_CONFIG.put("SHB", "https://img.yzcdn.cn/public_files/2017/06/21/bff9019f82660c13f58f5039f46b263e.png");
    BANK_ICON_CONFIG.put("PINGAN", "https://img.yzcdn.cn/public_files/2017/06/21/fa3c4dc1b0462ceee243e8796ad01032.png");
    BANK_ICON_CONFIG.put("SPDB", "https://img.yzcdn.cn/public_files/2017/06/21/f96f421e56d55e929984dbfefa4f6673.png");
    BANK_ICON_CONFIG.put("BCCB","https://img.yzcdn.cn/public_files/2017/06/27/7d444b891077f381628725b9259c7457.png");
  }

  /**
   * 获取bankCode指定的对应iconurl地址
   */
  public static String getIconUrl(String bankCode) {

    String icon = BANK_ICON_CONFIG.get(bankCode);
    if (icon == null) {
      return DEFAULT_BANK_ICON;
    }
    return icon;
  }

}
