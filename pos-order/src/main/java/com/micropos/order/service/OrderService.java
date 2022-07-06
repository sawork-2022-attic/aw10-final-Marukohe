package com.micropos.order.service;

import com.micropos.common.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Flux<Order> getOrders();

    Mono<Order> createOrder();
}
