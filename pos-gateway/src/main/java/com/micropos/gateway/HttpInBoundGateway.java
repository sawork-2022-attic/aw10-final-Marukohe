package com.micropos.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.stereotype.Component;

@Component
public class HttpInBoundGateway {
    @Bean
    public IntegrationFlow inGate() {
        return IntegrationFlows
                .from(WebFlux.inboundChannelAdapter("/integration/deliveries")
                        .requestMapping(m -> m.methods(HttpMethod.GET))
                        .statusCodeFunction(m -> HttpStatus.ACCEPTED))
                .channel("deliveryChannel")
                .get();
    }
}
