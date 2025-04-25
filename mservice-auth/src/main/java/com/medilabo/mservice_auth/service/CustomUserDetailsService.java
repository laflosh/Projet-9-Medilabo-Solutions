package com.medilabo.mservice_auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_auth.dto.UserDTO;
import com.medilabo.mservice_auth.proxy.MServiceUserProxy;

/**
 * 
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	MServiceUserProxy userProxy;

	/**
	 *
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDTO user = userProxy.getOneUserByUsername(username);
		
		if(user == null) {
			
			throw new UsernameNotFoundException("User not found with username : " + username);
			
		}
		
		List<String> roles = Arrays.asList(user.getRole());
		
		return new User(
				user.getUsername(),
				user.getPassword(),
				getGrantedAuthority(roles)
				);
		
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
