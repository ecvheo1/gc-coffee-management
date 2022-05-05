package com.example.gccoffeemanagement;

import com.example.gccoffeemanagement.domain.Category;
import com.example.gccoffeemanagement.domain.Product;
import com.example.gccoffeemanagement.dto.ProductCreateRequest;
import com.example.gccoffeemanagement.repository.ProductJdbcRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductJdbcRepositoryTest {

    @Configuration
    @EnableAutoConfiguration
    static class Config {

        @Bean
        public ProductJdbcRepository productJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new ProductJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    private ProductJdbcRepository productJdbcRepository;

    @Test
    @Order(1)
    void product를_전체_조회할_때__저장된_product가_없다면_빈_리스트를_반환한다() {
        //given, when
        List<Product> foundProducts = productJdbcRepository.findAll();

        //then
        assertThat(foundProducts.size()).isEqualTo(0);
    }

    @Test
    @Order(2)
    void product를_저장할_때_전달받은_product가_null이면_IllegalStateException이_발생한다() {
        //given
        final Product product = null;

        //when, then
        assertThrows(IllegalStateException.class, () -> productJdbcRepository.save(product));
    }

    @Test
    @Order(3)
    void product를_저장할_때_전달받은_product를_저장한다() {
        //given
        final Product firstProduct = firstProduct();
        final Product secondProduct = secondProduct();

        //when, then
        assertDoesNotThrow(() -> productJdbcRepository.save(firstProduct));
        assertDoesNotThrow(() -> productJdbcRepository.save(secondProduct));
    }

    @Test
    @Order(4)
    void name_category_price로_product를_조회할_때_조회된_바우처가_없다면_Optional_empty를_반환한다() {
        //given
        String name = "test";
        Category category = Category.MEDIUM_ROAST;
        int price = 1;

        //when
        Optional<Product> foundProduct = productJdbcRepository.findByNameCategoryPrice(name, category, price);

        //then
        assertThat(foundProduct).isEqualTo(Optional.empty());
    }

    @Test
    @Order(5)
    void name_category_price로_product를_조회할_때_조회된_바우처가_있다면_조회된_바우처를_반환한다() {
        //given
        final Product product = firstProduct();

        //when
        Optional<Product> foundProduct = productJdbcRepository.findByNameCategoryPrice(product.getName(), product.getCategory(), product.getPrice());

        //then
        assertThat(foundProduct).isNotEqualTo(Optional.empty());
        assertThat(foundProduct.get().getName()).isEqualTo(product.getName());
        assertThat(foundProduct.get().getCategory()).isEqualTo(product.getCategory());
        assertThat(foundProduct.get().getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @Order(6)
    void product를_전체_조회할_때_조회된_product_리스트를_반환한다() {
        //given, when
        List<Product> foundProducts = productJdbcRepository.findAll();

        //then
        assertThat(foundProducts.size()).isEqualTo(2);
        assertThat(foundProducts.get(0)).usingRecursiveComparison().ignoringFields("id", "createdAt", "updatedAt").isEqualTo(firstProduct());
        assertThat(foundProducts.get(1)).usingRecursiveComparison().ignoringFields("id", "createdAt", "updatedAt").isEqualTo(secondProduct());
    }

    private Product firstProduct() {
        return Product.of(new ProductCreateRequest("first", "BLONDE_ROAST", 10000, "first description"));
    }

    private Product secondProduct() {
        return Product.of(new ProductCreateRequest("second", "BLONDE_ROAST", 10000, "second description"));
    }
}
