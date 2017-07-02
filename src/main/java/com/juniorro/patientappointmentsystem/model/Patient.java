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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "First Name is required")
	private String firstName;

	@NotEmpty(message = "Last Name is required")
	private String lastName;

	@NotEmpty(message = "Phone is required")
	private String phone;

	@Email(message = "Not a good email")
	@NotEmpty(message = "Email is required")
	private String email;

	@NotEmpty(message = "Address is required")
	private String address;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Date of birth is required")
	private Date dateOfBirth;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Physician> physician;

	/* @NotEmpty(message = "Clinical department is required") */
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ClinicalDepartment> cinicalDepartment;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointment;

	@OneToOne
	private MedicalRecord medicalRecord;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Diagnosis> diagnosis;

	private boolean discharged;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(String firstName, String lastName, String phone, String email, String address, Date dateOfBirth,
			List<Physician> physician, List<ClinicalDepartment> cinicalDepartment, List<Appointment> appointment,
			MedicalRecord medicalRecord, List<Diagnosis> diagnosis, boolean discharged) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.physician = physician;
		this.cinicalDepartment = cinicalDepartment;
		this.appointment = appointment;
		this.medicalRecord = medicalRecord;
		this.diagnosis = diagnosis;
		this.discharged = discharged;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public boolean isDischarged() {
		return discharged;
	}

	public void setDischarged(boolean discharged) {
		this.discharged = discharged;
	}

}
