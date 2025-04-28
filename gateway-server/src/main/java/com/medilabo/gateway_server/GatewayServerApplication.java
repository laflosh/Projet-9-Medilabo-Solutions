package com.medilabo.gateway_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.gateway_server")
public class GatewayServerApplication {

	private final static Logger log = LogManager.getLogger("GatewayServerApplication");
	
	public static void main(String[] args) {
		log.info("Initialize gateway server");
		SpringApplication.run(GatewayServerApplication.class, args);
	}

}
