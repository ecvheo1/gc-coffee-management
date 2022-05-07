package com.example.gccoffeemanagement;

import com.example.gccoffeemanagement.controller.GlobalExceptionHandler;
import com.example.gccoffeemanagement.controller.api.ProductApiController;
import com.example.gccoffeemanagement.domain.Category;
import com.example.gccoffeemanagement.domain.Product;
import com.example.gccoffeemanagement.dto.ProductResponse;
import com.example.gccoffeemanagement.entity.ProductEntity;
import com.example.gccoffeemanagement.service.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductApiControllerTest {

    @InjectMocks
    ProductApiController productApiController;

    @Mock
    ProductService productService;

    MockMvc mockMvc;
    Gson gson;

    @BeforeEach
    void beforeEach() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(productApiController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void product_목록_조회_요청이_들어올_때_product_목록을_리턴한다() throws Exception {
        //given
        String url = "/api/v1/products";
        List<ProductResponse> productResponses = ProductResponse.listOf(List.of(firstProduct(), secondProduct()));
        doReturn(productResponses).when(productService).findAllProducts();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        verify(productService, times(1)).findAllProducts();
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(productResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(productResponses.get(0).getName()))
                .andExpect(jsonPath("$[0].category").value(productResponses.get(0).getCategory().toString()))
                .andExpect(jsonPath("$[0].price").value(productResponses.get(0).getPrice()))
                .andExpect(jsonPath("$[0].description").value(productResponses.get(0).getDescription()))
                .andExpect(jsonPath("$[1].id").value(productResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(productResponses.get(1).getName()))
                .andExpect(jsonPath("$[1].category").value(productResponses.get(1).getCategory().toString()))
                .andExpect(jsonPath("$[1].price").value(productResponses.get(1).getPrice()))
                .andExpect(jsonPath("$[1].description").value(productResponses.get(1).getDescription()));
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
}
