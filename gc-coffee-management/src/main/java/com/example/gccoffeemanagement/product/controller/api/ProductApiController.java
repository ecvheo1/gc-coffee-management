package com.example.gccoffeemanagement.product.controller.api;

import com.example.gccoffeemanagement.product.dto.ProductResponse;
import com.example.gccoffeemanagement.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProductList() {
        return Collections.unmodifiableList(ProductResponse.listOf(productService.findAllProducts()));
    }
}
