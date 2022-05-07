package com.example.gccoffeemanagement.order;

import com.example.gccoffeemanagement.common.advice.GlobalExceptionHandler;
import com.example.gccoffeemanagement.order.controller.api.OrderApiController;
import com.example.gccoffeemanagement.order.domain.Order;
import com.example.gccoffeemanagement.order.dto.OrderCreateRequest;
import com.example.gccoffeemanagement.order.dto.OrderProductCreateRequest;
import com.example.gccoffeemanagement.common.exception.ErrorCode;
import com.example.gccoffeemanagement.common.exception.NotExecuteException;
import com.example.gccoffeemanagement.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderApiControllerTest {

    @InjectMocks
    OrderApiController orderController;

    @Mock
    OrderService orderService;

    MockMvc mockMvc;
    Gson gson;
    ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void order_등록_요청이_들어올_때_email이_blank라면_BAD_REQUEST를_반환한다(String email) throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                email,
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "test.com"})
    void order_등록_요청이_들어올_때_email이_유효하지_않은_형식이면_BAD_REQUEST를_반환한다(String email) throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                email,
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void order_등록_요청이_들어올_때_address가_blank이면_BAD_REQUEST를_반환한다(String address) throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                address,
                "11111",
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void order_등록_요청이_들어올_때_postcode가_blank이면_BAD_REQUEST를_반환한다(String postcode) throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                postcode,
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_productId가_0이면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        int productId = 0;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(productId, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_productId가_음수이면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        int productId = -1;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(productId, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_quantity가_0이면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        int quantity = 0;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, quantity))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_quantity가_음수이면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INVALID_INPUT_VALUE.getMessage();
        int quantity = -1;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, quantity))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_IllegalStateException이_넘어온다면_INTERNAL_SERVER_ERROR를_반환한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        doThrow(new IllegalStateException()).when(orderService).saveOrder(any(Order.class));
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isInternalServerError());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_NotExcuteException이_넘어온다면_INTERNAL_SERVER_ERROR를_반환한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        String message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        doThrow(new NotExecuteException()).when(orderService).saveOrder(any(Order.class));
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        resultActions.andExpect(status().isInternalServerError());
        resultActions.andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void order_등록_요청이_들어올_때_정상적인_요청_데이터라면_order를_저장한다() throws Exception {
        //given
        String url = "/api/v1/orders";
        doNothing().when(orderService).saveOrder(any(Order.class));
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                "test@test.com",
                "test address",
                "11111",
                List.of(new OrderProductCreateRequest(1, 1))
        );

        //when
        ResultActions resultActions = createOrderPerform(url, orderCreateRequest);

        //then
        verify(orderService, times(1)).saveOrder(any(Order.class));
        resultActions.andExpect(status().isCreated());
    }

    private ResultActions createOrderPerform(String url, OrderCreateRequest orderCreateRequest) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreateRequest))
        );
    }
}