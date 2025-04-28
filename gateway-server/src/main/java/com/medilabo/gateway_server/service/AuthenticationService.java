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
import com.medilabo.gateway_server.dtos.UserDTO;
import com.medilabo.gateway_server.proxys.MServiceUserProxy;
import com.medilabo.gateway_server.utils.JwtUtils;

import reactor.core.publisher.Mono;

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
	public Mono<AuthenticationResponse> authentication(AuthenticationRequest request) {

		return reactiveAuthenticationManager.authenticate(
				
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())	
				
		).flatMap(authentication -> {
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername();
			String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                    .orElse("USER");
			
			String token = jwtUtils.generateToken(username, role);
			
			if(token == null) {
				
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
