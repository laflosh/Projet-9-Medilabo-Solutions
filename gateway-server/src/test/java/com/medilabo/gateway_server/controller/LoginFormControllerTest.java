package com.medilabo.gateway_server.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.dtos.AuthenticationResponse;
import com.medilabo.gateway_server.service.AuthenticationService;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LoginFormControllerTest {

	@Autowired
	WebTestClient webTestClient;
	
    @MockBean
    AuthenticationService authenticationService;
    
    private final String VALID_USERNAME = "testuser";
    private final String VALID_PASSWORD = "testpass";
    private final String JWT_TOKEN = "fake-jwt-token";
	
    @BeforeEach
    void setUp() {
    	
    	AuthenticationResponse authResponse = new AuthenticationResponse();
    	authResponse.setToken(JWT_TOKEN);
    	
    	Mockito.when(authenticationService.authentication(Mockito.any(AuthenticationRequest.class))).thenReturn(Mono.just(authResponse));
    	
    }
    
	@Test
	public void loginFromHtmlFormShouldSetUpCookieAndRedirect() {
	
		//Testing request
		webTestClient.post()
			.uri("/auth/login")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.bodyValue("username=" + VALID_USERNAME + "&password=" + VALID_PASSWORD)
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.FOUND)
	        .expectHeader().value("Location", value -> 
	        	Assertions.assertThat(value).isEqualTo("/ui/patients"))
	        .expectHeader().value("Set-Cookie", value -> {
	        	Assertions.assertThat(value).contains("jwt=" + JWT_TOKEN);
	        	Assertions.assertThat(value).contains("HttpOnly");
	        	Assertions.assertThat(value).contains("Path=/");
        });
		
	}

}
