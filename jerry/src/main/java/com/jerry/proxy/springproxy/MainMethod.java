package com.jerry.proxy.springproxy;

import com.jerry.proxy.interfaces.Fruit;
import org.springframework.aop.framework.ProxyFactory;

public class MainMethod {

  public static void main(String[] args) {
    // 被代理对象
    Fruit fruit = new Orange();

    // Spring提供的前置增强
    OrangeBeforeAdvice advice = new OrangeBeforeAdvice();

    // Spring提供的代理工厂类
    ProxyFactory factory = new ProxyFactory(fruit);
    factory.addAdvice(advice);
    Fruit proxy = (Fruit) factory.getProxy();
    proxy.color();
  }
}
