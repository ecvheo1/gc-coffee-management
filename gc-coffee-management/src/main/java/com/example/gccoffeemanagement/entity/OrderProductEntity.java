package com.example.gccoffeemanagement.entity;

import com.example.gccoffeemanagement.domain.OrderProduct;

import java.time.LocalDateTime;

public class OrderProductEntity extends BaseTimeEntity {
    private final long id;
    private final long orderId;
    private final long productId;
    private final int quantity;

    private OrderProductEntity(LocalDateTime createdAt, LocalDateTime updatedAt, long id, long orderId, long productId, int quantity) {
        super(createdAt, updatedAt);
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public static OrderProductEntity of(long id, long orderId, long productId, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new OrderProductEntity(createdAt, updatedAt, id, orderId, productId, quantity);
    }

    public static OrderProductEntity from(OrderProduct orderProduct) {
        return new OrderProductEntity(null, null, 0, 0, orderProduct.getProductId(), orderProduct.getQuantity());
    }

    public OrderProduct toDomain() {
        return OrderProduct.of(id, productId, quantity, super.getCreatedAt(), super.getUpdatedAt());
    }
}
