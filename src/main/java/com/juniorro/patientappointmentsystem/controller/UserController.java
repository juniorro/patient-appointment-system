package com.juniorro.patientappointmentsystem.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juniorro.patientappointmentsystem.Service.CustomerService;
import com.juniorro.patientappointmentsystem.Service.RoleService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.UserRole;
import com.juniorro.patientappointmentsystem.model.security.VerificationToken;
import com.juniorro.patientappointmentsystem.registrationlistener.OnRegistrationCompleteEvent;

@Controller
public class UserController {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView users() {
		List<Customer> customers = customerService.allCustomers();
		return new ModelAndView("users", "customers", customers);
	}

	@RequestMapping(value = "/userProfile", method = RequestMethod.GET)
	public ModelAndView userProfile(@RequestParam("id") Long id) {
		Customer customer = customerService.getOne(id);
		return new ModelAndView("userProfile", "customer", customer);
	}
	
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public ModelAndView editUser(@RequestParam("id") Long id) {
		Customer customer = customerService.getOne(id);
		return new ModelAndView("editUser", "customer", customer);
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public ModelAndView newUser() {
		Customer customer = new Customer();
		return new ModelAndView("newCustomer", "customer", customer);
	}

	@RequestMapping(value = "/saveNewUser", method = RequestMethod.POST)
	public String ModelAndView(@Valid Customer customer, BindingResult result, Model model,
			final HttpServletRequest request, final RedirectAttributes redirect) {
		if (customerService.checkUserExist(customer.getUsername(), customer.getEmail())) {

			if (customerService.checkEmailExist(customer.getEmail())) {
				model.addAttribute("emailExist", true);
			}

			if (customerService.checkUsernameExist(customer.getUsername())) {
				model.addAttribute("usernameExist", true);
			}

			return "newCustomer";
		}

		if (result.hasErrors()) {
			return "newCustomer";
		} else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(customer, roleService.findByName("ROLE_ADMIN")));
			customer.setEnabled(false);
			final Customer newcustomer = customerService.saveCustomer(customer, userRoles);
			MultipartFile profilePhoto = newcustomer.getProfilePhoto();

			try {
				byte[] bytes = profilePhoto.getBytes();
				String name = newcustomer.getId() + ".png";
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/user/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newcustomer, appUrl));
			redirect.addFlashAttribute("newUser", true);
			return "redirect:/users";
		}
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ModelAndView updateCustomer(@Valid Customer customer, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("editUser");
		} else {
			customerService.saveConfirmCustomer(customer);
			MultipartFile profilePhoto = customer.getProfilePhoto();

			try {
				byte[] bytes = profilePhoto.getBytes();
				String name = customer.getId() + ".png";
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/user/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/userProfile?id="+customer.getId(), "updatedUser", true);
		}
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model, Principal principal) {
		Customer customer = customerService.findByUsername(principal.getName());
		Set<UserRole> roles = customer.getCustomerRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("customer", customer);
        return "userProfile";
    }
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String delete(@RequestParam("id") long id, Model model) {
		customerService.delete(id);
		model.addAttribute("userDelete", true);
        return "users";
    }

}
