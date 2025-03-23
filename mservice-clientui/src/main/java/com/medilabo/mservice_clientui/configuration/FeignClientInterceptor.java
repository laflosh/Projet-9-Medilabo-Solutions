package com.medilabo.mservice_clientui.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import feign.RequestInterceptor;

/**
 * 
 */
@Configuration
public class FeignClientInterceptor {

	//User in memory with basicAuth
	private final String username = "user";
	private final String password = "password";
	
	/**
	 * @return
	 */
	@Bean
	public RequestInterceptor requestInterceptor() {
		
		return requestTemplate -> {
			String auth = username + ":" + password;
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	        requestTemplate.header("Authorization", "Basic " + encodedAuth);
		};
		
	}
	
	/**
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
}
