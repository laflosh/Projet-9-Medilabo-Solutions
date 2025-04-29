package com.medilabo.gateway_server.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Class for all the methods about Jwt token, methods to generate a token and methods for manipulate and parse a token
 */
@Component
public class JwtUtils {

	@Value("${app.secret-key}")
	private String secretKey;
	
	@Value("${app.expiration-time}")
	private Long expirationTime;
	
	/**
	 * Configuration of the claims and call the method to create a token
	 * 
	 * @param username of an user
	 * @param role of an user
	 * @return token
	 */
	public String generateToken(String username, String role){
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		
		return createToken(claims, username);
		
	}

	/**
	 * Create a token with all the user informations like username, claims and expiration duration
	 * 
	 * @param claims
	 * @param subject
	 * @return token
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
	 * Sign the secret key with a signature algorithm
	 * 
	 * @return secret key
	 */
	private Key getSignKey() {

		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
		
	}
	
	/**
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		
		String username = extractUsername(token);
		
		if(username.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * @param token
	 * @return
	 */
	public String extractUsername(String token) {
		
		return extractClaims(token, Claims::getSubject);
		
	}
	
	/**
	 * @param token
	 * @return
	 */
	public Date extractExpirationDate(String token) {
		
		return extractClaims(token, Claims::getExpiration);
		
	}
	
	/**
	 * @param token
	 * @return
	 */
	public String extractRole(String token) {
		
	    return extractAllClaims(token).get("role", String.class);
	    
	}
	
	/**
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		
		final Claims claims = extractAllClaims(token);
		
		return claimsResolver.apply(claims);
		
	}
	
	/**
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {

		JwtParser parser = Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build();
		
		return   parser
				.parseClaimsJws(token)
				.getBody();
		
	}

	/**
	 * @param token
	 * @return
	 */
	private boolean isTokenExpired(String token) {
		
		return extractExpirationDate(token).before(new Date());
		
	}
	
}
