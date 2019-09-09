package com.jerry;

import com.jerry.proxy.interfaces.Fruit;
import com.jerry.springtransaction.jdbctemplate.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}
