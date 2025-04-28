package com.medilabo.gateway_server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medilabo.gateway_server.dtos.UserDTO;
import com.medilabo.gateway_server.proxys.MServiceUserProxy;

import reactor.core.publisher.Mono;

/**
 * 
 */
@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService  {

	@Autowired
	MServiceUserProxy userProxy;
	
	@Override
	public Mono<UserDetails> findByUsername(String username) {

		try {
			
			UserDTO user = userProxy.getOneUserByUsername(username);
			
			if(user == null) {
				
				throw new UsernameNotFoundException("User not found with username : " + username);
				
			}
			
			List<String> roles = Arrays.asList(user.getRole());
			
			UserDetails userDetails = new User(
					user.getUsername(),
					user.getPassword(),
					getGrantedAuthority(roles)
					);
			
			return Mono.just(userDetails);
			
		} catch(Exception e) {
			
			return Mono.error(new UsernameNotFoundException("Error retrieving user: " + username, e));
			
		}
		
	}
	
	/**
	 * @param roles
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthority(List<String> roles){
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(String role : roles) {
			
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			
		}
		
		return authorities;
		
	}
	
}
