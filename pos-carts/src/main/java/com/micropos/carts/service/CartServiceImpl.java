package com.micropos.carts.service;

import com.micropos.carts.exception.ProductNotFoundException;
import com.micropos.common.model.Cart;
import com.micropos.common.model.Item;
import com.micropos.common.model.Order;
import com.micropos.common.model.Product;
import com.micropos.carts.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
class CartServiceImpl implements CartService {

    private final ProductRepository db;
    private final WebClient webClient;

    private Cart cart;

    public CartServiceImpl(ProductRepository db, WebClient.Builder webClientBuilder) {
        this.db = db;
        this.webClient = webClientBuilder.build();
    }

    private Cart getCartInternal() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    @Override
    public Mono<Cart> getCart() {
        return Mono.just(getCartInternal());
    }

    private Cart addProductInter(Product product) {
        Cart cart = getCartInternal();
        for (Item item : cart.getItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return cart;
            }
        }
        cart.addItem(new Item(product, 1));
        return cart;
    }

    @Override
    public Mono<Cart> addProduct(String productId) {
        return db.getProduct(productId)
                .map(this::addProductInter)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    private Cart removeProductInter(Product product) {
        Cart cart = getCartInternal();
        for (Item item : cart.getItems()) {
            if (item.getProduct().equals(product)) {
                cart.removeItem(item);
                return cart;
            }
        }
        return cart;
    }

    @Override
    public Mono<Cart> removeProduct(String productId) {
        return db.getProduct(productId)
                .map(this::removeProductInter)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    @Override
    public Mono<Order> checkoutCart() {
        String counterUrl = "http://POS-COUNTER/api/counter/checkout";

        return webClient.post()
                .uri(counterUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(getCartInternal())
                .retrieve()
                .bodyToMono(Double.class)
                .map((total) -> {
                    Order order = Order
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .items(getCartInternal().getItems())
                                    .counter(total)
                                    .build();
                    cart = new Cart();     // empty cart
                    return order;
                });
    }
}
