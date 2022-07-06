package com.micropos.delivery.service;

import com.micropos.common.model.Delivery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryService {
    Mono<Delivery> createDelivery(Delivery delivery);

    Mono<Delivery> findDeliveryByOrderId(String orderId);

    Flux<Delivery> getDeliveries();
}
