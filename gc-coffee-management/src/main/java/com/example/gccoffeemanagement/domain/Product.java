package com.example.gccoffeemanagement.domain;

import com.example.gccoffeemanagement.dto.ProductCreateRequest;

import java.time.LocalDateTime;

public class Product {
    private final long id;
    private final String name;
    private final Category category;
    private final int price;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Product(String name, Category category, int price, String description) {
        this(0, name, category, price, description, null, null);
    }

    private Product(long id, String name, Category category, int price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Product of(ProductCreateRequest request) {
        return new Product(request.getName(), Category.from(request.getCategory()), request.getPrice(), request.getDescription());
    }

    public static Product entityOf(long id, String name, Category category, int price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Product(id, name, category, price, description, createdAt, updatedAt);
    }
}
