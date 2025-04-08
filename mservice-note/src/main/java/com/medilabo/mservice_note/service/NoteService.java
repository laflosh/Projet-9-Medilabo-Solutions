package com.medilabo.mservice_note.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_note.model.Note;
import com.medilabo.mservice_note.repository.NoteRepository;

/**
 * 
 */
@Service
public class NoteService {

	private final static Logger log = LogManager.getLogger(NoteService.class);
	
	@Autowired
	NoteRepository noteRepository;

	/**
	 * @return
	 */
	public List<Note> getAllNotes() {
		
		List<Note> notes = noteRepository.findAll();
		
		return notes;
		
	}

	/**
	 * @param id
	 * @return
	 */
	public Note getOneNoteById(String id) {
		
		Note note = noteRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Note not found"));
		
		return note;
		
	}

	/**
	 * @param newNote
	 * @return
	 */
	public Note addNewNote(Note newNote) {
		
		Note addedNote = noteRepository.insert(newNote);
		
		return addedNote;
		
	}

	/**
	 * @param id
	 * @return
	 */
	public Note updateExistingNote(Note updateNote) {
		
		Note existingNote = getOneNoteById(updateNote.getId());
		
		if(existingNote.getPatId() != updateNote.getPatId()) {
			existingNote.setPatId(updateNote.getPatId());
		}
		
		if(existingNote.getPatient() != updateNote.getPatient()) {
			existingNote.setPatient(updateNote.getPatient());
		}
		
		if(existingNote.getNote() != updateNote.getNote()){
			existingNote.setNote(updateNote.getNote());
		}
		
		return noteRepository.save(existingNote);
		
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteExistingNote(String id) {
		
		if(noteRepository.existsById(id)) {
			
			noteRepository.deleteById(id);
			
			return true;
			
		}
		
		return false;
		
	}

	/**
	 * @param patientName
	 * @return
	 */
	public List<Note> getAllNotesDependingOfPatientName(String patientName) {
		
		List<Note> patientNotes = noteRepository.findByPatient(patientName);
		
		return patientNotes;
		
	}
	
}
