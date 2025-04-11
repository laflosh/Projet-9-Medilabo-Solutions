package com.medilabo.gateway_server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuration's class for managing the bean of the security for the application throught the gateway
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ConfigSecurity {

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
					.pathMatchers("/ui/patients/**").authenticated()
					.pathMatchers("/api/patients/**").authenticated()
					.pathMatchers("/api/notes/**").authenticated()					)
			.httpBasic(Customizer.withDefaults())
			.logout(logout -> logout
					.logoutUrl("/logout")
				);
			
		return http.build();
		
	}
	
	/**
	 * Create one User in memory for the basic auth for the application
	 * 
	 * @return An User
	 */
	@Bean
	public MapReactiveUserDetailsService userDetailsInMemory() {
		
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		
		return new MapReactiveUserDetailsService(user);
		
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
