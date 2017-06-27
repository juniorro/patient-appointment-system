package com.juniorro.patientappointmentsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorro.patientappointmentsystem.model.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

	List<Appointment> findByDoctor(String doctor);

/*	Appointment findByEmail(String email);*/
	
	long count();
	
}
