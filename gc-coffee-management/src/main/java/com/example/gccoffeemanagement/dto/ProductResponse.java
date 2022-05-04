package com.example.gccoffeemanagement.dto;

import com.example.gccoffeemanagement.domain.Category;
import com.example.gccoffeemanagement.domain.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
    private long id;
    private String name;
    private Category category;
    private int price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProductResponse(long id, String name, Category category, int price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public static List<ProductResponse> listOf(List<Product> products) {
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
