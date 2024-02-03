package com.ilabs.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cart-service", r -> r.path("/api/v1/cart/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://cart-service"))
                .route("manage-items-service", r -> r.path("/api/v1/item/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://manage-items-service"))
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .build();
    }
}