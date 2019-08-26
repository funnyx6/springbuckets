package com.funnyx.user.controller;

import com.funnyx.oauth.annotation.CheckAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class TestController {

  @Autowired private RestTemplate restTemplate;

  @Autowired private DiscoveryClient discoveryClient;

  @GetMapping(value = "/test")
  @CheckAuthorization
  public String testGateway() {
    return "This Request comes from Gateway!";
  }

  @GetMapping(value = "/testLoadBalance")
  public void loadBalance() {
    List<ServiceInstance> instances = discoveryClient.getInstances("registry-service");
  }
}
