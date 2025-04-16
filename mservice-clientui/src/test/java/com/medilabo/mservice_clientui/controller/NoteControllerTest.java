package com.medilabo.mservice_clientui.controller;

import static org.mockito.ArgumentMatchers.any;

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

import com.medilabo.mservice_clientui.beans.NoteBean;
import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.proxys.MServiceNoteProxy;
import com.medilabo.mservice_clientui.proxys.MServicePatientProxy;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	MServiceNoteProxy noteProxy;
	
	@Mock
	MServicePatientProxy patientProxy;
	
	@InjectMocks
	NoteController noteController;
	
    @BeforeEach
    void setup() {
    	
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
        
    }
    
    @Test
    public void getAccesToTheAddPage() throws Exception {
    	
    	//Testing patients for mock request
    	PatientBean mockPatient =  new PatientBean(1, "Doe", "John", "1990-01-01", "M", "123 Main St", "123456789");
    	
    	//Mock return
    	Mockito.when(patientProxy.getOnePatientById(mockPatient.getId())).thenReturn(mockPatient);
    	
    	//Testing request
    	mockMvc.perform(MockMvcRequestBuilders.get("/ui/notes/add/" + mockPatient.getId()))
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.model().attributeExists("note"))
    		.andExpect(MockMvcResultMatchers.view().name("notes/add"));
    	
    }
    
    @Test
    public void addNewNoteInDatabaseAndReturnCreated() throws Exception{
    	
    	//Testing note for mock request
		NoteBean mockNoteBean = new NoteBean("1", 1, "John Doe", "Patient has a cold.");
		
		//Mock return
		Mockito.when(noteProxy.addNewNote(any(NoteBean.class))).thenReturn(mockNoteBean);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ui/notes/validate")
				.param("patId", "1")
				.param("patient", "John Doe")
				.param("note", "Patient has a cold."))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("note"))
			.andExpect(MockMvcResultMatchers.view().name("resume"));
    	
    }
    
    @Test
    public void addNewNoteWithWrongArgument() throws Exception {
    	
    	//Testing request
    	mockMvc.perform(MockMvcRequestBuilders.post("/ui/notes/validate"))
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("note", "note"))
    		.andExpect(MockMvcResultMatchers.view().name("notes/add"));
    	
    }
    
    @Test
    public void getUpdatePageForOneNote() throws Exception {
    	
    	//Testing note for mock request
		NoteBean mockNoteBean = new NoteBean("1", 1, "John Doe", "Patient has a cold.");
		
		//Mock return
		Mockito.when(noteProxy.getOneNoteById(mockNoteBean.getId())).thenReturn(mockNoteBean);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/notes/update/" + mockNoteBean.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("note", mockNoteBean))
			.andExpect(MockMvcResultMatchers.view().name("notes/update"));
    	
    }
    
    @Test
    public void updateValidateNoteInDatabase() throws Exception {
    	
    	//Testing note for mock request
    	NoteBean mockNoteBean = new NoteBean("1", 1, "John Doe", "Patient has a cold.");
    	
    	//Mock return
		Mockito.when(noteProxy.addNewNote(any(NoteBean.class))).thenReturn(mockNoteBean);
		Mockito.when(noteProxy.getOneNoteById(mockNoteBean.getId())).thenReturn(mockNoteBean);
		Mockito.when(noteProxy.updateExistingNote(any(NoteBean.class))).thenReturn(mockNoteBean);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ui/notes/update/" + mockNoteBean.getId())
				.param("patId", "1")
				.param("patient", "John Doe")
				.param("note", "Différente note"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("note"))
			.andExpect(MockMvcResultMatchers.view().name("resume"));
    	
    }
    
    @Test
    public void updateNoneValidateNote() throws Exception {
    	
    	//Testing note for mock request
		NoteBean mockNoteBean = new NoteBean("1", 1, "John Doe", "Patient has a cold.");
		
		//Mock return
		Mockito.when(noteProxy.addNewNote(any(NoteBean.class))).thenReturn(mockNoteBean);
		Mockito.when(noteProxy.getOneNoteById(mockNoteBean.getId())).thenReturn(mockNoteBean);
    	
    	//Testing request
    	mockMvc.perform(MockMvcRequestBuilders.post("/ui/notes/update/" + mockNoteBean.getId()))
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("note", "note"))
    		.andExpect(MockMvcResultMatchers.view().name("notes/update"));
    	
    }
    
	@Test
	public void getConfirmationPageBeforeDeletePatient() throws Exception {
		
    	//Testing note for mock request
		NoteBean mockNoteBean = new NoteBean("1", 1, "John Doe", "Patient has a cold.");
		
		//Mock return
		Mockito.when(noteProxy.addNewNote(any(NoteBean.class))).thenReturn(mockNoteBean);
		Mockito.when(noteProxy.getOneNoteById(mockNoteBean.getId())).thenReturn(mockNoteBean);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/ui/notes/confirmation/" + mockNoteBean.getId()))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	    	.andExpect(MockMvcResultMatchers.model().attributeExists("note"))
	    	.andExpect(MockMvcResultMatchers.view().name("confirmation"));
		
	}
    
    @Test
    public void deleteExistingNoteInDatabase() throws Exception {
    	
    	//Testing note for mock request
		NoteBean mockNoteBean = new NoteBean("1", 1, "John Doe", "Patient has a cold.");
		
		//Mock return
		Mockito.when(noteProxy.getOneNoteById(mockNoteBean.getId())).thenReturn(mockNoteBean);
    	
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/notes/delete/" + mockNoteBean.getId()))
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/ui/patients/" + mockNoteBean.getPatId()));
		
    }

}
