package com.example.gccoffeemanagement.order;

import com.example.gccoffeemanagement.order.domain.Order;
import com.example.gccoffeemanagement.order.dto.OrderCreateRequest;
import com.example.gccoffeemanagement.order.dto.OrderProductCreateRequest;
import com.example.gccoffeemanagement.order.repository.OrderRepository;
import com.example.gccoffeemanagement.order.service.OrderService;
import com.example.gccoffeemanagement.order.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void order_저장_요청이_들어올_때_전달받은_order가_null이면_IllegalStateException이_발생한다() {
        //given
        final Order order = null;

        //when, then
        assertThrows(IllegalStateException.class, () -> orderService.saveOrder(order));
    }

    @Test
    void order_저장_요청이_들어올_때_정상적인_요청_데이터라면_orderRepository의_save_메소드를_호출한다() {
        //given
        final Order order = newOrder();

        //when
        orderService.saveOrder(order);

        //then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    private Order newOrder() {
        return Order.of(
                new OrderCreateRequest(
                        "test@test.com",
                        "test address",
                        "11111",
                        List.of(new OrderProductCreateRequest(1, 1))
        ));
    }
}
