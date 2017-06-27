package com.juniorro.patientappointmentsystem.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniorro.patientappointmentsystem.Service.AppointmentService;
import com.juniorro.patientappointmentsystem.model.Appointment;
import com.juniorro.patientappointmentsystem.model.Patient;
import com.juniorro.patientappointmentsystem.repo.AppointmentRepo;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepo appointmentRepo;

	@Override
	public List<Appointment> allAppointments() {
		return appointmentRepo.findAll();
	}

	@Override
	public long count() {
		return appointmentRepo.count();
	}

	@Override
	public Appointment saveAppointment(Appointment appointment) {
		return appointmentRepo.save(appointment);
	}

}
