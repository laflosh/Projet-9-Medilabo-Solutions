package com.medilabo.mservice_patient.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_patient.model.Patient;
import com.medilabo.mservice_patient.repository.PatientRepository;

/**
 * Service for managing all the method about the logic on patient domain
 */
@Service
public class PatientService {

	private final static Logger log = LogManager.getLogger(PatientService.class);

	@Autowired
	PatientRepository patientRepository;

	/**
	 * @return A list of patients
	 */
	public List<Patient> getAllPatients() {

		log.info("Fetching all the patients in the database");

		Iterable<Patient> patients = patientRepository.findAll();

		return StreamSupport.stream(patients.spliterator(), false).collect(Collectors.toList());

	}

	/**
	 * @param id of the patient
	 * @return A patient
	 */
	public Patient getOnePatientById(int id) {

		log.info("Fetching one patient depending of the id : {}", id);

		Patient patient = patientRepository.findById(id)
							.orElseThrow(() -> new RuntimeException("Patient not found"));

		return patient;

	}

	/**
	 * @param New patient
	 * @return Created patient
	 */
	public Patient addNewPatient(Patient patient) {

		log.info("Adding a new patient in the database : {}", patient.toString());

		Patient newPatient = patientRepository.save(patient);

		return newPatient;

	}

	/**
	 * @param updatePatient
	 * @return updated Patient
	 */
	public Patient updateExistingPatient(Patient updatePatient) {

		log.info("Updating an existing patient in the database : {}", updatePatient.toString());

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

	/**
	 * @param id of the patient
	 * @return true if deleted
	 */
	public boolean deleteExistingPatientById(int id) {

		log.info("Delete an existing patient in the database with the id : {}", id);

		if(patientRepository.existsById(id)) {

			patientRepository.deleteById(id);

			return true;

		}

		return false;

	}

}
