package com.medilabo.gateway_server.controller;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.medilabo.gateway_server.dtos.AuthenticationRequest;
import com.medilabo.gateway_server.dtos.AuthenticationResponse;
import com.medilabo.gateway_server.service.AuthenticationService;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AuthenticationControllerTest {

	@Autowired
	WebTestClient webTestClient;
	
    @MockBean
    AuthenticationService authenticationService;
	
	@Test
	void postLoginAndShouldAuthenticateAndSetCookieOnSucces() {
		
		AuthenticationRequest authRequest = new AuthenticationRequest("testUser", "testPassword");
        AuthenticationResponse mockResponse = new AuthenticationResponse("mock.jwt.token","testUser","USER");

        Mockito.when(authenticationService.authentication(Mockito.any()))
                .thenReturn(Mono.just(mockResponse));
		
		//Testing request
		webTestClient.post()
			.uri("/auth/api/login")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(authRequest)
			.exchange()
			.expectHeader().valueMatches("Set-Cookie", "jwt=mock\\.jwt\\.token.*");
		
	}
	
	@Test
	public void shouldLogoutAndClearJwtCookie() {
		
		//Testing request
		webTestClient.get()
			.uri("/auth/logout")
			.cookie("jwt", "fake.jwt.token.here")
			.exchange()
			.expectStatus().is3xxRedirection()
			.expectHeader().valueMatches("Location", "/ui")
			.expectCookie().maxAge("jwt", Duration.ZERO);
		
	}

}
