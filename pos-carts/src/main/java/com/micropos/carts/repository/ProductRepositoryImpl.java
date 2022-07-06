package com.micropos.carts.repository;

import com.micropos.common.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
class ProductRepositoryImpl implements ProductRepository {

    private final WebClient webClient;

    ProductRepositoryImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
    }

    @Override
    public Mono<Product> getProduct(String productId) {
        String url = String.format("http://POS-PRODUCTS/api/products/%s", productId);
        return webClient.get().uri(url).retrieve().bodyToMono(Product.class);
    }
}
