package com.micropos.carts.repository;

import com.micropos.common.model.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> getProduct(String productId);
}
