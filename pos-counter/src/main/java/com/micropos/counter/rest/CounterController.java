package com.micropos.counter.rest;

import com.micropos.common.api.CounterApi;
import com.micropos.common.dto.CartDto;
import com.micropos.counter.service.CounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class CounterController implements CounterApi {

    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public Mono<ResponseEntity<Double>> checkout(Mono<CartDto> cartDto, ServerWebExchange exchange) {
        return counterService.getTotalPrice(cartDto).map(ResponseEntity::ok);
    }
}
