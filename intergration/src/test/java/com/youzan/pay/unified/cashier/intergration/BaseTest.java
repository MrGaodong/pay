package com.youzan.pay.unified.cashier.intergration;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tao.ke Date: 2017/6/27 Time: 下午4:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
public abstract class BaseTest {

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

}