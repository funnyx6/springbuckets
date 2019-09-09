package com.jerry.springtransaction.configuration;

import com.jerry.springtransaction.jdbctemplate.JdbcTemplateDaoSupport;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class JpaTransactionConfig {

  @Bean
  public DataSource dataSource() throws SQLException {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setUsername("root");
    dataSource.setPassword("123456");
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/localdb");
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() throws SQLException {
    JdbcTemplateDaoSupport daoSupport = new JdbcTemplateDaoSupport();
    daoSupport.setDataSource(dataSource());
    return daoSupport.getJdbcTemplate();
  }
}
