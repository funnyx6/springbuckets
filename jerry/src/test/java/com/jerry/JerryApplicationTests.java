package com.jerry;

import com.jerry.proxy.interfaces.Fruit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JerryApplicationTests {

  @Autowired private ProxyFactoryBean factoryBean;

  @Test
  public void springAOPTest() {
    Fruit fruit = (Fruit) factoryBean.getObject();
    fruit.color();
  }
}
