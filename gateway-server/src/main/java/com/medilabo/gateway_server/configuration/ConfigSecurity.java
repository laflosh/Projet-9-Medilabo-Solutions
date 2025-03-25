package com.medilabo.gateway_server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 */
@Configuration
@EnableWebSecurity
public class ConfigSecurity {

	/**
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/ui").permitAll()
				.requestMatchers("/ui/patients/**").authenticated()
				.requestMatchers("/api/patients/**").authenticated()
				)
			.httpBasic(httpBasic -> httpBasic
					.realmName("MediLabo")
					)
			.logout(logout -> logout
					.logoutUrl("/logout")
        			.logoutSuccessUrl("/ui")
        			.invalidateHttpSession(true)
        			.deleteCookies("JSESSIONID")
				);
			
		return http.build();
		
	}
	
	/**
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsInMemory() {
		
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
		
	}
	
	/**
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
}
