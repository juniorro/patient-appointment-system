package com.juniorro.patientappointmentsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Physician {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "First Name is required")
	private String firstName;

	@NotEmpty(message = "Last Name is required")
	private String lastName;

	@Email(message = "Not a good email")
	@NotEmpty(message = "Email is required")
	private String email;

	@NotEmpty(message = "License number is required")
	private String licenseNumber;

	private String npiNumber;

	private String streetAddress;

	private String city;

	private String zipCode;

	private boolean enabled;

	@NotNull(message = "Phone number is required")
	private String phone;

	@ManyToOne(fetch = FetchType.EAGER)
	private Patient patient;

	public Physician() {
		super();
	}

	public Physician(String firstName, String lastName, String email, String licenseNumber, String npiNumber,
			String streetAddress, String city, String zipCode, boolean enabled, String phone, Patient patient) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.licenseNumber = licenseNumber;
		this.npiNumber = npiNumber;
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.enabled = enabled;
		this.phone = phone;
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getNpiNumber() {
		return npiNumber;
	}

	public void setNpiNumber(String npiNumber) {
		this.npiNumber = npiNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
