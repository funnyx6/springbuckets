package com.jerry.springcore.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Eoo {

  private int num;

  private String userName;

  public Eoo() {
    System.out.println("Eoo is Instantiated...");
  }

  public void init() {
    System.out.println("Eoo is init...");
  }
}
