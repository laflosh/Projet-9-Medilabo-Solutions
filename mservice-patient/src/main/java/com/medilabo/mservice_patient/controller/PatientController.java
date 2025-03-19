package com.medilabo.mservice_patient.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.mservice_patient.model.Patient;
import com.medilabo.mservice_patient.service.PatientService;

/**
 * Controller for managing the http requests about patient domain
 */
@RestController
@RequestMapping("/api")
public class PatientController {

	private final static Logger log = LogManager.getLogger(PatientController.class);

	@Autowired
	PatientService patientService;

	/**
	 * Fetching all the patients in the database
	 *
	 * @return A list of patients
	 */
	@GetMapping("/patients")
	public ResponseEntity<?> getAllPatients(){

		log.info("Trying to fetch all the patient in the database");

		List<Patient> patients = patientService.getAllPatients();

		if(patients != null) {

			return ResponseEntity.ok(patients);

		}else {

			return ResponseEntity.status(404).body("Patients not found");

		}

	}

	/**
	 * Fetching One patient depending of the id in parameter
	 *
	 * @param id of the patient
	 * @return A patient
	 */
	@GetMapping("/patients/{id}")
	public ResponseEntity<?> getOnePatientById(@PathVariable("id") int id){

		log.info("Trying to fetch one patient depending of the id : {}", id);

		Patient patient = patientService.getOnePatientById(id);

		if(patient != null) {

			return ResponseEntity.ok(patient);

		}else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");

		}

	}

	/**
	 * Add a new patient in the database
	 *
	 * @param A new patient
	 * @return The created patient
	 */
	@PostMapping("/patients")
	public ResponseEntity<?> addNewPatient(@RequestBody Patient patient){

		try {

			log.info("Trying to add a new patient in the database : {}", patient.toString());

			Patient newPatient = patientService.addNewPatient(patient);

			return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during saving a new patient");

		}

	}

	/**
	 * Update an existing patient in the database with the new data modified
	 *
	 * @param An updatePatient
	 * @return The updatedPatient
	 */
	@PutMapping("/patients")
	public ResponseEntity<?> updateExistingPatient(@RequestBody Patient updatePatient){

		try {

			log.info("Trying to update an existong patient in the database : {}", updatePatient.toString());

			Patient updatedPatient = patientService.updateExistingPatient(updatePatient);

			return ResponseEntity.status(HttpStatus.CREATED).body(updatedPatient);

		} catch(Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during update of the patient");

		}

	}

	/**
	 * Delete an existing patient in the database depending of the id in parameter
	 *
	 * @param id of the patient
	 * @return true if deleted
	 */
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<?> deleteExistingPatientById(@PathVariable int id){

		log.info("Trying to delete an existing patient in the database depending of the id : {}", id);

		boolean isDeleted = patientService.deleteExistingPatientById(id);

		if(isDeleted) {

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}

	}

}