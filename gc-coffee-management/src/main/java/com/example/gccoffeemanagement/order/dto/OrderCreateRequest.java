package com.example.gccoffeemanagement.order.dto;

import com.example.gccoffeemanagement.order.domain.Order;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class OrderCreateRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String postcode;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<OrderProductCreateRequest> orderProductRequests;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(String email, String address, String postcode, List<OrderProductCreateRequest> orderProductRequests) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderProductRequests = orderProductRequests;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<OrderProductCreateRequest> getOrderProductRequests() {
        return orderProductRequests;
    }

    public Order toDomain() {
        return Order.of(this);
    }
}
