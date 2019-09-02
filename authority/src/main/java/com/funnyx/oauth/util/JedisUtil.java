package com.funnyx.oauth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Slf4j
public class JedisUtil {

  @Autowired private JedisPool jedisPool;

  public String get(String key, int indexDB) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = jedisPool.getResource();
      jedis.select(indexDB);
      value = jedis.get(key);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
    return value;
  }
}
