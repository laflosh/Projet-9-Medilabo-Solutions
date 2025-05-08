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
 * Controller class for managing all the http request for the note domain 
 */
@RestController
@RequestMapping("/api")
public class NoteController {

	private final static Logger log = LogManager.getLogger(NoteController.class);
	
	@Autowired
	NoteService noteService;
	
	/**
	 * Fetching all the notes in the database and return a list
	 * 
	 * @return list of notes
	 */
	@GetMapping("/notes")
	public ResponseEntity<?> getAllNotes(){
		
		log.info("Trying to fetch all the notes in the database");
		
		List<Note> notes = noteService.getAllNotes();
		
		if(notes.isEmpty()) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes not found");
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(notes);
		
	}
	
	/**
	 * Fetching one note in the database depending of the id
	 * 
	 * @param id of the note
	 * @return One note
	 */
	@GetMapping("/notes/{id}")
	public ResponseEntity<?> getOneNoteById(@PathVariable("id") String id){
		
		log.info("Trying to fetch one note in the database with id : {}", id);
		
		Note note = noteService.getOneNoteById(id);
		
		if(note != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(note);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
			
		}
		
	}
	
	/**
	 * Add a new note in the database
	 * 
	 * @param newNote
	 * @return Added note
	 */
	@PostMapping("/notes")
	public ResponseEntity<?> addNewNote(@RequestBody Note newNote){
		
		log.info("Trying to add a new note in the database : {}", newNote);
		
		try {
			
			Note addedNote = noteService.addNewNote(newNote);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(addedNote);
			
		} catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during saving new note");
			
		}
		
	}
	
	/**
	 * Update an existing note in the database
	 * 
	 * @param update note
	 * @return updated note
	 */
	@PutMapping("/notes")
	public ResponseEntity<?> updateExistingNote(@RequestBody Note updateNote){
		
		log.info("Trying to update an existing note in the database : {}", updateNote);
		
		try {
			
			Note updatedNote = noteService.updateExistingNote(updateNote);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedNote);
			
		} catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during updating an existing note");
			
		}
		
	}
	
	/**
	 * Delete an existing note in the database 
	 * 
	 * @param id of the note
	 * @return true if delete
	 */
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteExistingNote(@PathVariable("id") String id){
		
		log.info("Trying to delete an existing note in the database with id : {}", id);
		
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
	 * Fetching all the notes of one patient with the name of the patient 
	 * 
	 * @param patientName
	 * @return patientName's notes
	 */
	@GetMapping("/notes/patient/{patientName}")
	public ResponseEntity<?> getAllNotesDependingOfPatientName(@PathVariable("patientName") String patientName) {
		
		log.info("Trying to fetch all the notes for the patient {} in database", patientName);
		
		List<Note> patientNotes = noteService.getAllNotesDependingOfPatientName(patientName);
		
		return ResponseEntity.status(HttpStatus.OK).body(patientNotes);
		
	}
	
}
