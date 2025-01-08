package com.pratikesh.ecommerce.api_gateway.filters;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    public AuthenticationGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().get("Authorization").getFirst();
            String token = authorizationHeader.split("Bearer ")[1];


            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config{

    }
}
