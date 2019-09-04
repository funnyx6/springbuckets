package com.jerry.proxy.springproxy;

import com.jerry.proxy.interfaces.Fruit;

public class Orange implements Fruit {

  @Override
  public void color() {
    System.out.println("It tastes good!");
  }
}
