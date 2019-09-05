package com.jerry.proxy.jdkproxy;

import com.jerry.proxy.interfaces.Fruit;

import java.lang.reflect.Proxy;

public class MainMethod {

  public static void main(String[] args) {
    // 可以生成proxy的class文件
    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    //
    Fruit fruit = new Apple();
    ProxyHandler proxyHandler = new ProxyHandler(fruit);
    Fruit f =
        (Fruit)
            Proxy.newProxyInstance(
                fruit.getClass().getClassLoader(), fruit.getClass().getInterfaces(), proxyHandler);
    f.color();
  }
}
