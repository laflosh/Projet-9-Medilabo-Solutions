package com.medilabo.gateway_server.dtos;

/**
 * Entity to set all properties for the response from the server after the authentication of the user
 */
public class AuthenticationResponse {

	private String token;
	
	private String username;
	
	private String authorities;
	
	public AuthenticationResponse() {
		
	}

	public AuthenticationResponse(String token, String username, String authorities) {
		super();
		this.token = token;
		this.username = username;
		this.authorities = authorities;
	}

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
