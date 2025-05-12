package com.medilabo.gateway_server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for web client
 */
@Configuration
public class WebClientConfig {

    /**
     * Bean to configure the base url for web client
     * 
     * @param builder
     * @return
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
    	
        return builder.baseUrl("http://localhost:8080").build();
        
    }
    
}
