/**
 * Youzan.com Inc.
 * Copyright (c) 2012-2016 All Rights Reserved.
 */

import com.youzan.pay.unified.cashier.dal.dao.PayStrategyDetailInfoDao;
import com.youzan.pay.unified.cashier.dal.dataobject.PayStrategyDo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wulonghui
 * @version testDao.java, v 0.1 2017-08-05 15:16
 */
public class testDao {

  public static void main(String[] args) {
    ApplicationContext
        ctx =
        new ClassPathXmlApplicationContext("classpath:META-INF/spring/spring-dao-entry.xml");

    PayStrategyDetailInfoDao payStrategyDetailInfoDao=
        (PayStrategyDetailInfoDao) ctx.getBean("payStrategyDetailInfoDao");

    PayStrategyDo payStrategyDo=payStrategyDetailInfoDao.selectByBizStrategyAndBizCode("PAY", "1001");

    System.out.println(payStrategyDo.getPayChannel());
  }
}
