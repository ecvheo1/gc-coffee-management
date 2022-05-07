package com.example.gccoffeemanagement.product.service;

import com.example.gccoffeemanagement.product.domain.Product;
import com.example.gccoffeemanagement.product.dto.ProductResponse;
import com.example.gccoffeemanagement.order.exception.DuplicateProductException;
import com.example.gccoffeemanagement.common.exception.ErrorCode;
import com.example.gccoffeemanagement.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return Collections.unmodifiableList(ProductResponse.listOf(productRepository.findAll()));
    }

    @Override
    public void saveProduct(Product product) {
        if (product == null) {
            throw new IllegalStateException(ErrorCode.NULL_INPUT_OBJECT.getMessage());
        }
        validateDuplicateProduct(product);
        productRepository.save(product);
    }

    private void validateDuplicateProduct(Product product) {
        if (productRepository.findByNameCategoryPrice(product.getName(), product.getCategory(), product.getPrice()).isPresent()) {
            throw new DuplicateProductException();
        }
    }
}