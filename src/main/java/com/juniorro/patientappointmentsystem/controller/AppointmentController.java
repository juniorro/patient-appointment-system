package com.juniorro.patientappointmentsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juniorro.patientappointmentsystem.Service.AppointmentService;
import com.juniorro.patientappointmentsystem.Service.PatientService;
import com.juniorro.patientappointmentsystem.Service.PhysicianService;
import com.juniorro.patientappointmentsystem.model.Appointment;
import com.juniorro.patientappointmentsystem.model.Patient;
import com.juniorro.patientappointmentsystem.model.Physician;

@Controller
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PhysicianService physicianService;
	
	@Autowired
	private PatientService patientService;

	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public ModelAndView appointments(Model model) {
		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);
		List<Appointment> appointments = appointmentService.allAppointments();
		return new ModelAndView("appointments", "appointments", appointments);
	}
	
	@RequestMapping(value = "/newAppointment", method = RequestMethod.GET)
	public ModelAndView newAppointment(Model model) {
		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);
		List<Physician> physicians = physicianService.allPhysicians();
		List<Patient> patients = patientService.allPatients();
		model.addAttribute("patients", patients);
		return new ModelAndView("newAppointment", "physicians", physicians);
	}
	
	@RequestMapping(value = "/saveNewAppointment", method = RequestMethod.POST)
	public ModelAndView saveNewAppointment(@Valid Appointment newAppointment, BindingResult result, Model model, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			List<Physician> physicians = physicianService.allPhysicians();
			List<Patient> patients = patientService.allPatients();
			model.addAttribute("patients", patients);
			return new ModelAndView("newAppointment", "physicians", physicians);
		} else {
			appointmentService.saveAppointment(newAppointment);
			redirect.addFlashAttribute("newAppointmentSuccess", true);
			return new ModelAndView("redirect:/appointments");
		}
	}
	
	@RequestMapping(value = "/editAppointment", method = RequestMethod.GET)
	public ModelAndView editCustomer(Long id, Model model) {
		Appointment appointment = appointmentService.getOne(id);
		Physician physician = appointment.getPhysician();
		Patient patient = appointment.getPatient();
		List<Physician> physicians = physicianService.allPhysicians();
		List<Patient> patients = patientService.allPatients();
		model.addAttribute("patients", patients);
		model.addAttribute("physicians", physicians);
		model.addAttribute("physician", physician);
		model.addAttribute("patient", patient);
		return new ModelAndView("editAppointment", "appointment", appointment);
	}

}
