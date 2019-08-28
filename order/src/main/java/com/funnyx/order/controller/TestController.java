package com.funnyx.order.controller;

import com.alibaba.fastjson.JSON;
import com.funnyx.oauth.response.BasicResponse;
import com.funnyx.oauth.service.ElasticsearchCommonService;
import com.funnyx.order.entity.TestObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class TestController {

  @Autowired private ElasticsearchCommonService elasticsearchCommonService;

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
}
