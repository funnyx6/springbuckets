package com.jerry.springcore.starter;

import com.jerry.springcore.config.MainConfig;
import com.jerry.springcore.entity.Foo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainStarter {

  public static void main(String[] args) {

    /*
       初始化IOC容器，这里使用注解的形式
       也可以用XML的形式，例如：
       ApplicationContext context = new ClassPathXmlApplicationContext("location");
       context.getBean("");
       ...

       CustomizeAnnotationConfigApplicationContext context = new CustomizeAnnotationConfigApplicationContext("classpath:application-bean.xml");
       Order foo = context.getBean("order", Order.class);
       这里有一个钩子方法，postProcessBeanFactory() 交由子类来自行实现，在这里可以进行一些对Bean的修改，以及一些重要参数的设置。
    */

    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(MainConfig.class);

    // 从IOC容器获取Bean
    Foo foo = context.getBean("foo", Foo.class);
    //    Eoo eoo = context.getBean("eoo", Eoo.class);
    System.out.println(foo.toString());
  }
}
