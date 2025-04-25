package com.medilabo.mservice_auth.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.mservice_auth.dto.AuthenticationRequest;
import com.medilabo.mservice_auth.dto.AuthenticationResponse;
import com.medilabo.mservice_auth.service.AuthenticationService;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	private final static Logger log = LogManager.getLogger(AuthenticationController.class);
	
	@Autowired
	AuthenticationService authService;
	
	/**
	 * @param request
	 * @return
	 */
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
		
		try {
			
			AuthenticationResponse response = authService.authentication(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username/password in request");
			
		}
		
	}
	
}
