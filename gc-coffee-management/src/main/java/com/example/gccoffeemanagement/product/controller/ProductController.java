package com.example.gccoffeemanagement.product.controller;

import com.example.gccoffeemanagement.product.dto.ProductCreateRequest;
import com.example.gccoffeemanagement.product.dto.ProductResponse;
import com.example.gccoffeemanagement.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductList(Model model) {
        List<ProductResponse> productResponses = Collections.unmodifiableList(ProductResponse.listOf(productService.findAllProducts()));
        model.addAttribute("products", productResponses);
        return "product-list";
    }

    @GetMapping("/product/new")
    public String getProductForm() {
        return "new-product";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute @Valid ProductCreateRequest request) {
        productService.saveProduct(request.toDomain());
        return "redirect:/products";
    }
}
