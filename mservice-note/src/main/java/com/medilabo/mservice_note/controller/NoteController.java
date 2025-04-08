package com.medilabo.mservice_note.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.mservice_note.model.Note;
import com.medilabo.mservice_note.service.NoteService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class NoteController {

	private final static Logger log = LogManager.getLogger(NoteController.class);
	
	@Autowired
	NoteService noteService;
	
	/**
	 * @return
	 */
	@GetMapping("/notes")
	public ResponseEntity<?> getAllNotes(){
		
		List<Note> notes = noteService.getAllNotes();
		
		if(notes != null) {
			
			return ResponseEntity.ok().body(notes);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes not found");
			
		}
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/notes/{id}")
	public ResponseEntity<?> getOneNoteById(@PathVariable("id") String id){
		
		Note note = noteService.getOneNoteById(id);
		
		if(note != null) {
			
			return ResponseEntity.ok().body(note);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
			
		}
		
	}
	
	/**
	 * @param newNote
	 * @return
	 */
	@PostMapping("/notes")
	public ResponseEntity<?> addNewNote(@RequestBody Note newNote){
		
		try {
			
			Note addedNote = noteService.addNewNote(newNote);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(addedNote);
			
		} catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during saving new note");
			
		}
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@PutMapping("/notes")
	public ResponseEntity<?> updateExistingNote(@RequestBody Note updateNote){
		
		try {
			
			Note updatedNote = noteService.updateExistingNote(updateNote);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedNote);
			
		} catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during updating an existing note");
			
		}
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteExistingNote(@PathVariable("id") String id){
		
		try {
			
			boolean isDeleted = noteService.deleteExistingNote(id);
			
			if(isDeleted) {

				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

			} else {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}
		
	}
	
	/**
	 * @param patientName
	 * @return
	 */
	@GetMapping("/notes/patient/{patientName}")
	public ResponseEntity<?> getAllNotesDependingOfPatientName(@PathVariable("patientName") String patientName) {
		
		List<Note> patientNotes = noteService.getAllNotesDependingOfPatientName(patientName);
		
		if(patientNotes != null) {
			
			return ResponseEntity.ok().body(patientNotes);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes of the patient not found");
			
		}
		
	}
	
}
