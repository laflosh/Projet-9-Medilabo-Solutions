package com.mservice_patient.mservice_patient.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mservice_patient.mservice_patient.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Integer>{

	
	
}
