package com.service.order.entity;

import com.service.order.model.Product;
import com.service.order.model.User;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Order {
    private String id;
    private User user;
    private Product product;
    private double price;
}
