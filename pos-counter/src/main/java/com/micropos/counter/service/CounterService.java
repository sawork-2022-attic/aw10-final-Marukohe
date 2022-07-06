package com.micropos.counter.service;

import com.micropos.common.dto.CartDto;
import com.micropos.common.dto.ItemDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CounterService {
    public Mono<Double> getTotalPrice(Mono<CartDto> cart) {
        return cart.map(this::checkout).defaultIfEmpty(Double.NaN);
    }

    private Double checkout(CartDto cartDto) {
        double total = 0;
        for (ItemDto item : cartDto.getItems()) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }

}
