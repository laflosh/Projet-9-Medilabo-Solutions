package com.medilabo.gateway_server.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.medilabo.gateway_server.service.CustomUserDetailsService;
import com.medilabo.gateway_server.utils.JwtUtils;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * 
 */
@Component
public class JwtFilter implements WebFilter {

	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	/**
	 *
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		String path = exchange.getRequest().getPath().value();
		
	    // public routes
	    if (path.startsWith("/auth/login") || 
	        path.startsWith("/ui") || 
	        path.startsWith("/ui/login/") || 
	        path.startsWith("/css/") || 
	        path.startsWith("/js/") || 
	        path.startsWith("/images/") || 
	        path.equals("/") || 
	        path.startsWith("/api/users/username/") || 
	        path.startsWith("/api/users/mail/")) {
	        return chain.filter(exchange);
	    }
		
		String authHeaders = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
	    
		String username;
		String jwt = authHeaders.substring(7);
		
		if(authHeaders != null && authHeaders.startsWith("Bearer ")) {

			username = jwtUtils.extractUsername(jwt);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
	            return userDetailsService.findByUsername(username)
	                    .flatMap(userDetails -> {
	                    	
	                        if (jwtUtils.validateToken(jwt, userDetails)) {
	                        	
	                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
	                                    userDetails, null, userDetails.getAuthorities());
	                            
	                            SecurityContextHolder.getContext().setAuthentication(auth);
	                            
	                        }
	                        
	                        return chain.filter(exchange);
	                        
	                    })
	                    .onErrorResume(e -> {
	                    	
	                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	                        return exchange.getResponse().setComplete();
	                        
	                    });
			
			}
			
		}
		
		return chain.filter(exchange);
		
	}

}
