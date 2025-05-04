package com.medialbo.mservice_risk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(HttpServletRequest request) {
    	
        return requestTemplate -> {
        	
            String authHeader = request.getHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
            	
                requestTemplate.header("Authorization", authHeader);
                
            }
            
        };
        
    }
	
}
