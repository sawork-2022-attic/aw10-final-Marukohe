package com.micropos.delivery.rest;

import com.micropos.common.api.DeliveriesApi;
import com.micropos.common.dto.DeliveryDto;
import com.micropos.delivery.exception.DeliveryNotFoundException;
import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class DeliveryController implements DeliveriesApi {
    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;

    public DeliveryController(DeliveryService deliveryService, DeliveryMapper deliveryMapper) {
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public Mono<ResponseEntity<DeliveryDto>> getDeliveryByOrderId(String orderId, ServerWebExchange exchange) {
        return deliveryService.findDeliveryByOrderId(orderId)
                .map(deliveryMapper::toDeliveryDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new DeliveryNotFoundException()));
    }

    @Override
    public Mono<ResponseEntity<Flux<DeliveryDto>>> getDeliveries(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(
                        deliveryService.getDeliveries().map(deliveryMapper::toDeliveryDto)))
                .switchIfEmpty(Mono.error(new DeliveryNotFoundException()));
    }
}
