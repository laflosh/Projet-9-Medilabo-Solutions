package com.medilabo.mservice_risk.bean;

/**
 * Bean model for the entity patient from the mservice-patient
 */
public class PatientBean {

	private int id;

	private String name;

	private String firstName;

	private String birthDate;

	private String gender;

	private String address;

	private String phoneNumber;

	public PatientBean() {
		
	}

	public PatientBean(int id, String name, String firstName, String birthDate, String gender, String address,
			String phoneNumber) {
		this.id = id;
		this.name = name;
		this.firstName = firstName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
