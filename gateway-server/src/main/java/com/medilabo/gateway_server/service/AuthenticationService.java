package com.medilabo.gateway_server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.dtos.AuthenticationResponse;
import com.medilabo.gateway_server.proxys.MServiceUserProxy;
import com.medilabo.gateway_server.utils.JwtUtils;

import reactor.core.publisher.Mono;

/**
 * Service class for all the methods to managing the authentication of users
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
	 * Login user with username/password and the authentication manager,
	 * if the user are correctly login, create a token with user's data
	 * 
	 * @param request object
	 * @return response object
	 */
	public Mono<AuthenticationResponse> authentication(AuthenticationRequest request) {

		log.info("Login the user with username : {}", request.getUsername());
		
		return reactiveAuthenticationManager.authenticate(
				
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())	
				
		).flatMap(authentication -> {
			
			log.info("Login succesfull");
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername();
			String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                    .orElse("USER");
			
			String token = jwtUtils.generateToken(username, role);
			
			if(token == null) {
				
				log.error("Error during the creation of the token");
				
				return Mono.error(new RuntimeException("Error during the creation of the token"));
				
			}
			
			AuthenticationResponse authResponse = new AuthenticationResponse();
			authResponse.setToken(token);
			authResponse.setUsername(username);
			authResponse.setAuthorities(role);
			
			return Mono.just(authResponse);
			
		}).onErrorResume(e -> {
			
            log.error("Authentication error", e);
            return Mono.error(new RuntimeException("Error during authentication"));
			
		});
		
	}

}
