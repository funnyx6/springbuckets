package com.jerry.springcore.config;

import com.jerry.springcore.entity.Eoo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// 这里用自定义的类去实现了BeanPostProcessor
// 对于这两个方法分别进行实现，因为在BeanPostProcessor
// 自定义实现的类以Bean的形式注册到IOC中，然后这个Processor会在其他相关的Bean实例化之后开始调用。
// 例如这里的Eoo实例，在IOC调用了其构造器之后，开始调用了postProcessBeforeInitialization()方法。
// 在调用了Eoo实例的初始化方法之后，调用了postProcessAfterInitialization()方法。
// 这个就是Spring对外提供的接口之一，还有BeanFactoryPostProcessor这个接口，这个接口的调用机制是在
// BeanDefinition加载完成，Bean的实例化之前进行。
// 在这里都可以对实例化的Bean进行一些修改。
public class CustomizeBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    Eoo eoo = null;
    if (bean instanceof Eoo) {
      System.out.println("Before BeanPostProcessor...");
      eoo = (Eoo) bean;
      eoo.setUserName("Jacky");
    }
    return eoo;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Eoo eoo = null;
    if (bean instanceof Eoo) {
      System.out.println("After BeanPostProcessor...");
      eoo = (Eoo) bean;
      eoo.setUserName("Jacky");
    }
    return eoo;
  }
}
