package com.micropos.order.service;

import com.micropos.common.model.Order;
import com.micropos.order.repository.OrderRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService{

    private final WebClient webClient;

    private final OrderRepository orderRepository;

    private final StreamBridge streamBridge;

    public OrderServiceImpl(OrderRepository orderRepository,
                            StreamBridge streamBridge,
                            WebClient.Builder builder) {
        this.orderRepository = orderRepository;
        this.streamBridge = streamBridge;
        this.webClient = builder.build();
    }

    @Override
    public Flux<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> createOrder() {
        String url = "http://POS-CARTS/api/carts/checkout";
        return webClient.post()
                .uri(url)
                .retrieve()
                .bodyToMono(Order.class)
                .flatMap(orderRepository::save)
                .doOnNext(order -> streamBridge.send("createOrder-out-0", order));
    }
}
