package com.funnyx.oauth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

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

  public void hset(String key, String field, String value) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.hset(key, field, value);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
  }

  public void hset(String key, Map<String, String> map) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.hset(key, map);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
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

  /**
   * 是否存在key
   *
   * @param key 键
   * @return true if the key exists, otherwise false
   */
  public boolean existsKey(String key) {
    Jedis jedis = null;
    boolean value = false;
    try {
      jedis = jedisPool.getResource();
      value = jedis.exists(key);
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
   * 将给定元素放入队列，然后返回队列当前包含的元素数量作为结果。
   *
   * @param key
   * @param item
   */
  public long enqueue(String key, String item) {
    Jedis jedis = null;
    long value = 0;
    try {
      jedis = jedisPool.getResource();
      value = jedis.lpush(key, item);
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
   * 判断这个元素是否在集合中
   *
   * @param key
   * @param item
   */
  public boolean sismember(String key, String item) {
    Jedis jedis = null;
    boolean value = false;
    try {
      jedis = jedisPool.getResource();
      value = jedis.sismember(key, item);
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
   * 将指定的元素添加到集合中
   *
   * @param key
   * @param member
   */
  public void sadd(String key, String member) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.sadd(key, member);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (jedis != null) {
        jedis.close(); // return resource to JedisPool
      }
    }
  }

  /**
   * 1 if the new element was removed 0 if the new element was not a member of the set
   *
   * @param key
   * @param member
   * @return
   */
  public long srem(String key, String member) {
    Jedis jedis = null;
    long value = 0L;
    try {
      jedis = jedisPool.getResource();
      value = jedis.srem(key, member);
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
   * 移除并返回队列目前入队时间最长的元素。
   *
   * @param key
   */
  public String dequeue(String key) {
    Jedis jedis = null;
    String value = "";
    try {
      jedis = jedisPool.getResource();
      value = jedis.rpop(key);
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
