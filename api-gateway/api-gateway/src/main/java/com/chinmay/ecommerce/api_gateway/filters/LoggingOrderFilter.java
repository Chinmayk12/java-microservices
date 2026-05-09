package com.chinmay.ecommerce.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingOrderFilter extends AbstractGatewayFilterFactory<LoggingOrderFilter.Config> {

    public LoggingOrderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Pre-Filter logic
            log.info("Order Filter Pre: {}", exchange.getRequest().getURI());

            // Pass the request to the next filter in the chain
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Post-Filter logic
                log.info("Order Filter Post: {}", exchange.getResponse().getStatusCode());
            }));
        };
    }

    public static class Config {
        // Put the configuration properties for your filter here
    }

}
