/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils;

import com.youzan.pay.unified.cashier.core.utils.enums.CorrectionLevel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import org.apache.commons.collections.MapUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author twb
 * @version QRCodeGenerator.java, v 0.1 2017-05-24 15:32
 */
public class QRCodeGenerator {

  private final static String QUESTION_SYMBOL = "?";
  private final static String AND_SYMBOL = "&";
  private final static String EQUAL_SYMBOL = "=";
  private final static String DEFAULT_FORMAT = "png";
  private final static BarcodeFormat DEFAULT_BARCODE_FORMAT = BarcodeFormat.QR_CODE;
  private final static int DEFAULT_LEVEL_CODE = 1;
  private final static int DEFAULT_WIDTH = 300;
  private final static int DEFAULT_HEIGHT = 300;

  public static String packageURI(String domain, Map<String, String> params) {
    if (domain == null || domain.trim().length() == 0) {
      throw new IllegalArgumentException();
    }
    if (MapUtils.isEmpty(params)) {
      return domain;
    }
    StringBuilder sb = new StringBuilder(domain);
    sb.append(QUESTION_SYMBOL);
    for (Map.Entry<String, String> param : params.entrySet()) {
      String key = param.getKey();
      String value = param.getValue();
      checkParam(key, value);
      sb.append(key).append(EQUAL_SYMBOL).append(value).append(AND_SYMBOL);
    }
    return deleteLastChar(sb.toString());
  }

  private static void checkParam(String... params) {
    if (params != null && params.length > 0) {
      for (String param : params) {
        if (param == null || param.trim().length() == 0) {
          throw new IllegalArgumentException();
        }
      }
    }
  }

  private static String deleteLastChar(String value) {
    checkParam(value);
    int length = value.length();
    value = value.substring(0, length - 1);
    return value;
  }

  public static String encode(String content) {
    return encode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_LEVEL_CODE);
  }

  public static String encode(String content, int width, int height) {
    return encode(content, width, height, DEFAULT_LEVEL_CODE);
  }

  /**
   * BASE64编码
   *
   * @param content   内容（url）
   * @param width     宽度
   * @param height    高度
   * @param levelCode 错误校正级别
   * @return
   */
  public static String encode(String content, int width, int height, int levelCode) {
    Hashtable<Object, Object> hints = new Hashtable<>();
    hints.put(EncodeHintType.ERROR_CORRECTION,
              CorrectionLevel.getLevelByCode(levelCode));
    try {
      BitMatrix bitMatrix = new MultiFormatWriter()
          .encode(content, DEFAULT_BARCODE_FORMAT, width, height);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, baos);

      BASE64Encoder encoder = new BASE64Encoder();

      String beforeReplace = encoder.encode(baos.toByteArray());

//      String afterReplace = "";
//      if (beforeReplace != null) {
//        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//        Matcher m = p.matcher(beforeReplace);
//        afterReplace = m.replaceAll("");
//      }
      return beforeReplace;
    } catch (WriterException | IOException e) {
      throw new IllegalStateException();
    }
  }

  /**
   *
   * @param path 创建文件的目录
   * @param fileName 文件名
   * @param contentOfBase64 文件内容，BASE64编码
   * @throws IOException
   */
  public static void createImage(String path, String fileName, String contentOfBase64) throws IOException {
    BASE64Decoder decoder = new BASE64Decoder();
    FileOutputStream outputStream = null;
    try  {
      File file = new File(path + File.separator + fileName);
      outputStream = new FileOutputStream(file);
      byte[] bytes = decoder.decodeBuffer(contentOfBase64);
      outputStream.write(bytes);
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String zfb = "https://cashier-qa.youzan.com/pay/qRCodeRecharge?imageNo=820000000068R089990899568779660&createTime=1498740138129";
    String wx = "https://cashier.youzan.com/pay/qRCodeRecharge?imageNo=820000000068R089990899568779660&createTime=1498740138129";
    String zfb2 = encode(zfb);
    String wx2 = encode(wx);
    createImage("/Users/twb", "zfb.png", zfb2);
    createImage("/Users/twb", "wx.png", wx2);
  }
}
