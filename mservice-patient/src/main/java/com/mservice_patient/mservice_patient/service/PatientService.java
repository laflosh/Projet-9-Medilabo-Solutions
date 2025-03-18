package com.mservice_patient.mservice_patient.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mservice_patient.mservice_patient.model.Patient;
import com.mservice_patient.mservice_patient.repository.PatientRepository;

/**
 * 
 */
@Service
public class PatientService {

	private final static Logger log = LogManager.getLogger(PatientService.class);
	
	@Autowired
	PatientRepository patientRepository;

	/**
	 * @return
	 */
	public List<Patient> getAllPatients() {
		
		Iterable<Patient> patients = patientRepository.findAll();
		
		return StreamSupport.stream(patients.spliterator(), false).collect(Collectors.toList());
		
	}

	/**
	 * @param id
	 * @return
	 */
	public Patient getOnePatientById(int id) {

		Patient patient = patientRepository.findById(id)
							.orElseThrow(() -> new RuntimeException("Patient not found"));
		
		return patient;
		
	}

	/**
	 * @param patient
	 * @return
	 */
	public Patient addNewPatient(Patient patient) {
		
		Patient newPatient = patientRepository.save(patient);
		
		return newPatient;
		
	}

	/**
	 * @param updatePatient
	 * @return
	 */
	public Patient updateExistingPatient(Patient updatePatient) {

		Patient existingPatient = getOnePatientById(updatePatient.getId());
		
		if(updatePatient.getName() != existingPatient.getName()) {
			existingPatient.setName(updatePatient.getName());
		}
		
		if(updatePatient.getFirstName() != existingPatient.getFirstName()) {
			existingPatient.setFirstName(updatePatient.getFirstName());
		}
		
		if(updatePatient.getBirthDate() != existingPatient.getBirthDate()) {
			existingPatient.setBirthDate(updatePatient.getBirthDate());
		}
		
		if(updatePatient.getGender() != existingPatient.getGender()) {
			existingPatient.setGender(updatePatient.getGender());
		}
		
		if(updatePatient.getAddress() != existingPatient.getAddress()) {
			existingPatient.setAddress(updatePatient.getAddress());
		}
		
		if(updatePatient.getPhoneNumber() != existingPatient.getPhoneNumber()) {
			existingPatient.setPhoneNumber(updatePatient.getPhoneNumber());
		}
		
		return patientRepository.save(existingPatient);
		
	}
	
}
