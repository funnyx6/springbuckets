package com.jerry.proxy.jdkproxy;

import com.jerry.proxy.interfaces.Fruit;

public class Apple implements Fruit {

  @Override
  public void color() {
    System.out.println("This apple's color is red, It looks good!");
  }
}
