package com.example.gccoffeemanagement.domain;

import com.example.gccoffeemanagement.dto.OrderProductCreateRequest;

import java.time.LocalDateTime;

public class OrderProduct {
    private long id;
    private long productId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private OrderProduct(long productId, int quantity) {
        this(0, productId, quantity, null, null);
    }

    private OrderProduct(long id, long productId, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static OrderProduct of(OrderProductCreateRequest request) {
        return new OrderProduct(request.getProductId(), request.getQuantity());
    }

    public static OrderProduct of(long id, long productId, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new OrderProduct(id, productId, quantity, createdAt, updatedAt);
    }
}
