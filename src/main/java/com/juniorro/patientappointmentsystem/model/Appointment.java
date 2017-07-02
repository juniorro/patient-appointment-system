package com.juniorro.patientappointmentsystem.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@DateTimeFormat(pattern="MM/dd/yyyy h:m a")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "From Date is required")
	private Date fromDate;
	
	@DateTimeFormat(pattern="MM/dd/yyyy h:m a")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "To date is required")
	private Date toDate;

	private String note;

	@NotNull(message = "Patient is required")
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@NotNull(message = "Physician is required")
	@ManyToOne
	@JoinColumn(name = "physician_id")
	private Physician physician;

	public Appointment() {
		super();
	}

	public Appointment(Date fromDate, Date toDate, String note, Patient patient, Physician physician) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.note = note;
		this.patient = patient;
		this.physician = physician;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

}
