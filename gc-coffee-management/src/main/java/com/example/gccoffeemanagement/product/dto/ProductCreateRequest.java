package com.example.gccoffeemanagement.product.dto;

import com.example.gccoffeemanagement.product.domain.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @Min(0)
    private int price;

    @NotBlank
    private String description;

    public ProductCreateRequest(String name, String category, int price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Product toDomain() {
        return Product.of(this);
    }
}
