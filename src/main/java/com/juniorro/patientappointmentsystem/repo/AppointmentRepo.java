package com.juniorro.patientappointmentsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorro.patientappointmentsystem.model.Appointment;
import com.juniorro.patientappointmentsystem.model.Physician;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

	List<Appointment> findByPhysician(Physician physician);

	long count();

}
