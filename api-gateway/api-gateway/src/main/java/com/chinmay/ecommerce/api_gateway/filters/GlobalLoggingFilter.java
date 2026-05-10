package com.chinmay.ecommerce.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    @Override
    // This method will be called for every request passing through the API Gateway
    // Mono<Void> indicates that this method will return a reactive stream that completes when the filter processing is done
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Pre-Filter

        // exchange.getRequest().getPath() is used to get the path of the incoming request
        log.info("Logging From Global Pre: {}", exchange.getRequest().getPath());

        // chain.filter(exchange) is used to pass the request to the next filter in the chain.
        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            // Post-Filter
            log.info("Logging From  Global Post: {}", exchange.getResponse().getStatusCode());
        }));
    }

    // The getOrder() method is used to specify the order of the filter in the filter chain.
    @Override
    public int getOrder() {
        return 5;
    }
}