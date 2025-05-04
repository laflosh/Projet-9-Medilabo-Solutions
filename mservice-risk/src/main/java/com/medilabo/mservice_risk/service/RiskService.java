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

@Service
public class RiskService {

	private final static Logger log = LogManager.getLogger(RiskService.class);
	final int checkAge = 30;
	final KeyWords[] keyWords = KeyWords.values();

	@Autowired
	MServicePatientProxy patientProxy;

	@Autowired
	MServiceNoteProxy noteProxy;

	public RiskLevel calculateRiskLevel(int id) {

		PatientBean patient = patientProxy.getOnePatientById(id);
		List<NoteBean> patientNotes = noteProxy.getAllNotesDependingOfPatientName(patient.getName());

		List<String> notes = new ArrayList<>();

		for(NoteBean patientNote : patientNotes) {

			String note = patientNote.getNote();

			notes.add(note);

		}

		int countKeyWords = 0;

		for(String note : notes) {

			for(KeyWords keyWord : keyWords) {

				if(note.toLowerCase().contains(keyWord.getMot().toLowerCase())) {

					countKeyWords++;

				}

			}

		}
		
		System.out.println("Nombre de mots détecté : " + countKeyWords);

		int age = calculateAge(patient.getBirthDate());
		
		System.out.println("Nombre de age détecté : " + age);
		
		System.out.println("Nombre de age détecté : " + patient.getGender());

		if(countKeyWords < 2) {

			return RiskLevel.NONE;

		} else {
			
			if(patient.getGender().equals("M")) {

				//Over 30 years
				if(countKeyWords >= 2 && countKeyWords <= 5 && age >= checkAge) {
					
					return RiskLevel.BORDERLINE;
					
				}
				
				if(countKeyWords >= 6 && countKeyWords <= 7 && age >= checkAge) {
					
					return RiskLevel.IN_DANGER;
					
				}
				
				if(countKeyWords >= 8 && age >= checkAge) {
					
					return RiskLevel.EARLY_ONSET;
					
				}
				
				//Under 30 years
				if(countKeyWords >= 3 && countKeyWords < 5 && age <= checkAge) {
					
					return RiskLevel.IN_DANGER;
					
				}
				
				if(countKeyWords >= 5 && age <= checkAge) {
					
					return RiskLevel.EARLY_ONSET;
					
				}

			} else if(patient.getGender().equals("F")) {

				//Over 30 years
				if(countKeyWords >= 2 && countKeyWords <= 5 && age >= checkAge) {
					
					return RiskLevel.BORDERLINE;
					
				}
				
				if(countKeyWords >= 6 && countKeyWords <= 7 && age >= checkAge) {
					
					return RiskLevel.IN_DANGER;
					
				}
				
				if(countKeyWords >= 8 && age >= checkAge) {
					
					return RiskLevel.EARLY_ONSET;
					
				}
				
				//Under 30 years
				if(countKeyWords >= 3 && countKeyWords < 7 && age <= checkAge) {
					
					return RiskLevel.IN_DANGER;
					
				}
				
				if(countKeyWords >= 7 && age <= checkAge) {
					
					return RiskLevel.EARLY_ONSET;
					
				}

			}
			
		}
		
		return null;

	}

	private int calculateAge(String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate dateNaissance = LocalDate.parse(birthdate, formatter);
        LocalDate dateActuelle = LocalDate.now();
        
        return Period.between(dateNaissance, dateActuelle).getYears();

	}

}
