package com.jerry.springcore.starter;

import com.jerry.springcore.config.MainConfig;
import com.jerry.springcore.entity.Foo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainStarter {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(MainConfig.class);

    Foo foo = context.getBean("foo", Foo.class);
    System.out.println(foo.toString());
  }
}
