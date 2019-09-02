package com.funnyx.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class JedisConfig {

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Bean
  public JedisPool redisPoolFactory() {
    log.info("JedisPool Init Success...");
    JedisPoolConfig config = new JedisPoolConfig();
    // config 使用默认配置
    JedisPool jedisPool = new JedisPool(config, host);
    return jedisPool;
  }
}
