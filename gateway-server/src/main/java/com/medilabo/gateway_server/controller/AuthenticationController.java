package com.medilabo.gateway_server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.dtos.AuthenticationResponse;
import com.medilabo.gateway_server.service.AuthenticationService;

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
	 * 
	 * @param request object
	 * @return response object
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
		
		try {
			
			log.info("Trying to login the user with the username {}", request.getUsername());	
			
			Mono<AuthenticationResponse> response = authService.authentication(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		} catch (Exception e) {
			
			log.info("Error during the authentication : {}", e);
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username/password in request");
			
		}
		
	}
	
}
