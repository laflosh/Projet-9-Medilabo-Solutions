package com.medilabo.mservice_clientui.proxys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medilabo.mservice_clientui.beans.NoteBean;
import com.medilabo.mservice_clientui.configuration.FeignConfig;

/**
 * Interface for http request to mservice-note
 */
@FeignClient(name = "mservice-note", url = "localhost:8080", configuration = FeignConfig.class)
public interface MServiceNoteProxy {

	@GetMapping("/api/notes")
	List<NoteBean> getAllNotes();
	
	@GetMapping("/api/notes/{id}")
	NoteBean getOneNoteById(@PathVariable("id") String id);
	
	@PostMapping("/api/notes")
	NoteBean addNewNote(@RequestBody NoteBean note);
	
	@PutMapping("/api/notes")
	NoteBean updateExistingNote(@RequestBody NoteBean note);
	
	@DeleteMapping("/api/notes/{id}")
	void deleteExistingNote(@PathVariable("id") String id);
	
	@GetMapping("/api/notes/patient/{patientName}")
	List<NoteBean> getAllNotesDependingOfPatientName(@PathVariable("patientName") String patientName);
	
}
