package com.chinmay.ecommerce.order_service.Configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RefreshScope
@Data
public class FeaturesEnableConfig {
    @Value("${features.user-tracking-enabled}")
    private boolean isUserTrackingEnabled;
}
