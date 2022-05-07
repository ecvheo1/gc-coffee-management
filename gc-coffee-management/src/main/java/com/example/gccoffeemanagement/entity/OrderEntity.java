package com.example.gccoffeemanagement.entity;

import com.example.gccoffeemanagement.domain.Order;
import com.example.gccoffeemanagement.domain.OrderStatus;

import java.time.LocalDateTime;

public class OrderEntity extends BaseTimeEntity {
    private final long id;
    private final String email;
    private final String address;
    private final String postcode;
    private final OrderStatus orderStatus;

    private OrderEntity(LocalDateTime createdAt, LocalDateTime updatedAt, long id, String email, String address, String postcode, OrderStatus orderStatus) {
        super(createdAt, updatedAt);
        this.id = id;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public static OrderEntity of(long id, String email, String address, String postcode, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new OrderEntity(createdAt, updatedAt, id, email, address, postcode, orderStatus);
    }

    public static OrderEntity from(Order order) {
        return new OrderEntity(null, null, 0, order.getEmail(), order.getAddress(), order.getPostcode(), order.getOrderStatus());
    }

    public Order toDomain() {
        return Order.of(id, email, address, postcode, orderStatus, super.getCreatedAt(), super.getUpdatedAt());
    }
}
