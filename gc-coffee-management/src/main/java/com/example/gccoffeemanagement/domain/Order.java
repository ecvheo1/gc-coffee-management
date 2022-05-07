package com.example.gccoffeemanagement.domain;

import com.example.gccoffeemanagement.dto.OrderCreateRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private long id;
    private String email;
    private String address;
    private String postcode;
    private List<OrderProduct> orderProducts;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(String email, String address, String postcode, List<OrderProduct> orderProducts) {
        this(0, email, address, postcode, orderProducts, OrderStatus.ACCEPTED, null, null);
    }

    private Order(long id, String email, String address, String postcode, List<OrderProduct> orderProducts, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderProducts = orderProducts;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Order of(OrderCreateRequest request) {
        List<OrderProduct> orderProducts = request.getOrderProductRequests()
                .stream()
                .map(orderProductRequest -> orderProductRequest.toDomain())
                .collect(Collectors.toList());
        return new Order(request.getEmail(), request.getAddress(), request.getPostcode(), orderProducts);
    }

    public static Order of(long id, String email, String address, String postcode, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Order(id, email, address, postcode, List.of(), orderStatus, createdAt, updatedAt);
    }
}
