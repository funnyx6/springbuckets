package com.funnyx.order;

import com.funnyx.oauth.util.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {

  @Autowired JedisUtil jedisUtil;

  @Test
  public void contextLoads() {}

  @Test
  public void saveProduct() {
    // 将商品先加入到redis中，比如key为product，然后value假设为商品的ID
    for (int i = 1; i <= 10; i++) {
      jedisUtil.sadd("product", String.valueOf(i));
    }
  }
}
