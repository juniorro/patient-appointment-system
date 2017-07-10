package com.juniorro.patientappointmentsystem.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "First Name is required")
	private String firstName;

	@NotEmpty(message = "Last Name is required")
	private String lastName;

	private String gender;

	private String ssn;

	@NotEmpty(message = "Phone is required")
	private String phone;

	@Email(message = "Not a good email")
	@NotEmpty(message = "Email is required")
	private String email;

	private String streetAddress;

	private String city;

	private String zipCode;

	private String status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Date of birth is required")
	private Date dateOfBirth;

	@Transient
	private MultipartFile patientPhoto;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Physician> physician;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ClinicalDepartment> cinicalDepartment;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointment;

	@OneToOne
	private MedicalRecord medicalRecord;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Diagnosis> diagnosis;

	public Patient() {
		super();
	}

	public Patient(Long id, String firstName, String lastName, String gender, String ssn, String phone, String email,
			String streetAddress, String city, String zipCode, String status, Date dateOfBirth,
			MultipartFile patientPhoto, List<Physician> physician, List<ClinicalDepartment> cinicalDepartment,
			List<Appointment> appointment, MedicalRecord medicalRecord, List<Diagnosis> diagnosis) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.ssn = ssn;
		this.phone = phone;
		this.email = email;
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.status = status;
		this.dateOfBirth = dateOfBirth;
		this.patientPhoto = patientPhoto;
		this.physician = physician;
		this.cinicalDepartment = cinicalDepartment;
		this.appointment = appointment;
		this.medicalRecord = medicalRecord;
		this.diagnosis = diagnosis;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public MultipartFile getPatientPhoto() {
		return patientPhoto;
	}

	public void setPatientPhoto(MultipartFile patientPhoto) {
		this.patientPhoto = patientPhoto;
	}

	public List<Physician> getPhysician() {
		return physician;
	}

	public void setPhysician(List<Physician> physician) {
		this.physician = physician;
	}

	public List<ClinicalDepartment> getCinicalDepartment() {
		return cinicalDepartment;
	}

	public void setCinicalDepartment(List<ClinicalDepartment> cinicalDepartment) {
		this.cinicalDepartment = cinicalDepartment;
	}

	public List<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<Appointment> appointment) {
		this.appointment = appointment;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public List<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<Diagnosis> diagnosis) {
		this.diagnosis = diagnosis;
	}

}
