package com.medilabo.gateway_server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.dtos.AuthenticationResponse;
import com.medilabo.gateway_server.dtos.UserDTO;
import com.medilabo.gateway_server.proxys.MServiceUserProxy;
import com.medilabo.gateway_server.utils.JwtUtils;

/**
 * 
 */
@Service
public class AuthenticationService {

	private final static Logger log = LogManager.getLogger(AuthenticationService.class);
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	MServiceUserProxy userProxy;
	
	@Autowired
	ReactiveAuthenticationManager reactiveAuthenticationManager;
	
	/**
	 * @param request
	 * @return
	 */
	public AuthenticationResponse authentication(AuthenticationRequest request) {

		try {
			
			reactiveAuthenticationManager.authenticate(
					
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())	
					
			);
			
			UserDTO user = userProxy.getOneUserByUsername(request.getUsername());
			
			String token = jwtUtils.generateToken(user.getUsername(), user.getRole());
			
			if(token == null) {
				
				throw new RuntimeException("Error during the creation of the token");
				
			}
			
			AuthenticationResponse authResponse = new AuthenticationResponse();
			authResponse.setToken(token);
			authResponse.setUsername(user.getUsername());
			authResponse.setMail(user.getMail());
			authResponse.setRole(user.getRole());
			
			return authResponse;
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			throw new RuntimeException("Error during authentication");
			
		}
		
	}

}
