package com.medilabo.mservice_risk.enums;

/**
 *
 */
public enum KeyWords {

	HÉMOGLOBINE_A1C("hémoglobine A1C"),
	MICROALBUMINE("microalbumine"),
	TAILLE("taille"),
	POIDS("poids"),
	FUMEUR("fumeur"),
	FUMEUSE("fumeuse"),
	ANORMAL("anormal"),
	CHOLESTÉROL("cholestérol"),
	VERTIGES("vertiges"),
	RECHUTE("rechute"),
	RÉACTION("réaction"),
	ANTICORPS("anticorps");

    private final String mot;

    KeyWords(String mot) {
        this.mot = mot;
    }

    public String getMot() {
        return mot;
    }

}
