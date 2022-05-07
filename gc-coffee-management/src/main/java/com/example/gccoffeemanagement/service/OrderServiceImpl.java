package com.example.gccoffeemanagement.service;

import com.example.gccoffeemanagement.domain.Order;
import com.example.gccoffeemanagement.exception.ErrorCode;
import com.example.gccoffeemanagement.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        if (order == null) {
            throw new IllegalStateException(ErrorCode.NULL_INPUT_OBJECT.getMessage());
        }

        orderRepository.save(order);
    }
}
