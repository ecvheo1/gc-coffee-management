package com.example.gccoffeemanagement.controller.api;

import com.example.gccoffeemanagement.dto.OrderCreateRequest;
import com.example.gccoffeemanagement.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderApiController {

    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody @Valid OrderCreateRequest request) {
        orderService.saveOrder(request.toDomain());
    }
}
