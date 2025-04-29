package com.medilabo.gateway_server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.medilabo.gateway_server.service.CustomUserDetailsService;

/**
 * Configuration's class for managing the beans of the security for the application throught the gateway
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ConfigSecurity {

	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	/**
	 * Filterchain for managing the permition of url, managing the method of authentication of the application
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeExchange(exchange -> exchange
					.pathMatchers("/").permitAll()
					.pathMatchers("/ui").permitAll()
					.pathMatchers("/static/**").permitAll()
					.pathMatchers("/css/**", "/js/**", "/images/**").permitAll()
					.pathMatchers("/favicon.ico").permitAll()
					.pathMatchers("/api/users/username/**","/api/users/mail/**").permitAll()
					.pathMatchers("/auth/login").permitAll()
					.pathMatchers("/ui/patients/**").authenticated()
					.pathMatchers("/ui/notes/**").authenticated()
					.pathMatchers("/ui/users/**").authenticated()
					.pathMatchers("/api/patients/**").authenticated()
					.pathMatchers("/api/notes/**").authenticated()
					.pathMatchers("/api/users/**").authenticated()
					)
			.httpBasic(Customizer.withDefaults())
			.logout(logout -> logout
					.logoutUrl("/logout")
				);
			
		return http.build();
		
	}
	
	/**
	 * Configuration of the authentication manager for handling user authentication
	 * 
	 * @param http
	 * @param bCryptPasswordEncoder
	 * @return
	 * @throws Exception
	 */
	@Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder passwordEncoder) {
    	
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(customUserDetailsService);
        
        authenticationManager.setPasswordEncoder(passwordEncoder);
        
        return authenticationManager;
    }
	
	/**
	 * Bcrypt encoder bean for encoding the password of the in memory user
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
}
