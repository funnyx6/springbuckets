package com.funnyx.order.service;

import com.funnyx.order.entity.Order;
import com.funnyx.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

  @Autowired private OrderRepository orderRepository;

  public Order save(Order order) {
    return orderRepository.save(order);
  }

  public Optional<Order> findById(Long id) {
    return orderRepository.findById(id);
  }
}
