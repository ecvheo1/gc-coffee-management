package com.example.gccoffeemanagement.order.repository;

import com.example.gccoffeemanagement.order.domain.Order;

public interface OrderRepository {

    void save(Order order);
}
