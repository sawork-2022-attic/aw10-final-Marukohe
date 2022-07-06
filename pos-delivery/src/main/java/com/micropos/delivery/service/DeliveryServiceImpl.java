package com.micropos.delivery.service;

import com.micropos.common.model.Delivery;
import com.micropos.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Mono<Delivery> createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Mono<Delivery> findDeliveryByOrderId(String orderId) {
        return deliveryRepository.findByOrderId(orderId);
    }

    @Override
    public Flux<Delivery> getDeliveries() {
        return deliveryRepository.findAll();
    }
}
