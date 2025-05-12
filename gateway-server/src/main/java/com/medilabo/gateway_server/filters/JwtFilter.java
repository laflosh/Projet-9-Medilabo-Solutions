package com.medilabo.gateway_server.filters;

import java.net.URI;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.medilabo.gateway_server.service.CustomUserDetailsService;
import com.medilabo.gateway_server.utils.JwtUtils;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * Personnal filter to validate the jwt token in the request throught the gateway
 */
@Component
public class JwtFilter implements WebFilter {

	private final static Logger log = LogManager.getLogger(JwtFilter.class);
	
	private String username = null;
	private String jwt = null;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	/**
	 * Filter method for jwt token before the filter chain :
	 * Check if the route from the request is public
	 * Extract the jwt token from the headers or the cookie in the request
	 * With the class JwtUtils validate the token with the claims in it
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		System.out.println("JwtFilter executed for request: " + exchange.getRequest().getURI());
		
		String path = exchange.getRequest().getPath().value();
		String authHeaders = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
	    if (checkPublicRoute(path)) {
	    	
	        return chain.filter(exchange);
	        
	    }

	    if(authHeaders != null) {
	    	extractTokenAndUsernameFromHeaders(authHeaders, exchange);
	    } else {
	    	extractTokenAndUsernameFromCookie(exchange);
	    }
	    
		if(username != null) {
			
			final String JWT = jwt;
			
            return userDetailsService.findByUsername(username)
                    .flatMap(userDetails -> {
                    	
                        if (jwtUtils.validateToken(JWT, userDetails)) {
                        	
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            
                            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            	    .header("Authorization", "Bearer " + JWT)
                            	    .build();

                            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                            
                            return chain.filter(mutatedExchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                            
                        } else if(jwtUtils.isTokenExpired(JWT)) {
                        	
                        	System.out.println("Token expiré redirection en cours");
                        	
                    	    exchange.getResponse()
                            .addCookie(ResponseCookie.from("jwt", "")
                                .path("/")
                                .httpOnly(true)
                                .maxAge(Duration.ZERO)
                                .build());
                    		
                    		exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                    		exchange.getResponse().getHeaders().setLocation(URI.create("/ui/login"));
                    		
                    		return exchange.getResponse().setComplete();
                        	
                        }
                        
						return chain.filter(exchange);
                        
                    })
                    .onErrorResume(e -> {
                    	
                    	log.info("pas autorisé venant du filtre jwt");
                    	
                	    exchange.getResponse()
                        .addCookie(ResponseCookie.from("jwt", "")
                            .path("/")
                            .httpOnly(true)
                            .maxAge(Duration.ZERO)
                            .build());
                		
                		exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                		exchange.getResponse().getHeaders().setLocation(URI.create("/ui/login"));
                    	
                        return exchange.getResponse().setComplete();
                        
                    });
		
		}
		
		return chain.filter(exchange);
		
	}
	
	/**
	 * Boolean method to check if the path of the request is public or not
	 * 
	 * @param path
	 * @return true if public route
	 */
	private boolean checkPublicRoute(String path) {
		
		if(path.startsWith("/auth/") || 
	        path.equals("/ui") || 
	        path.startsWith("/ui/login/") || 
	        path.startsWith("/css/") || 
	        path.startsWith("/js/") || 
	        path.startsWith("/images/") || 
	        path.equals("/") || 
	        path.startsWith("/api/users/username/") || 
	        path.startsWith("/api/users/mail/")) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * Extract the jwt token form headers informations
	 * and set the token in the global var. jwt
	 * 
	 * @param authHeaders
	 * @param exchange
	 */
	private void extractTokenAndUsernameFromHeaders(String authHeaders, ServerWebExchange exchange) {
		
		if(authHeaders != null && authHeaders.startsWith("Bearer ")) {

			jwt = authHeaders.substring(7);
			username = jwtUtils.extractUsername(jwt);
			
		} 
		
	}
	
	/**
	 * Extract the jwt token from the cookie informations
	 * and set the token in the global var. jwt
	 * 
	 * @param exchange
	 */
	private void extractTokenAndUsernameFromCookie(ServerWebExchange exchange) {
		
		HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("jwt");
		
		if(jwtCookie != null) {
			
			String raw = jwtCookie.getValue();
			
			if(raw.startsWith("jwt=")) {
				raw = raw.substring(4);
			}
			
			log.info("jwt in method : {}", raw);
			
			jwt = raw;
			username = jwtUtils.extractUsername(jwt);
			
		}
		
	}

}
