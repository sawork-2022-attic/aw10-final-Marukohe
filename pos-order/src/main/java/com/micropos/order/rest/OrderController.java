package com.micropos.order.rest;

import com.micropos.common.api.OrdersApi;
import com.micropos.common.dto.OrderDto;
import com.micropos.order.exception.OrderNotFoundException;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class OrderController implements OrdersApi {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    public OrderController(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> createOrder(ServerWebExchange exchange) {
        return orderService.createOrder()
                .map(orderMapper::toOrderDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new OrderNotFoundException()));
    }

    @Override
    public Mono<ResponseEntity<Flux<OrderDto>>> listOrders(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(
                        orderService.getOrders().map(orderMapper::toOrderDto)))
                .switchIfEmpty(Mono.error(new OrderNotFoundException()));
    }
}
