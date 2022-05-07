package com.example.gccoffeemanagement.order.service;

import com.example.gccoffeemanagement.order.domain.Order;
import com.example.gccoffeemanagement.common.exception.ErrorCode;
import com.example.gccoffeemanagement.order.repository.OrderRepository;
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
