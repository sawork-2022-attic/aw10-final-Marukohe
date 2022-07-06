package com.micropos.delivery.config;

import com.micropos.common.model.Delivery;
import com.micropos.common.model.Order;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class DeliveryConfiguration {

    @Bean
    public Consumer<Order> createDelivery(DeliveryService deliveryService) {
        return (order) -> {
            Delivery delivery = Delivery.builder()
                    .id(UUID.randomUUID().toString())
                    .orderId(order.getId())
                    .build();
            Mono.delay(Duration.ofSeconds(10))
                    .then(deliveryService.createDelivery(delivery))
                    .block();
        };
    }
}
