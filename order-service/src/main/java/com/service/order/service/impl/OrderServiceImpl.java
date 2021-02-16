package com.service.order.service.impl;

import com.service.order.entity.Order;
import com.service.order.repository.OrderRepository;
import com.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }
}
