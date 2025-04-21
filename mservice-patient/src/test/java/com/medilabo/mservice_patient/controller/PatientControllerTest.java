package com.medilabo.mservice_patient.controller;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.mservice_patient.model.Patient;
import com.medilabo.mservice_patient.repository.PatientRepository;
import com.medilabo.mservice_patient.service.PatientService;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

	final String TEST_PATIENT_PREFIX = "test_patient";
	List<Patient> testPatients = new ArrayList<>();

	@Autowired
	PatientRepository patientRepository;
	
	@Mock
	PatientService patientService;
	
	@InjectMocks
	PatientController patientController;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(){
		
		//Creating a patient test
		Patient testPatient = new Patient();
		testPatient.setName(TEST_PATIENT_PREFIX + "1");
		testPatient.setFirstName("test");
		testPatient.setBirthDate("00-00-0000");
		testPatient.setGender("M");
		testPatient.setAddress("address");
		testPatient.setPhoneNumber("000-000-0000");

		//Adding to the database
		patientRepository.save(testPatient);
		testPatients.add(testPatient);

	}

	@AfterEach
	public void tearDown() {

		//Delete all the test entity in the database
		testPatients.forEach(testPatient -> {
			if(patientRepository.existsById(testPatient.getId())) {
				patientRepository.deleteById(testPatient.getId());
			}
		});
		testPatients.clear();

	}

	@Test
	public void getAllPatientInTheDatabaseAndReturnOk() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/patients"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));

	}

	@Test
	public void getOnePatientInTheDatabaseandReturnOk() throws Exception {

		//Get testing patient
		Patient patient = testPatients.get(0);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/patients/" + patient.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));

	}

	@Test
	public void addNewPatientInDatabaseAndReturnCreated() throws Exception {

		//Create a new patient
		Patient testPatient = new Patient();
		testPatient.setName(TEST_PATIENT_PREFIX + "2");
		testPatient.setFirstName("test test");
		testPatient.setBirthDate("00-00-0000");
		testPatient.setGender("F");
		testPatient.setAddress("address address");
		testPatient.setPhoneNumber("000-000-0000");

		String patientAsString = objectMapper.writeValueAsString(testPatient);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
					.contentType(MediaType.APPLICATION_JSON).content(patientAsString))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
		testPatients.add(testPatient);

	}

	@Test
	public void addPatientWithWrongArgumentAndReturnBadRequest() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
		
		//Create a new patient
		Patient testPatient = new Patient();
		testPatient.setName(TEST_PATIENT_PREFIX + "2");
		testPatient.setFirstName("test test");
		testPatient.setBirthDate("00-00-0000");
		testPatient.setGender("F");
		testPatient.setAddress("address address");
		testPatient.setPhoneNumber("000-000-0000");

		String patientAsString = objectMapper.writeValueAsString(testPatient);
		
		//Mock return
		Mockito.when(patientService.addNewPatient(Mockito.any())).thenThrow(new RuntimeException());
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
				.contentType(MediaType.APPLICATION_JSON).content(patientAsString))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void updateExistingPatientInDatabaseAndReturnCreated() throws Exception {

		//Fetching patient to update
		Patient updatePatient = testPatients.get(0);
		updatePatient.setName(TEST_PATIENT_PREFIX + "update");
		updatePatient.setFirstName("try");
		updatePatient.setBirthDate("11-11-1111");
		updatePatient.setGender("F");
		updatePatient.setAddress("localisation");
		updatePatient.setPhoneNumber("111-111-1111");

		String updatePatientAsString = objectMapper.writeValueAsString(updatePatient);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/patients")
					.contentType(MediaType.APPLICATION_JSON).content(updatePatientAsString))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));

	}

	@Test
	public void updateExistingPatientWithWrongArgumentAndReturnBadRequest() throws Exception{

		mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
		
		//Fetching patient to update
		Patient updatePatient = testPatients.get(0);
		updatePatient.setName(TEST_PATIENT_PREFIX + "update");
		updatePatient.setFirstName("try");
		updatePatient.setBirthDate("11-11-1111");
		updatePatient.setGender("F");
		updatePatient.setAddress("localisation");
		updatePatient.setPhoneNumber("111-111-1111");

		String updatePatientAsString = objectMapper.writeValueAsString(updatePatient);
		
		//Mock return
		Mockito.when(patientService.updateExistingPatient(Mockito.any())).thenThrow(new RuntimeException());
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/patients")
					.contentType(MediaType.APPLICATION_JSON).content(updatePatientAsString))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void deleteExistingPatientInDatabaseAndReturnNoContent() throws Exception {

		//Fetching patient to delete
		Patient deletePatient = patientRepository.findById(testPatients.get(0).getId())
								.orElseThrow(() -> new RuntimeException("Patient not found"));

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/" + deletePatient.getId()))
			.andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void deleteNoneExistingPatientInDatabaseAndReturnNotFound() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/" + -1))
			.andExpect(MockMvcResultMatchers.status().isNotFound());

	}

}
