package com.example.gccoffeemanagement.product.service;

import com.example.gccoffeemanagement.product.domain.Product;
import com.example.gccoffeemanagement.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    void saveProduct(Product product);
}
