package com.example.gccoffeemanagement.entity;

import com.example.gccoffeemanagement.domain.Category;
import com.example.gccoffeemanagement.domain.Product;

import java.time.LocalDateTime;

public class ProductEntity extends BaseTimeEntity {
    private final long id;
    private final String name;
    private final Category category;
    private final int price;
    private final String description;

    private ProductEntity(LocalDateTime createdAt, LocalDateTime updatedAt, long id, String name, Category category, int price, String description) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
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

    public static ProductEntity of(long id, String name, Category category, int price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProductEntity(createdAt, updatedAt, id, name, category, price, description);
    }

    public Product toDomain() {
        return Product.of(id, name, category, price, description, super.getCreatedAt(), super.getUpdatedAt());
    }
}
