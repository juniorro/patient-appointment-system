package com.juniorro.patientappointmentsystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Appointment;

@Service
public interface AppointmentService {

	List<Appointment> allAppointments();
	
	Appointment saveAppointment(Appointment appointment);

	long count();

}
