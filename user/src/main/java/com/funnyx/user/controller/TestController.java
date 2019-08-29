package com.funnyx.user.controller;

import com.funnyx.oauth.annotation.CheckAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class TestController {

  @Autowired private DiscoveryClient discoveryClient;

  @GetMapping(value = "/test")
  @CheckAuthorization
  public String testGateway() {
    return "This Request comes from Gateway!";
  }

  @GetMapping(value = "/testFeign")
  public String testFeignInvoke() {
    return "This Request Comes From Other Service!";
  }

  @GetMapping(value = "/testLoadBalance")
  public void loadBalance() {
    List<ServiceInstance> instances = discoveryClient.getInstances("registry-service");
  }
}
