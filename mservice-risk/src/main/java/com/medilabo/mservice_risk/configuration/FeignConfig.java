package com.medilabo.mservice_risk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Config feign to intercept the request and put the token in it to pass the security in the gateway
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
    	
        return requestTemplate -> {
        	
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
            if (attributes != null) {
            	
                HttpServletRequest request = attributes.getRequest();
                
                String authHeader = request.getHeader("Authorization");
                
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                	
                    requestTemplate.header("Authorization", authHeader);
                    
                }
                
            }
            
        };
        
    }

}
