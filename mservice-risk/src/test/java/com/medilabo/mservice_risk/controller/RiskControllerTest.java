package com.medilabo.mservice_risk.controller;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
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

import com.medilabo.mservice_risk.bean.NoteBean;
import com.medilabo.mservice_risk.bean.PatientBean;
import com.medilabo.mservice_risk.enums.RiskLevel;
import com.medilabo.mservice_risk.proxy.MServiceNoteProxy;
import com.medilabo.mservice_risk.proxy.MServicePatientProxy;
import com.medilabo.mservice_risk.service.RiskService;

@SpringBootTest
@AutoConfigureMockMvc
public class RiskControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	MServiceNoteProxy noteProxy;
	
	@Mock
	MServicePatientProxy patientProxy;
	
    @Mock
    private RiskService riskService;
	
	@InjectMocks
	RiskController riskController;
	
    @BeforeEach
    void setup() {
    	
        mockMvc = MockMvcBuilders.standaloneSetup(riskController).build();
        
    }
	
	@Test
	void calculateTheRiskLevelOfOnePatientAndReturnOk() throws Exception {

		//Create mock patient and mock notes
		PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");

		List<NoteBean> mockNotes = new ArrayList<NoteBean>();
		NoteBean mockNoteBean1 = new NoteBean("1", 1, "John Doe", "Fumeur, poids");
		NoteBean mockNoteBean2 = new NoteBean("2", 1, "John Doe", "Taille");
		mockNotes.add(mockNoteBean1);
		mockNotes.add(mockNoteBean2);
		
		//Mock return
		Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
		Mockito.when(noteProxy.getAllNotesDependingOfPatientName(mockPatient.getName())).thenReturn(mockNotes);
		Mockito.when(riskService.calculateRiskLevel(mockPatient.getId())).thenReturn(RiskLevel.BORDERLINE);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/risk/" + mockPatient.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Risque modéré (BORDERLINE)")));
		
	}

}
