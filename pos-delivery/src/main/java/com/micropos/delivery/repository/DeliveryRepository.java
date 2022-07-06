package com.micropos.delivery.repository;

import com.micropos.common.model.Delivery;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DeliveryRepository extends ReactiveMongoRepository<Delivery, String> {
    Mono<Delivery> findByOrderId(String orderId);
}
