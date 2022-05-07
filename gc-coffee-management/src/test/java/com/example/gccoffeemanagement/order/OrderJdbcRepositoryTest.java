package com.example.gccoffeemanagement.order;

import com.example.gccoffeemanagement.order.dto.OrderCreateRequest;
import com.example.gccoffeemanagement.order.dto.OrderProductCreateRequest;
import com.example.gccoffeemanagement.order.repository.OrderJdbcRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@Sql(scripts = {"classpath:product-insert.sql"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderJdbcRepositoryTest {

    @Configuration
    @EnableAutoConfiguration
    static class Config {

        @Bean
        public OrderJdbcRepository orderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new OrderJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    private OrderJdbcRepository orderJdbcRepository;

    @Test
    @Order(1)
    void order를_저장할_때_전달받은_order가_null이면_IllegalStateException이_발생한다() {
        //given
        final com.example.gccoffeemanagement.order.domain.Order order = null;

        //when, then
        assertThrows(IllegalStateException.class, () -> orderJdbcRepository.save(order));
    }

    @Test
    @Order(2)
    void order를_저장할_때_전달받은_order를_저장한다() {
        //given
        final com.example.gccoffeemanagement.order.domain.Order order = newOrder();

        //when, then
        assertDoesNotThrow(() -> orderJdbcRepository.save(order));
    }

    private com.example.gccoffeemanagement.order.domain.Order newOrder() {
        return com.example.gccoffeemanagement.order.domain.Order.of(
                new OrderCreateRequest(
                        "test@test.com",
                        "test address",
                        "11111",
                        List.of(new OrderProductCreateRequest(1, 1))
                ));
    }
}