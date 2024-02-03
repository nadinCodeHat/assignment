package com.ilabs.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfiguration {

    @Bean
    public OpenAPI gateWayOpenApi() {
        return new OpenAPI().info(new Info().title("ILabs Shopping Cart Microservices")
                .description("Documentation for all the Microservices in ILabs Shopping Cart Application")
                .version("v1.0.0"));
    }
}
