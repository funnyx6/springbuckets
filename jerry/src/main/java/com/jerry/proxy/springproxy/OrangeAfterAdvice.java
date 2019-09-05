package com.jerry.proxy.springproxy;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class OrangeAfterAdvice implements AfterReturningAdvice {
  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
      throws Throwable {
    System.out.println("Pick a bigger one again!");
  }
}
