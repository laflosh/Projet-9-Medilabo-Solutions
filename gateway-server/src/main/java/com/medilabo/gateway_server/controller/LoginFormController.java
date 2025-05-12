package com.medilabo.gateway_server.controller;

import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.service.AuthenticationService;

import reactor.core.publisher.Mono;

/**
 * Controller class for handling the request from the form login page to authenticate an user in the application
 */
@Controller
public class LoginFormController {

	@Autowired
	AuthenticationService authService;
	
	/**
	 * Authenicate an user from the form in the login page
	 * Set a cookie with the jwt token
	 * Redirect the user in /ui/patients page in the application
	 * 
	 * @param exchange
	 * @return
	 */
	@PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Mono<Void> loginFromHtmlForm(ServerWebExchange exchange){
		
		return exchange.getFormData()
				.flatMap(date -> {
					
					String username = date.getFirst("username");
					String password = date.getFirst("password");
					
					AuthenticationRequest authRequest = new AuthenticationRequest();
					authRequest.setUsername(username);
					authRequest.setPassword(password);
					
					return authService.authentication(authRequest)
							.flatMap(authResponse -> {
								
								String jwt = authResponse.getToken();
								
                                ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                                        .httpOnly(true)
                                        .path("/")
                                        .maxAge(Duration.ofHours(1))
                                        .build();

                                exchange.getResponse().addCookie(cookie);
                                
                                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                                exchange.getResponse().getHeaders().setLocation(URI.create("/ui/patients"));
                                
                                return exchange.getResponse().setComplete();
								
							})
							.onErrorResume(e -> {
								
	                             exchange.getResponse().setStatusCode(HttpStatus.FOUND);
	                             exchange.getResponse().getHeaders().setLocation(URI.create("/ui/login"));
	                             
	                             return exchange.getResponse().setComplete();
								
							});
					
				});
		
	}
	
}
