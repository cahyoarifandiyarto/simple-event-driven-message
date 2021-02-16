package com.service.order.service;

import com.service.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();
    void createOrder(Order order);
}
