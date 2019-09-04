package com.jerry.proxy.springproxy.config;

import com.jerry.proxy.interfaces.Fruit;
import com.jerry.proxy.springproxy.Orange;
import com.jerry.proxy.springproxy.OrangeBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfiguration {

  @Bean
  public ProxyFactoryBean factoryBean() throws ClassNotFoundException {
    ProxyFactoryBean factoryBean = new ProxyFactoryBean();
    factoryBean.setProxyInterfaces(Fruit.class.getInterfaces());
    factoryBean.setInterceptorNames("orangeBeforeAdvice");
    factoryBean.setTarget(orange());
    return factoryBean;
  }

  @Bean
  public Orange orange() {
    return new Orange();
  }

  @Bean
  public OrangeBeforeAdvice orangeBeforeAdvice() {
    return new OrangeBeforeAdvice();
  }
}
