package com.micropos.carts.rest;

import com.micropos.common.api.CartsApi;
import com.micropos.common.dto.CartDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.common.dto.OrderDto;
import com.micropos.carts.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class CartController implements CartsApi {

    private final CartMapper cartMapper;
    private final CartService cartService;

    public CartController(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    @Override
    public Mono<ResponseEntity<CartDto>> addProductById(String productId, ServerWebExchange exchange) {
        return cartService.addProduct(productId)
                .map(cartMapper::toCartDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<CartDto>> getCart(ServerWebExchange exchange) {
        return cartService.getCart()
                .map(cartMapper::toCartDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<CartDto>> removeProduct(String productId, ServerWebExchange exchange) {
        return cartService.removeProduct(productId)
                .map(cartMapper::toCartDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> checkoutCart(ServerWebExchange exchange) {
        return cartService.checkoutCart()
                .map(cartMapper::toOrderDto)
                .map(ResponseEntity::ok);
    }
}
