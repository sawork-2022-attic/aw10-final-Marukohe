package com.micropos.carts.service;

import com.micropos.common.model.Cart;
import com.micropos.common.model.Order;
import reactor.core.publisher.Mono;

public interface CartService {
    Mono<Cart> getCart();

    Mono<Cart> addProduct(String productId);

    Mono<Cart> removeProduct(String productId);

    Mono<Order> checkoutCart();
}
