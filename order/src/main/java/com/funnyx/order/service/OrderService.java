package com.funnyx.order.service;

import com.funnyx.order.entity.Order;
import com.funnyx.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

  @Autowired private OrderRepository orderRepository;

  public Order save(Order order) {
    return orderRepository.save(order);
  }

  // 添加方法级别注解，如果是类级别注解，则只对public的方法有效。
  @Cacheable(cacheNames = "orders")
  public Optional<Order> findById(Long id) {
    return orderRepository.findById(id);
  }
}
