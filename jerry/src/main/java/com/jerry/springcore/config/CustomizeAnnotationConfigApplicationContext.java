package com.jerry.springcore.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomizeAnnotationConfigApplicationContext extends ClassPathXmlApplicationContext {

  public CustomizeAnnotationConfigApplicationContext(String s) {
    super(s);
  }

  @Override
  protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    BeanDefinition mainConfig = beanFactory.getBeanDefinition("order");
    mainConfig.getPropertyValues().addPropertyValue("orderNum", "111");
  }
}
