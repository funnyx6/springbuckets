package com.funnyx.order.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service")
public interface OrderFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/api/user/testFeign")
  String testFeign();
}
