package com.service.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.order.entity.Order;
import com.service.order.model.User;
import com.service.order.repository.OrderRepository;
import com.service.order.service.UserServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserServiceHandlerImpl implements UserServiceHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "user-service-event")
    public void consume(String userStr) {
        try {
            User user = OBJECT_MAPPER.readValue(userStr, User.class);
            updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        List<Order> userOrders = orderRepository.findByUserId(user.getId());
        userOrders.forEach(p -> p.setUser(user));
        orderRepository.saveAll(userOrders);
    }
}
