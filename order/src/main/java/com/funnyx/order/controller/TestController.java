package com.funnyx.order.controller;

import com.alibaba.fastjson.JSON;
import com.funnyx.oauth.response.BasicResponse;
import com.funnyx.oauth.service.ElasticsearchCommonService;
import com.funnyx.oauth.util.JedisUtil;
import com.funnyx.oauth.util.RedissonUtil;
import com.funnyx.order.entity.Order;
import com.funnyx.order.entity.TestObject;
import com.funnyx.order.feignclient.OrderFeignClient;
import com.funnyx.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class TestController {

  @Autowired private ElasticsearchCommonService elasticsearchCommonService;

  @Autowired private OrderFeignClient orderFeignClient;

  @Autowired private RedissonUtil redissonUtil;

  @Autowired private OrderService orderService;

  @Autowired private JedisUtil jedisUtil;

  @GetMapping(value = "/api/order/test")
  public String test() throws Exception {
    return elasticsearchCommonService.get("index4", "1");
  }

  @GetMapping(value = "/api/order/testObject")
  public BasicResponse<TestObject> testObject() throws Exception {
    TestObject testObject = elasticsearchCommonService.getObject("index4", "1", TestObject.class);
    return new BasicResponse<>(testObject);
  }

  @GetMapping(value = "/api/order/testIndex")
  public void testIndexObject() throws Exception {
    TestObject testObject = new TestObject();
    testObject.setContent("Hello");
    testObject.setName("Jacky");
    String jsonString = JSON.toJSONString(testObject);
    elasticsearchCommonService.index("index4", jsonString);
  }

  @GetMapping(value = "/api/order/testCount")
  public BasicResponse<Long> testCount() throws Exception {
    Long count = elasticsearchCommonService.count("index4");
    return new BasicResponse<>(count);
  }

  @GetMapping(value = "/api/order/testSearch")
  public BasicResponse<List<TestObject>> testSearch() throws Exception {
    List<TestObject> list = elasticsearchCommonService.searchAll("index4", TestObject.class);
    return new BasicResponse<>(list);
  }

  @GetMapping(value = "/api/order/testFeign")
  public String testFeign() {
    return orderFeignClient.testFeign();
  }

  @GetMapping(value = "/api/order/testRedissonGet")
  public String testRedissonGet() {
    String hello = (String) redissonUtil.get("hello1");
    return hello;
  }

  @GetMapping(value = "/api/order/testRedissonSet")
  public void testRedissonSet() {
    redissonUtil.set("hello1", "world1");
  }

  @GetMapping(value = "/api/order/testGetMap")
  public String testRedissonGetMap() {
    TestObject hget = (TestObject) redissonUtil.hget("content:map1", 123);
    return hget.getContent();
  }

  @GetMapping(value = "/api/order/testSetMap")
  public void testRedissonSetMap() {
    TestObject testObject = new TestObject();
    testObject.setName("一篇有实力的文章");
    testObject.setContent("Java,Golang,Javascript");
    redissonUtil.hset("content:map", 123, testObject);
  }

  @GetMapping(value = "/api/order/saveOrder")
  public BasicResponse<Order> saveOrder(Order order) {
    Order save = orderService.save(order);
    return new BasicResponse<>(save);
  }

  @GetMapping(value = "/api/order/{id}")
  public BasicResponse<Order> getOrderWithId(@PathVariable Long id) {
    Optional<Order> order = orderService.findById(id);
    if (order.isPresent()) {
      return new BasicResponse<>(order.get());
    } else return new BasicResponse<>(5000, "对应的ID没有数据");
  }

  @GetMapping(value = "/api/order/testJedis")
  public Long testJedis() {
    return jedisUtil.increase("number");
  }
}
