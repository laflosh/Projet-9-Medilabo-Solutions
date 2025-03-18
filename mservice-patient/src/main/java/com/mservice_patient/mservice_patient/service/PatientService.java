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

	public List<Patient> getAllPatients() {
		
		Iterable<Patient> patients = patientRepository.findAll();
		
		return StreamSupport.stream(patients.spliterator(), false).collect(Collectors.toList());
		
	}
	
}
