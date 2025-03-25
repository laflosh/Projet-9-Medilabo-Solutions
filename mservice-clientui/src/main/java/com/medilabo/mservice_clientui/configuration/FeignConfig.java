package com.medilabo.mservice_clientui.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

/**
 * 
 */
@Configuration
public class FeignConfig {
	
	@Value("${feign.client.username}")
	private String username;

	@Value("${feign.client.password}")
	private String password;
	
	/**
	 * @return
	 */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
         return new BasicAuthRequestInterceptor(username, password);
    }
	
}
