package com.jerry.proxy.springproxy.config;

import com.jerry.proxy.interfaces.Fruit;
import com.jerry.proxy.springproxy.Orange;
import com.jerry.proxy.springproxy.OrangeBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring提供的动态代理的方式，这里以配置的形式来实现动态代理，主要是由这个ProxyFactoryBean代理工厂实现
 *
 * @see org.springframework.aop.framework.AopProxy
 * @see org.springframework.aop.framework.AopProxyFactory
 * @see org.springframework.aop.framework.ProxyFactory
 * @see ProxyFactoryBean
 *     <p>这里最基础的接口就是AopProxy，它的实现有两种，一个是Java的动态代理，另一个就是CGlib
 */
@Configuration
public class ProxyConfiguration {

  @Bean
  public ProxyFactoryBean factoryBean(Fruit fruit) throws ClassNotFoundException {
    ProxyFactoryBean factoryBean = new ProxyFactoryBean();
    factoryBean.setProxyInterfaces(Fruit.class.getInterfaces());
    factoryBean.setInterceptorNames("orangeBeforeAdvice");
    factoryBean.setTarget(fruit);
    return factoryBean;
  }

  @Bean
  public Fruit fruit() {
    return new Orange();
  }

  @Bean
  public OrangeBeforeAdvice orangeBeforeAdvice() {
    return new OrangeBeforeAdvice();
  }
}
