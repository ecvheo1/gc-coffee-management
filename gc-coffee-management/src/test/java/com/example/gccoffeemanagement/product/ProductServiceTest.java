package com.example.gccoffeemanagement.product;

import com.example.gccoffeemanagement.product.domain.Category;
import com.example.gccoffeemanagement.product.domain.Product;
import com.example.gccoffeemanagement.product.dto.ProductCreateRequest;
import com.example.gccoffeemanagement.product.dto.ProductResponse;
import com.example.gccoffeemanagement.product.entity.ProductEntity;
import com.example.gccoffeemanagement.order.exception.DuplicateProductException;
import com.example.gccoffeemanagement.product.repository.ProductRepository;
import com.example.gccoffeemanagement.product.service.ProductService;
import com.example.gccoffeemanagement.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void product_목록_조회_요청이_들어올_때_product_목록을_조회하고_product_리스트를_반환한다() {
        //given
        final Product firstProduct = firstProduct();
        final Product secondProduct = secondProduct();
        List<Product> products = List.of(firstProduct, secondProduct);
        doReturn(products).when(productRepository).findAll();

        //when
        List<Product> foundProducts = productService.findAllProducts();

        //then
        verify(productRepository, times(1)).findAll();
        assertThat(foundProducts).usingRecursiveComparison().isEqualTo(products);
    }

    @Test
    void product_저장_요청이_들어올_때_전달받은_product가_null이면_IllegalStateException이_발생한다() {
        //given
        final Product product = null;

        //when, then
        assertThrows(IllegalStateException.class, () -> productService.saveProduct(product));
    }

    @Test
    void product_저장_요청이_들어올_때_중복된_product가_이미_존재하면_DuplicateProductException이_발생한다() {
        //given
        final Product product = newProduct();
        doReturn(Optional.of(Product.class)).when(productRepository).findByNameCategoryPrice(any(String.class), any(Category.class), any(Integer.class));

        //when, then
        assertThrows(DuplicateProductException.class, () -> productService.saveProduct(product));
    }

    @Test
    void product_저장_요청이_들어올_때_정상적인_요청_데이터라면_productRepository의_save_메소드를_호출한다() {
        //given
        final Product product = newProduct();
        doReturn(Optional.empty()).when(productRepository).findByNameCategoryPrice(any(String.class), any(Category.class), any(Integer.class));

        //when
        productService.saveProduct(product);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    private Product firstProduct() {
        return ProductEntity.of(
                1L,
                "first",
                Category.BLONDE_ROAST,
                10000,
                "first description",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        ).toDomain();
    }

    private Product secondProduct() {
        return ProductEntity.of(
                1L,
                "second",
                Category.BLONDE_ROAST,
                10000,
                "second description",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        ).toDomain();
    }

    private Product newProduct() {
        return Product.of(new ProductCreateRequest("test", "BLONDE_ROAST", 10000, "test description"));
    }
}
