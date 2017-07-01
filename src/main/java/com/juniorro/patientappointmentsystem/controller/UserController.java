package com.juniorro.patientappointmentsystem.controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.juniorro.patientappointmentsystem.Service.CustomerService;
import com.juniorro.patientappointmentsystem.Service.RoleService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.UserRole;
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editCustomer(Long id) {
		Customer customer = customerService.getOne(id);
		return new ModelAndView("editCustomer", "customer", customer);
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
			final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newcustomer, appUrl));
			redirect.addFlashAttribute("newUser", true);
			return "redirect:/users";
		}
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public ModelAndView updateCustomer(@Valid Customer customer, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("editCustomer");
		} else {
			customerService.saveConfirmCustomer(customer);
			return new ModelAndView("editCustomer", "updatedCustomer", true);
		}
	}

}
