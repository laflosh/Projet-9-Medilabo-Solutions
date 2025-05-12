package com.medilabo.gateway_server.controller;

import java.net.URI;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.dtos.AuthenticationResponse;
import com.medilabo.gateway_server.service.AuthenticationService;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * Controller class for managing all the http requests about the authentication
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final static Logger log = LogManager.getLogger(AuthenticationController.class);
	
	@Autowired
	AuthenticationService authService;
	
	/**
	 * Login an user and return a token
	 * Set the token into a cookie for the clientui service
	 * 
	 * @param request object
	 * @return response object
	 */
	@PostMapping("/api/login")
	public Mono<ResponseEntity<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request){
		
		log.info("Trying to login the user with the username {}", request.getUsername());
		
		return authService.authentication(request)
				.map(authResponse -> {
					
					ResponseCookie jwtCookie = ResponseCookie.from("jwt", authResponse.getToken())
							.httpOnly(true)
							.secure(false)
							.path("/")
							.maxAge(Duration.ofHours(1))
							.build();
					
					return ResponseEntity.status(HttpStatus.OK)
							.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
							.body(authResponse);
					
				})
				.onErrorResume(e -> {
					
					log.info("Error during the authentication : {}", e);
					
					return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
					
				});
		
	}
	
	/**
	 * Delete the cookie who contains the jwt token and redirect 
	 * the user to the home page
	 * 
	 * @param response
	 * @return
	 */
	@GetMapping("/logout")
	public Mono<Void> logout(ServerHttpResponse response){
		
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();

        response.addCookie(deleteCookie);

        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().setLocation(URI.create("/ui"));
        
        return response.setComplete();
		
	}
	
}
