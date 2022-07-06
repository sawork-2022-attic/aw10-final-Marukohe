package com.micropos.products.service;

import com.micropos.common.model.Product;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> products() {
        return productRepository.findAll().take(50, true);
    }

    @Override
    public Mono<Product> getProduct(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> randomProduct() {
        return null;
    }
}
