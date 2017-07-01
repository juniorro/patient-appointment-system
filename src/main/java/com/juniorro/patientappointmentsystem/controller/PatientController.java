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
import com.juniorro.patientappointmentsystem.Service.PatientService;
import com.juniorro.patientappointmentsystem.model.Patient;

@Controller
public class PatientController {
	@Autowired
	private PatientService patientService;

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public ModelAndView users() {
		List<Patient> patients = patientService.allPatients();
		return new ModelAndView("patients", "patients", patients);
	}

	@RequestMapping(value = "/editPatient", method = RequestMethod.GET)
	public ModelAndView editCustomer(Long id) {
		Patient patient = patientService.getOne(id);
		return new ModelAndView("editPatient", "patient", patient);
	}

	@RequestMapping(value = "/newPatient", method = RequestMethod.GET)
	public ModelAndView newUser() {
		Patient patient = new Patient();
		return new ModelAndView("newPatient", "patient", patient);
	}

	@RequestMapping(value = "/saveNewPatient", method = RequestMethod.POST)
	public ModelAndView saveNewPatient(@Valid Patient patient, BindingResult result, Model model, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("newPatient");
		} else {
			patientService.savePatient(patient);
			redirect.addFlashAttribute("newPatientSuccess", true);
			return new ModelAndView("redirect:/patients");
		}
	}

	@RequestMapping(value = "/updatePatient", method = RequestMethod.POST)
	public ModelAndView updateCustomer(@Valid Patient patient, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("editPatient");
		} else {
			patientService.savePatient(patient);
			return new ModelAndView("editPatient", "updatedPatient", true);
		}
	}

}
