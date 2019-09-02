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

  /**
   * 通用根据key获取value
   *
   * @param key 键
   * @param indexDB 数据库
   */
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

  /**
   * 通用根据key获取value，默认数据库为0
   *
   * @param key 键
   */
  public String get(String key) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.get(key);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
    return value;
  }

  /**
   * 通过key 和 field 获取指定的 value
   *
   * @param key
   * @param field
   */
  public String hget(String key, String field) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.hget(key, field);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
    return value;
  }

  /**
   * Increment the number stored at key by one
   *
   * @param key
   */
  public Long increase(String key) {
    Jedis jedis = null;
    Long value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.incr(key);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
    return value;
  }

  /**
   * HyperlogLog 计算基数
   *
   * @param key 键
   */
  public Long pdcount(String key) {
    Jedis jedis = null;
    Long value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.pfcount(key);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
    return value;
  }

  /**
   * HyperlogLog 添加元素
   *
   * @param key 键
   * @param values
   */
  public Long pfadd(String key, String[] values) {
    Jedis jedis = null;
    Long value = null;
    try {
      jedis = jedisPool.getResource();
      value = jedis.pfadd(key, values);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
    return value;
  }
}
