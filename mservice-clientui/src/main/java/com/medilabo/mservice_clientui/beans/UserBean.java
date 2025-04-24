package com.medilabo.mservice_clientui.beans;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * Bean entity for the user model from mservice-user
 */
public class UserBean {

	private int id;
	
	@NotEmpty(message = "{user.username.notempty}")
	private String username;
	
	@NotEmpty(message = "{user.name.notempty}")
	private String name;
	
	@NotEmpty(message = "{user.firstName.notempty}")
	private String firstName;
	
	@NotEmpty(message = "{user.mail.notempty}")
	private String mail;
	
	@NotEmpty(message = "{user.password.notempty}")
	private String password;
	
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "{user.birthDate.pattern}")
	private String birthDate;
	
	private Date creationDate;
	
	@NotEmpty(message = "{user.role.notempty}")
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
