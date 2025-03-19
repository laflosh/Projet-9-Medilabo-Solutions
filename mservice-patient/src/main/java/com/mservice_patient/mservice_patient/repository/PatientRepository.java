package com.mservice_patient.mservice_patient.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mservice_patient.mservice_patient.model.Patient;

/**
 * Interface to interact with the MySql database for the mservice-patient
 */
@Repository
public interface PatientRepository extends CrudRepository<Patient,Integer>{

	
	
}
