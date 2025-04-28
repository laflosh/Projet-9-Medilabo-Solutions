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

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final static Logger log = LogManager.getLogger(AuthenticationController.class);
	
	@Autowired
	AuthenticationService authService;
	
	/**
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
		
		try {
			
			Mono<AuthenticationResponse> response = authService.authentication(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username/password in request");
			
		}
		
	}
	
}
