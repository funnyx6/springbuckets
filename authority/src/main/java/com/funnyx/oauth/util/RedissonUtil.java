package com.funnyx.oauth.util;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedissonUtil {

  @Autowired private RedissonClient redissonClient;

  /**
   * 通用get方法
   *
   * @param key 键
   */
  public Object get(String key) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    return bucket.get();
  }

  /**
   * 通用set方法
   *
   * @param key 键
   * @param value 值
   */
  public void set(String key, Object value) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    bucket.set(value);
  }

  /**
   * hash方式存放数据
   *
   * @param key
   * @param field
   */
  public void hset(String key, Object field, Object value) {
    RMap<Object, Object> map = redissonClient.getMap(key);
    map.put(field, value);
  }

  /**
   * hash方式读取数据
   *
   * @param key
   * @param field
   */
  public Object hget(String key, Object field) {
    RMap<Object, Object> map = redissonClient.getMap(key);
    return map.get(field);
  }

  /**
   * 读取本地缓存数据 这类映射的使用主要用于在特定的场景下，映射缓存（MapCache）上的高度频繁的读取操作
   *
   * @param key
   * @param field
   */
  public Object hCacheGet(String key, Object field) {
    RLocalCachedMap<Object, Object> localCachedMap =
        redissonClient.getLocalCachedMap(key, LocalCachedMapOptions.defaults());
    return localCachedMap.get(field);
  }

  /**
   * 保存list数据
   *
   * @param key
   * @param value
   */
  public void setList(String key, Object value) {
    RList<Object> list = redissonClient.getList(key);
    list.add(value);
  }

  /**
   * 获取所有list数据
   *
   * @param key
   */
  public List<Object> getAllList(String key) {
    RList<Object> list = redissonClient.getList(key);
    return list.readAll();
  }

  /**
   * 获取范围内的list数据
   *
   * @param key
   * @param toIndex index的默认值从0开始，如果是传-1，表示最后一个数据。
   */
  public List<Object> getListWithIndex(String key, int toIndex) {
    RList<Object> list = redissonClient.getList(key);
    return list.range(toIndex);
  }

  /**
   * 获取锁
   *
   * @param param
   */
  public RLock getLock(String param) {
    return redissonClient.getLock(param);
  }

  /**
   * 使用布隆过滤器判断一个key是否存在
   *
   * @param name 布隆过滤器名称
   * @param key 键
   */
  public boolean containsWithBloomFilter(String name, String key) {
    RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(name);
    return bloomFilter.contains(key);
  }
}
