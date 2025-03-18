package com.mservice_patient.mservice_patient.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mservice_patient.mservice_patient.model.Patient;
import com.mservice_patient.mservice_patient.service.PatientService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class PatientController {

	private final static Logger log = LogManager.getLogger(PatientController.class);
	
	@Autowired
	PatientService patientService;
	
	@GetMapping("/patients")
	public ResponseEntity<?> getAllPatients(){
		
		List<Patient> patients = patientService.getAllPatients();
		
		if(patients != null) {
		
			return ResponseEntity.ok(patients);
			
		}else {
			
			return ResponseEntity.status(404).body("Patients not found");
			
		}
		
	}
	
}
