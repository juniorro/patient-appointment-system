package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniorro.patientappointmentsystem.Service.DoctorService;
import com.juniorro.patientappointmentsystem.model.Doctor;
import com.juniorro.patientappointmentsystem.repo.DoctorRepo;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepo doctorRepo;

	@Override
	public List<Doctor> allDoctors() {
		return doctorRepo.findAll();
	}

	}
