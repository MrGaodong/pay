package com.youzan.pay.unified.cashier.core.utils.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author tao.ke Date: 2017/6/24 Time: 下午10:46
 */
public class MyCollector {

  /**
   * 将对象list列表转换成以对象中某个属性为key，以对象中某个属性为value
   *
   * <code>
   * TypeA b1 = new TypeA("jiangxi",1001);
   * TypeA b2 = new TypeA("zhejiang",1002);
   * TypeA b3 = new TypeA("beijing",1003);
   * List<TypeB> list = Lists.newArrayList(b1,b2,b3);
   * list.stream().collect(listToMap((entry) -> entry.getCode(),(entry) -> entry.getAddress()));
   * </code>
   *
   * @param keyFun   提取key的方法
   * @param valueFun 提取value的方法
   * @param <T>      输入的类型
   * @param <K>      返回的key的类型
   * @param <V>      返回的value的类型
   * @return jdk8 collect方法需要的收集器
   */
  public static <T, K, V> Collector<T, ?, Map<K, V>> listToMap(
      Function<? super T, ? extends K> keyFun, Function<? super T, ? extends V> valueFun) {

    Supplier<Map<K, V>> supplier = HashMap::new;
    BiConsumer<Map<K, V>, T> accumulator = (m, t) -> {
      m.put(keyFun.apply(t), valueFun.apply(t));
    };

    BinaryOperator<Map<K, V>> combiner = (left, right) -> {
      left.putAll(right);
      return left;
    };

    return Collector.of(supplier, accumulator, combiner, Collector.Characteristics.UNORDERED);
  }

}
