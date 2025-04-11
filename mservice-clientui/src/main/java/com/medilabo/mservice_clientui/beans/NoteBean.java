package com.medilabo.mservice_clientui.beans;

import jakarta.validation.constraints.NotEmpty;

/**
 * 
 */
public class NoteBean {

	private String id;
	
	private int patId;
	
	private String patient;
	
	@NotEmpty(message = "{note.note.notempty}")
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPatId() {
		return patId;
	}

	public void setPatId(int patId) {
		this.patId = patId;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
