package com.jerry.proxy.springproxy;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class OrangeBeforeAdvice implements MethodBeforeAdvice {

  @Override
  public void before(Method method, Object[] args, Object target) throws Throwable {
    System.out.println("Pick a big one!");
  }
}
