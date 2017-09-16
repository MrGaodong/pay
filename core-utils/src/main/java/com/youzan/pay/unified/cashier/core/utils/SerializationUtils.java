/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.youzan.pay.unified.cashier.core.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 序列化工具
 *
 * @author twb
 * @version SerializationUtils.java, v 0.1 2017-05-17 20:04
 */
public class SerializationUtils {

  public static <T> byte[] toBytes(T object, Class<T> clazz) {
    RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
    byte[] bytes = ProtostuffIOUtil.toByteArray(object,
                                                schema,
                                                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    return bytes;
  }

  public static <T> T toObject(byte[] bytes, Class<T> clazz) {
    RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
    T object = schema.newMessage();
    ProtostuffIOUtil.mergeFrom(bytes, object, schema);
    return object;
  }
}
