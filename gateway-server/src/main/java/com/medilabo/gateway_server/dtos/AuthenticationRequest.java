package com.medilabo.gateway_server.dtos;

/**
 * Entity for authentication request with all properties for authentication
 */
public class AuthenticationRequest {

	private String username;
	
	private String password;

	public AuthenticationRequest() {
		
	}

	public AuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
