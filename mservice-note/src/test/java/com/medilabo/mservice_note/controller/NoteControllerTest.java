package com.medilabo.mservice_note.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.mservice_note.model.Note;
import com.medilabo.mservice_note.repository.NoteRepository;
import com.medilabo.mservice_note.service.NoteService;

import jakarta.ws.rs.core.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

	final String TEST_NOTE_PREFIX = "test_note";
	List<Note> testNotes = new ArrayList<>();
	
	@Autowired
	NoteRepository noteRepository;
	
	@Mock
	NoteService noteService;
	
	@InjectMocks
	NoteController noteController;
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	public void setUp(){

		//Creating a patient test
		Note testNote = new Note();
		testNote.setNote(TEST_NOTE_PREFIX + " 1");
		testNote.setPatId(1);
		testNote.setPatient("TestPatient");;

		//Adding to the database
		noteRepository.insert(testNote);
		testNotes.add(testNote);

	}

	@AfterEach
	public void tearDown() {

		//Delete all the test entity in the database
		testNotes.forEach(testNote -> {
			if(noteRepository.existsById(testNote.getId())) {
				noteRepository.deleteById(testNote.getId());
			}
		});
		testNotes.clear();

	}
	
	@Test
	public void getAllNotesAndReturnOk() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void getOneNoteByIdAndReturnOk() throws Exception {
		
		//Get testing note
		Note testNote = testNotes.get(0);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/" + testNote.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void addNewNoteAndReturnIsCreated() throws Exception {
		
		//Create new note
		Note testNote = new Note();
		testNote.setPatId(1);
		testNote.setPatient("TestPatient");
		testNote.setNote(TEST_NOTE_PREFIX + "2");
		
		String testNoteAsString = objectMapper.writeValueAsString(testNote);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
			.contentType(MediaType.APPLICATION_JSON).content(testNoteAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
		List<Note> notesDatabase = noteRepository.findAll();
		
		testNotes.add(notesDatabase.get(notesDatabase.size() - 1));
		
	}
	
	@Test
	public void addNoteWithWrongArgumentAndReturnBadRequest() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
		
		//Create new note
		Note testNote = new Note();
		testNote.setPatId(1);
		testNote.setPatient("TestPatient");
		testNote.setNote(TEST_NOTE_PREFIX + "2");
		
		String testNoteAsString = objectMapper.writeValueAsString(testNote);
		
		//Mock return
		Mockito.when(noteService.addNewNote(Mockito.any())).thenThrow(new RuntimeException());
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
			.contentType(MediaType.APPLICATION_JSON).content(testNoteAsString))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void updateOneExistingNoteInDatabaseAndReturnIsCreated() throws Exception {
		
		//Get the test note to update
		Note testNoteUpdate = testNotes.get(0);
		testNoteUpdate.setPatId(2);
		testNoteUpdate.setPatient("Autre patient");
		testNoteUpdate.setNote("Nouvelle note");
		
		String testNoteUpdateAsString = objectMapper.writeValueAsString(testNoteUpdate);
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
			.contentType(MediaType.APPLICATION_JSON).content(testNoteUpdateAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void updateNoteWithWrongArgumentAndReturnBadRequest() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
		
		//Get the test note to update
		Note testNoteUpdate = testNotes.get(0);
		testNoteUpdate.setPatId(2);
		testNoteUpdate.setPatient("Autre patient");
		testNoteUpdate.setNote("Nouvelle note");
		
		String testNoteUpdateAsString = objectMapper.writeValueAsString(testNoteUpdate);
		
		//Mock return
		Mockito.when(noteService.updateExistingNote(Mockito.any())).thenThrow(new RuntimeException());
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
			.contentType(MediaType.APPLICATION_JSON).content(testNoteUpdateAsString))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void deleteOneExistingNoteByIdInDatabaseAndReturnNoContent() throws Exception {
		
		//Fetching note to delete
		Note testNote = noteRepository.findById(testNotes.get(0).getId())
						.orElseThrow(() -> new RuntimeException ("Note not found"));
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/" + testNote.getId()))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	public void deleteNoneExistingNoteByIdInDatabaseAndReturnNotFound() throws Exception {
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/" + 1))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void getAllNoteOfOnePatientDependingHisNameAndReturnOk() throws Exception {
		
		//Get the name of testing patient
		String namePatient = testNotes.get(0).getPatient();
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/patient/" + namePatient))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void getAllNotesOfNoneExistingPatientAndReturnNotFound() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/patient/"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());		
	}

}
