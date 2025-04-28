package com.medilabo.gateway_server.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 */
@Component
public class JwtUtils {

	@Value("${app.secret-key}")
	private String secretKey;
	
	@Value("${app.expiration-time}")
	private Long expirationTime;
	
	/**
	 * @param username
	 * @param role
	 * @return
	 */
	public String generateToken(String username, String role){
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		
		return createToken(claims, username);
		
	}

	/**
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
		
	}

	/**
	 * @return
	 */
	private Key getSignKey() {

		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
		
	}
	
}
