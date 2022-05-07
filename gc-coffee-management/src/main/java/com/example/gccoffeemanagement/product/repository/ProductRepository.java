package com.example.gccoffeemanagement.product.repository;

import com.example.gccoffeemanagement.product.domain.Category;
import com.example.gccoffeemanagement.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findByNameCategoryPrice(String name, Category category, int price);

    void save(Product product);
}
