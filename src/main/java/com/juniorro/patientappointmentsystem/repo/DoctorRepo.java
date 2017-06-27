package com.juniorro.patientappointmentsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorro.patientappointmentsystem.model.Doctor;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {

	long count();

}
