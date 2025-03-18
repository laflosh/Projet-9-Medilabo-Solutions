package com.mservice_patient.mservice_patient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MservicePatientApplication {

	private final static Logger log = LogManager.getLogger("MservicePatientApplication");
	
	public static void main(String[] args) {
		
		log.info("Initialize mservice-patient server");
		SpringApplication.run(MservicePatientApplication.class, args);
		
	}

}
