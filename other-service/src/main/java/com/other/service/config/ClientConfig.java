package com.other.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Value("${product.service.url}")
    private String productServiceUrl;
    @Value("${user.service.url}")
    private String userServiceUrl;

    @Bean
    public WebClient productWebClient(WebClient.Builder builder) {
        return builder.baseUrl(productServiceUrl)
                .build();
    }

    @Bean
    public WebClient userWebClient(WebClient.Builder builder) {
        return builder.baseUrl(userServiceUrl)
                .build();
    }

}
