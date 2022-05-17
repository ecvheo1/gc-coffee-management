package com.example.gccoffeemanagement.product;

import com.example.gccoffeemanagement.common.advice.GlobalExceptionHandler;
import com.example.gccoffeemanagement.product.controller.ProductController;
import com.example.gccoffeemanagement.product.domain.Category;
import com.example.gccoffeemanagement.product.domain.Product;
import com.example.gccoffeemanagement.product.dto.ProductResponse;
import com.example.gccoffeemanagement.product.entity.ProductEntity;
import com.example.gccoffeemanagement.order.exception.DuplicateProductException;
import com.example.gccoffeemanagement.common.exception.ErrorCode;
import com.example.gccoffeemanagement.common.exception.NotExecuteException;
import com.example.gccoffeemanagement.product.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void product_목록_뷰_조회_요청이_들어올_때_product_목록을_조회하고_product_목록_뷰_이름을_리턴한다() throws Exception {
        //given
        String url = "/products";
        String viewName = "product-list";
        Product firstProduct = ProductEntity.of(
                1L,
                "first",
                Category.BLONDE_ROAST,
                10000,
                "first description",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        ).toDomain();
        Product secondProduct = ProductEntity.of(
                1L,
                "second",
                Category.BLONDE_ROAST,
                10000,
                "second description",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        ).toDomain();
        List<Product> products = List.of(firstProduct, secondProduct);
        List<ProductResponse> productResponses = ProductResponse.listOf(products);
        doReturn(products).when(productService).findAllProducts();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        //then
        verify(productService, times(1)).findAllProducts();
        resultActions.andExpect(view().name(viewName));
    }

    @Test
    void product_등록_뷰_요청이_들어올_때_product_등록_뷰_이름을_반환한다() throws Exception {
        //given
        String url = "/product/new";
        String viewName = "new-product";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        //then
        resultActions.andExpect(view().name(viewName));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void product_등록_요청이_들어올_때_name이_blank라면_BAD_REQUEST를_반환한다(String name) throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", name)
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void product_등록_요청이_들어올_때_category가_blank라면_BAD_REQUEST를_반환한다(String category) throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", category)
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void product_등록_요청이_들어올_때_price가_음수라면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "-1")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void product_등록_요청이_들어올_때_description이_blank라면_BAD_REQUEST를_반환한다(String description) throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "100")
                        .param("description", description));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void product_등록_요청이_들어올_때_category가_유효하지_않다면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.CATEGORY_NOT_FOUND.getMessage();
        String invalidCategory = "blahblah";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", invalidCategory)
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void product_등록_요청이_들어올_때_IllegalStateException이_넘어온다면_INTERNAL_SERVER_ERROR를_반환한다() throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        doThrow(new IllegalStateException()).when(productService).saveProduct(any(Product.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isInternalServerError());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void product_등록_요청이_들어올_때_DuplicateProductException이_넘어온다면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.PRODUCT_DUPLICATED.getMessage();
        doThrow(new DuplicateProductException()).when(productService).saveProduct(any(Product.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void product_등록_요청이_들어올_때_NotExcuteException이_넘어온다면_INTERNAL_SERVER_ERROR를_반환한다() throws Exception {
        //given
        String url = "/products";
        String message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        doThrow(new NotExecuteException()).when(productService).saveProduct(any(Product.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        resultActions.andExpect(status().isInternalServerError());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void product_등록_요청이_들어올_때_정상적인_요청_데이터라면_product를_저장하고_리다이렉트한다() throws Exception {
        //given
        String url = "/products";
        String redirectedUrl = "/products";
        doNothing().when(productService).saveProduct(any(Product.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "test")
                        .param("category", "MEDIUM_ROAST")
                        .param("price", "100")
                        .param("description", "test description"));

        //then
        verify(productService, times(1)).saveProduct(any(Product.class));
        resultActions.andExpect(redirectedUrl(redirectedUrl));
    }
}