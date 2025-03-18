package com.mservice_patient.mservice_patient.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	/**
	 * @return
	 */
	@GetMapping("/patients")
	public ResponseEntity<?> getAllPatients(){
		
		List<Patient> patients = patientService.getAllPatients();
		
		if(patients != null) {
		
			return ResponseEntity.ok(patients);
			
		}else {
			
			return ResponseEntity.status(404).body("Patients not found");
			
		}
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/patients/{id}")
	public ResponseEntity<?> getOnePatientById(@PathVariable("id") int id){

		Patient patient = patientService.getOnePatientById(id);
		
		if(patient != null) {
			
			return ResponseEntity.ok(patient);
			
		}else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
			
		}
		
	}
	
	/**
	 * @param patient
	 * @return
	 */
	@PostMapping("/patients")
	public ResponseEntity<?> addNewPatient(@RequestBody Patient patient){
		
		try {
			
			Patient newPatient = patientService.addNewPatient(patient);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during saving a new patient");
			
		}
		
	}
	
	/**
	 * @param updatePatient
	 * @return
	 */
	@PutMapping("/patients")
	public ResponseEntity<?> updateExistingPatient(@RequestBody Patient updatePatient){
		
		try {
			
			Patient updatedPatient = patientService.updateExistingPatient(updatePatient);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedPatient);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during update of the patient");
			
		}
		
	}
	
}
