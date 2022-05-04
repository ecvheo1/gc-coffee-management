package com.example.gccoffeemanagement.controller;

import com.example.gccoffeemanagement.dto.ProductCreateRequest;
import com.example.gccoffeemanagement.dto.ProductResponse;
import com.example.gccoffeemanagement.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductList(Model model) {
        List<ProductResponse> productResponses = productService.findAllProducts();
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
        return "redirect:/api/v1/products";
    }
}
