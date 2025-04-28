package com.medilabo.gateway_server.dtos;

/**
 * 
 */
public class AuthenticationResponse {

	private String token;
	
	private String username;
	
	private String authorities;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
}
