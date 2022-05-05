package com.example.gccoffeemanagement.service;

import com.example.gccoffeemanagement.domain.Product;
import com.example.gccoffeemanagement.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAllProducts();

    void saveProduct(Product product);
}
