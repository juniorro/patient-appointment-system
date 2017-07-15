package com.juniorro.patientappointmentsystem.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.juniorro.patientappointmentsystem.Service.PhysicianService;
import com.juniorro.patientappointmentsystem.model.Physician;

@Controller
public class PhysicianController {
	
	@Autowired
	private PhysicianService physicianService;

	@RequestMapping(value = "/physicians", method = RequestMethod.GET)
	public ModelAndView physicians() {
		List<Physician> physicians = physicianService.allPhysicians();
		return new ModelAndView("physicians", "physicians", physicians);
	}

	@RequestMapping(value = "/physicianProfile", method = RequestMethod.GET)
	public ModelAndView editPhysician(Long id) {
		Physician physician = physicianService.findById(id);
		return new ModelAndView("physicianProfile", "physician", physician);
	}

	@RequestMapping(value = "/newPhysician", method = RequestMethod.GET)
	public ModelAndView newPhysician() {
		Physician physician = new Physician();
		return new ModelAndView("newPhysician", "physician", physician);
	}

	@RequestMapping(value = "/saveNewPhysician", method = RequestMethod.POST)
	public ModelAndView saveNewPhysician(@Valid Physician physician, BindingResult result, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("newPhysician");
		} else {
			physicianService.savePhysician(physician);
			MultipartFile profilePhoto = physician.getProfilePhoto();

			try {
				byte[] bytes = profilePhoto.getBytes();
				String name = physician.getId() + ".png";
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/physician/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			redirect.addFlashAttribute("newPhysicianSuccess", true);
			return new ModelAndView("redirect:/physicians");
		}
	}

	@RequestMapping(value = "/updatePhysician", method = RequestMethod.POST)
	public ModelAndView updatePhysician(@Valid Physician physician, BindingResult result, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("editPhysician");
		} else {
			physicianService.savePhysician(physician);
			MultipartFile profilePhoto = physician.getProfilePhoto();

			try {
				byte[] bytes = profilePhoto.getBytes();
				String name = physician.getId() + ".png";
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/physician/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			redirect.addFlashAttribute("updatedPhysician", true);
			return new ModelAndView("redirect:/physicianProfile?id="+physician.getId());
		}
	}
	
	@RequestMapping(value = "/deletePhysician", method = RequestMethod.GET)
    public String delete(@RequestParam("id") long id, final RedirectAttributes redirect) {
		physicianService.delete(id);
		redirect.addFlashAttribute("physicianDelete", true);
        return "redirect:/physicians";
    }

}
