package com.chinmay.ecommerce.api_gateway.filters;

import com.chinmay.ecommerce.api_gateway.service.JwtService;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
            return ((exchange, chain) -> {

                    // If the filter is disabled, pass the request to the next filter in the chain without any authentication.
                    if(!config.isEnabled) return chain.filter(exchange);

                    // Extract the Authorization header
                    String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

                    // Check if the Authorization header is present and starts with "Bearer "
                     if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                    }

                     // Extract the token from the Authorization header
                    String token = authorizationHeader.split("Bearer ")[1];
                     // Validate the token and extract the user ID
                     Long userId = jwtService.getUserIdFromToken(token);

                    // This is used to add the user ID to the request headers so that it can be accessed by the downstream services.
                    // We are mutating the request to add a new header "X-User-Id" with the value of the user ID extracted from the token.
                    org.springframework.http.server.reactive.ServerHttpRequest request = exchange.getRequest()
                                .mutate()
                                .header("X-User-Id", userId.toString()) // Add the user ID to the request headers
                                .build();

                     // We will pass this value to rest of downstream services for for e.g this will be used in orderService controller to get the
                    // user ID and fetch the orders for that user.

                    // Pass the request
                    return chain.filter(exchange.mutate().request(request).build());
            });
    }

    // This is used for fetching any parameters from the application.yml file for this filter.For example, if we want to enable or disable this
    // filter based on a property in the application.yml file, we can add a boolean property in the Config class and fetch it in the apply method.
    @Data
    public static class Config {
        // This is used to enable or disable the filter based on a property in the application.yml file. If this property is set to false,
        // the filter will be bypassed and the request will be passed to the downstream services without any authentication.
        private boolean isEnabled;
    }
}
