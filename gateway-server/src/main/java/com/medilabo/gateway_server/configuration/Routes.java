package com.medilabo.gateway_server.configuration;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 
 */
@Configuration
public class Routes {
	
	/**
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> clientuiServiceRoute(){
		
		return GatewayRouterFunctions.route("mservice-clientui")
				.route(RequestPredicates.path("/**"), HandlerFunctions.http("http://localhost:9001"))
				.build();
		
	}

	/**
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> patientServiceRoute(){
		
		return GatewayRouterFunctions.route("mservice-patient")
				.route(RequestPredicates.path("/api/patients/**"), HandlerFunctions.http("http://localhost:9002"))
				.build();
		
	}
	
}
