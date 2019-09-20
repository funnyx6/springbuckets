package com.jerry.springcore.config;

import com.jerry.springcore.entity.Eoo;
import com.jerry.springcore.entity.Foo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

  @Bean
  public Foo foo() {
    Foo foo = new Foo();
    foo.setName("Tonny");
    foo.setJob("Engineer");
    return foo;
  }

  @Bean(initMethod = "init")
  public Eoo eoo() {
    Eoo eoo = new Eoo();
    eoo.setNum(1);
    eoo.setUserName("Tommy");
    return eoo;
  }

  @Bean
  public CustomizeBeanPostProcessor customizeBeanPostProcessor() {
    return new CustomizeBeanPostProcessor();
  }
}
