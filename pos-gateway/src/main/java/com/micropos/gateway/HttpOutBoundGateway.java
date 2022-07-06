package com.micropos.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HttpOutBoundGateway {
    @Bean
    public IntegrationFlow outGate() {
        return IntegrationFlows.from("deliveryChannel")
                .handle(WebFlux.outboundGateway(
                                message -> UriComponentsBuilder
                                        .fromUriString("http://localhost:8085/api/deliveries")
                                        .build().toUri())
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class))
                .handle(System.out::println)
                .get();
    }
}
