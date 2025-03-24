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
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return GatewayRouterFunctions.route()
                .route(RequestPredicates.path("/api/patients/**"), 
                        HandlerFunctions.http("http://localhost:9002")) //mservice-patient
                .route(RequestPredicates.path("/ui/**"), 
                        HandlerFunctions.http("http://localhost:9001")) //mservice-clientui
                .build();
    }
	
}
