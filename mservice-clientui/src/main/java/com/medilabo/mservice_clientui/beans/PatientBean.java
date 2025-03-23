package com.medilabo.mservice_clientui.beans;

import jakarta.validation.constraints.NotEmpty;

/**
 * 
 */
public class PatientBean {

	private int id;
	
	@NotEmpty(message = "{patient.name.notempty}")
	private String name;
	
	@NotEmpty(message = "{patient.firstName.notempty}")
	private String firstName;
	
	@NotEmpty(message = "{patient.birthDate.notempty}")
	private String birthDate;
	
	@NotEmpty(message = "{patient.gender.notempty}")
	private String gender;
	
	private String address;
	
	private String phoneNumber;
	
	@Override
	public String toString() {

		return "Patient {" +
						" id : " + id +
						", name : " + name +
						", firstName : " + firstName +
						", birthDate : " + birthDate +
						", gender : " + gender +
						", address : " + address +
						", phoneNumber : " + phoneNumber +
						"}";

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
