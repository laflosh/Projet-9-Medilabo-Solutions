package com.medilabo.mservice_auth.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_auth.dto.AuthenticationRequest;
import com.medilabo.mservice_auth.dto.AuthenticationResponse;
import com.medilabo.mservice_auth.dto.UserDTO;
import com.medilabo.mservice_auth.proxy.MServiceUserProxy;
import com.medilabo.mservice_auth.util.JwtUtils;

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
	AuthenticationManager authenticationManager;
	
	/**
	 * @param request
	 * @return
	 */
	public AuthenticationResponse authentication(AuthenticationRequest request) {

		try {
			
			authenticationManager.authenticate(
					
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
