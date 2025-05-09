package com.medilabo.mservice_risk.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_risk.bean.NoteBean;
import com.medilabo.mservice_risk.bean.PatientBean;
import com.medilabo.mservice_risk.enums.KeyWords;
import com.medilabo.mservice_risk.enums.RiskLevel;
import com.medilabo.mservice_risk.proxy.MServiceNoteProxy;
import com.medilabo.mservice_risk.proxy.MServicePatientProxy;

/**
 * Service class for all the logic to calculate the risk level of one patient
 */
@Service
public class RiskService {

	private final static Logger log = LogManager.getLogger(RiskService.class);
	final int checkAge = 30;
	final KeyWords[] keyWords = KeyWords.values();

	@Autowired
	MServicePatientProxy patientProxy;

	@Autowired
	MServiceNoteProxy noteProxy;

	/**
	 * Calculate the risk level of one patient depending of the age of the patient 
	 * and how many key words are presents in their notes
	 * 
	 * @param id of the patient
	 * @return riskLevel
	 */
	public RiskLevel calculateRiskLevel(int id) {

		log.info("Calculate the risk level for the patient with id : {}", id);
		
		PatientBean patient = patientProxy.getOnePatientById(id);
		List<NoteBean> patientNotes = noteProxy.getAllNotesDependingOfPatientName(patient.getName());

		List<String> notes = getAllNotesForPatient(patientNotes);

		int countKeyWords = countKeyWordsInNotes(notes);

		int age = calculateAge(patient.getBirthDate());
		
		return determinateRiskLevel(age, countKeyWords, patient);

	}

	/**
	 * Extract all the notes in the List of NoteBean
	 * 
	 * 
	 * @param patientNotes (List of NoteBean)
	 * @return List of notes
	 */
	private List<String> getAllNotesForPatient(List<NoteBean> patientNotes){
		
		List<String> notes = new ArrayList<>();
		
		for(NoteBean patientNote : patientNotes) {

			String note = patientNote.getNote();

			notes.add(note);

		}
		
		return notes;
		
	}
	
	/**
	 * Count how many keywords are present in all notes 
	 * 
	 * @param notes
	 * @return count of keywords
	 */
	private int countKeyWordsInNotes(List<String> notes) {
		
		int count = 0;
		
		for(String note : notes) {

			for(KeyWords keyWord : keyWords) {

				if(note.toLowerCase().contains(keyWord.getMot().toLowerCase())) {

					count++;

				}

			}

		}
		
		return count;
		
	}
	
	/**
	 * Determinate the risk level of one patient
	 * 
	 * @param age
	 * @param countKeyWords
	 * @param patient
	 * @return riskLevel
	 */
	private RiskLevel determinateRiskLevel(int age, int countKeyWords, PatientBean patient) {

		if(countKeyWords < 2) {
			
			return RiskLevel.NONE;
			
		} else {
			
			if(checkAgeUnderThirty(age)) {
				
				if(patient.getGender().equals("M")) {
				
					return levelRiskForMaleUnderThirty(countKeyWords);
					
				} else if(patient.getGender().equals("F")) {
					
					return levelRiskForFemaleUnderThirty(countKeyWords);
					
				}	
				
			} else {
				
				return levelRiskOverThirty(countKeyWords);
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Check the age of the patient to determinate if it's under or over 30
	 * 
	 * @param age of the patient
	 * @return boolean
	 */
	private boolean checkAgeUnderThirty(int age) {
		
		if(age <= checkAge) {
			
			return true;
			
		}
		
		return false;
	}

	/**
	 * Determinate the risk level for a female under 30 years
	 * 
	 * @param countKeyWords
	 * @return riskLevel
	 */
	private RiskLevel levelRiskForFemaleUnderThirty(int countKeyWords) {

		if(countKeyWords >= 3 && countKeyWords < 7) {
			
			return RiskLevel.IN_DANGER;
			
		} else if(countKeyWords >= 7) {
			
			return RiskLevel.EARLY_ONSET;
			
		}
		
		return null;
		
	}

	/**
	 * Determinate the risk level for a male under 30 years
	 * 
	 * @param countKeyWords
	 * @return riskLevel
	 */
	private RiskLevel levelRiskForMaleUnderThirty(int countKeyWords) {

		if(countKeyWords >= 3 && countKeyWords < 5) {
			
			return RiskLevel.IN_DANGER;
			
		} else if(countKeyWords >= 5) {
			
			return RiskLevel.EARLY_ONSET;
			
		}
		
		return null;
		
	}
	
	/**
	 * Determinate the risk level for a patient over 30 years
	 * 
	 * @param countKeyWords
	 * @return
	 */
	private RiskLevel levelRiskOverThirty(int countKeyWords) {
		
		if(countKeyWords >= 2 && countKeyWords <= 5) {
			
			return RiskLevel.BORDERLINE;
			
		} else if(countKeyWords >= 6 && countKeyWords <= 7) {
			
			return RiskLevel.IN_DANGER;
			
		} else if(countKeyWords >= 8) {
			
			return RiskLevel.EARLY_ONSET;
		
		}
		
		return null;	
		
	}

	/**
	 * Calculate the age of one patient based on the birthdate
	 * 
	 * @param birthdate of the patient
	 * @return age of the patient
	 */
	private int calculateAge(String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate dateNaissance = LocalDate.parse(birthdate, formatter);
        LocalDate dateActuelle = LocalDate.now();
        
        return Period.between(dateNaissance, dateActuelle).getYears();

	}

}
