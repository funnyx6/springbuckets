package com.jerry;

import com.jerry.proxy.interfaces.Fruit;
import com.jerry.proxy.springproxy.config.ProxyConfiguration;
import com.jerry.springtransaction.jdbctemplate.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JerryApplicationTests {

  @Autowired private ProxyFactoryBean factoryBean;
  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  public void springAOPTest() {
    Fruit fruit = (Fruit) factoryBean.getObject();
    fruit.color();
  }

  @Test
  public void jdbcTemplateTest() {

    Order order =
        jdbcTemplate.queryForObject("select * from t_order where id = 1", new OrderRowMapper());
    System.out.println(order);
  }

  private class OrderRowMapper
      implements RowMapper<com.jerry.springtransaction.jdbctemplate.Order> {
    @Override
    public com.jerry.springtransaction.jdbctemplate.Order mapRow(ResultSet rs, int rowNum)
        throws SQLException {
      Order order = new Order();
      order.setId(rs.getLong("id"));
      order.setOrderNum(rs.getString("orderNum"));
      return order;
    }
  }

  @Test
  public void applicationContext() {
    ApplicationContext context = new AnnotationConfigApplicationContext(ProxyConfiguration.class);
    Fruit fruit = (Fruit) context.getBean("fruit");
    fruit.color();
  }

  @Test
  public void applicationContextFromXML() {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:application-bean.xml");
    Order order = (Order) context.getBean("order");
    System.out.println(order);

    BeanFactory factory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader =
        new XmlBeanDefinitionReader((DefaultListableBeanFactory) factory);
    int i = reader.loadBeanDefinitions("classpath:application-bean.xml");
    System.out.println(i);
    Order order1 = (Order) factory.getBean("order");
    System.out.println(order1);
  }
}
