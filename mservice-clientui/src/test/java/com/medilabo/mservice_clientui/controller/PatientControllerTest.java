package com.medilabo.mservice_clientui.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.proxys.MServicePatientProxy;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	MServicePatientProxy patientProxy;
	
	@InjectMocks
	PatientController patientController;
	
    @BeforeEach
    void setup() {
    	
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        
    }
	
	@Test
	public void getAccessToTheListPageWithAllPatients() throws Exception {
		
		//Testing patients for mock request
        List<PatientBean> mockPatients = Arrays.asList(
                new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789"),
                new PatientBean(2, "Smith", "Jane", "1985-05-10", "F", "456 Elm St", "987654321")
        );
        
        //Mock return
        Mockito.when(patientProxy.getAllPatients()).thenReturn(mockPatients);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/patients"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
			.andExpect(MockMvcResultMatchers.view().name("patients/list"));
		
	}
	
	@Test
	public void getAccesToTheInformationPageOfOnePatient() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
        Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
        
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.get("/ui/patients/" + mockPatient.getId()))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/informations"));
		
	}
	
	@Test
	public void getAccessToTheAddPage() throws Exception {
		
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.get("/ui/patients/add"))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/add"));
		
	}
	
	@Test
	public void postValidateNewPatientAndReturnResumePage() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
        Mockito.when(patientProxy.addNewPatient(any(PatientBean.class))).thenReturn(mockPatient);
        
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.post("/ui/patients/validate")
                .param("name", "Doe")
                .param("firstName", "John")
                .param("birthDate", "1990-01-01")
                .param("gender", "M")
                .param("address", "123 Main St")
                .param("phoneNumber", "123456789"))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/resume"));
		
	}
	
	@Test
	public void postNoneValidateNewPatient() throws Exception {
		
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.post("/ui/patients/validate"))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "name", "firstName", "birthDate", "gender"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/add"));
		
	}
	
	@Test
	public void getUpdatePageForAnExistingPatient() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
        Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
        
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.get("/ui/patients/update/" + mockPatient.getId()))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/update"));
		
	}
	
	@Test
	public void postUpdateValidateExistingPatient() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
        Mockito.when(patientProxy.addNewPatient(any(PatientBean.class))).thenReturn(mockPatient);
        Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
        Mockito.when(patientProxy.updateExistingPatient(any(PatientBean.class))).thenReturn(mockPatient);
        
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.post("/ui/patients/update/" + mockPatient.getId())
                .param("name", "Mock")
                .param("firstName", "Patient")
                .param("birthDate", "1990-01-01")
                .param("gender", "F")
                .param("address", "123 Main St")
                .param("phoneNumber", "123456789"))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/resume"));
		
	}
	
	@Test
	public void postNoneValidatePatientToUpdate() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
		Mockito.when(patientProxy.addNewPatient(any(PatientBean.class))).thenReturn(mockPatient);
        Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
        
        //Testing request
        mockMvc.perform(MockMvcRequestBuilders.post("/ui/patients/update/" + mockPatient.getId()))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
        	.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "name", "firstName", "birthDate", "gender"))
        	.andExpect(MockMvcResultMatchers.view().name("patients/update"));
		
	}
	
	@Test
	public void getConfirmationPageBeforeDeletePatient() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
        Mockito.when(patientProxy.addNewPatient(any(PatientBean.class))).thenReturn(mockPatient);
        Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/ui/patients/confirmation/" + mockPatient.getId()))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	    	.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
	    	.andExpect(MockMvcResultMatchers.view().name("patients/confirmation"));
		
	}
	
	@Test
	public void deletePatientExistingInDatabaseAndReturnPatientsList() throws Exception {
		
		//Testing patients for mock request
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
		
		//Mock return
        Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/ui/patients/delete/" + mockPatient.getId()))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	    	.andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
	    	.andExpect(MockMvcResultMatchers.view().name("patients/list"));
		
	}

}
