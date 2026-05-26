package com.healthcare.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;

@Configuration
public class GatewayConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user-service")
            .route(RequestPredicates.path("/patients/**"),
                HandlerFunctions.http("http://localhost:8081"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> providerServiceRoute() {
        return GatewayRouterFunctions.route("provider-service")
            .route(RequestPredicates.path("/doctors/**"),
                HandlerFunctions.http("http://localhost:8082"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute() {
        return GatewayRouterFunctions.route("booking-service")
            .route(RequestPredicates.path("/appointments/**"),
                HandlerFunctions.http("http://localhost:8083"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoute() {
        return GatewayRouterFunctions.route("payment-service")
            .route(RequestPredicates.path("/bills/**"),
                HandlerFunctions.http("http://localhost:8084"))
            .build();
    }
}