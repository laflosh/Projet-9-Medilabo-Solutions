package com.medilabo.gateway_server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medilabo.gateway_server.proxys.MServiceUserProxy;

import reactor.core.publisher.Mono;

/**
 * Service class to create userdetails with the user data
 */
@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService  {

	private final static Logger log = LogManager.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	MServiceUserProxy userProxy;
	
	/**
	 * Return an userdetails based on an existing user in the database
	 * 
	 * @param username of the user
	 * @return user details
	 */
	@Override
	public Mono<UserDetails> findByUsername(String username) {

		log.info("Fetching an user in the databse with the username : {}", username);
		
		return userProxy.getOneUserByUsername(username)
				.flatMap(user -> {
			
					if(user == null) {
						
						throw new UsernameNotFoundException("User not found with username : " + username);
						
					}
					
					List<String> roles = Arrays.asList(user.getRole());
					
					log.info("User found, making the user details");
					
					UserDetails userDetails = new User(
							user.getUsername(),
							user.getPassword(),
							getGrantedAuthority(roles)
							);
					
					return Mono.just(userDetails);
					
		}).onErrorResume(e -> {
			
			return Mono.error(new UsernameNotFoundException("Error retrieving user: " + username, e));
			
		});
		
	}
	
	/**
	 * Create a list of grand authorities based of the roles of the user
	 * 
	 * @param roles
	 * @return authorities
	 */
	private List<GrantedAuthority> getGrantedAuthority(List<String> roles){
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(String role : roles) {
			
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			
		}
		
		return authorities;
		
	}
	
}
