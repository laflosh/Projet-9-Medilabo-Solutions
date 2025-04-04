package com.medilabo.mservice_note;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MserviceNoteApplication {

	private final static Logger log = LogManager.getLogger("MserviceNoteApplication");
	
	public static void main(String[] args) {
		
		log.info("Initialize mservice-note server");
		SpringApplication.run(MserviceNoteApplication.class, args);
		
	}

}
