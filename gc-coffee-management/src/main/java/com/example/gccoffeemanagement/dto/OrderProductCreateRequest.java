package com.example.gccoffeemanagement.dto;

import com.example.gccoffeemanagement.domain.OrderProduct;

import javax.validation.constraints.Min;

public class OrderProductCreateRequest {
    @Min(1)
    private int productId;

    @Min(1)
    private int quantity;

    public OrderProductCreateRequest() {
    }

    public OrderProductCreateRequest(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderProduct toDomain() {
        return OrderProduct.of(this);
    }
}