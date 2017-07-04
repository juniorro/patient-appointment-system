package com.juniorro.patientappointmentsystem.controller;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juniorro.patientappointmentsystem.Service.AppointmentService;
import com.juniorro.patientappointmentsystem.Service.CustomerDetailsService;
import com.juniorro.patientappointmentsystem.Service.CustomerService;
import com.juniorro.patientappointmentsystem.Service.PasswordResetTokenService;
import com.juniorro.patientappointmentsystem.Service.PatientService;
import com.juniorro.patientappointmentsystem.Service.PhysicianService;
import com.juniorro.patientappointmentsystem.Service.RoleService;
import com.juniorro.patientappointmentsystem.Service.VerificationTokenService;
import com.juniorro.patientappointmentsystem.model.Customer;
import com.juniorro.patientappointmentsystem.model.security.PasswordResetToken;
import com.juniorro.patientappointmentsystem.model.security.UserRole;
import com.juniorro.patientappointmentsystem.model.security.VerificationToken;
import com.juniorro.patientappointmentsystem.registrationlistener.OnRegistrationCompleteEvent;

@Controller
public class HomeController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PhysicianService physicianService;

	@Autowired
	private CustomerDetailsService customerDetailsService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentService appointmentService;

	@RequestMapping(value = "/home")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/welcome")
	public String welcome(Model model) {
		long patientCounts = patientService.count();
		long physicianCounts = physicianService.count();
		long appointmentCounts = appointmentService.count();
		model.addAttribute("physicianCounts", physicianCounts);
		model.addAttribute("patientCounts", patientCounts);
		model.addAttribute("appointmentCounts", appointmentCounts);
		return "home";
	}

	@RequestMapping(value = "/about")
	public String about() {
		return "about";
	}

	@RequestMapping(value = "/contact")
	public String contact() {
		return "contact";
	}

	@RequestMapping(value = "/recover")
	public String recover() {
		return "resetpass";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value = "/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}

	@RequestMapping(value = "/newpassword")
	public String resetPassword() {
		return "newpass";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("customer", new Customer());
		return "register";
	}

	/* POST action registration method */

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(@Valid Customer customer, BindingResult result, Model model,
			final HttpServletRequest request, final RedirectAttributes redirect) {

		if (customerService.checkUserExist(customer.getUsername(), customer.getEmail())) {

			if (customerService.checkEmailExist(customer.getEmail())) {
				model.addAttribute("emailExist", true);
			}

			if (customerService.checkUsernameExist(customer.getUsername())) {
				model.addAttribute("usernameExist", true);
			}

			return "register";
		}

		if (result.hasErrors()) {
			return "register";
		}

		else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(customer, roleService.findByName("ROLE_ADMIN")));
			customer.setEnabled(false);
			final Customer newcustomer = customerService.saveCustomer(customer, userRoles);
			final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newcustomer, appUrl));
			redirect.addFlashAttribute("success", true);
			return "redirect:/login";
		}
	}

	/* Confirm registration method */

	@RequestMapping(value = "/confirm")
	public String confirm(final Model model, @RequestParam("token") final String token,
			final RedirectAttributes redirect) {

		final VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		if (verificationToken == null) {
			redirect.addFlashAttribute("wrongToken", true);
			return "redirect:/login";
		}
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			redirect.addFlashAttribute("tokenExpired", true);
			return "redirect:/login";
		}
		final Customer customer = verificationToken.getCustomer();
		customer.setEnabled(true);
		customerService.saveConfirmCustomer(customer);
		redirect.addFlashAttribute("tokenVerified", true);
		return "redirect:/login";
	}

	@RequestMapping(value = "/recover", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView resetPassword(final HttpServletRequest request,
			@RequestParam("email") final String customerEmail, final RedirectAttributes redirect) {
		final Customer customer = customerService.findByEmail(customerEmail);
		if (customer == null) {
			redirect.addFlashAttribute("wrongEmail", true);
			return new ModelAndView("redirect:/recover");
		}
		final String token = UUID.randomUUID().toString();
		passwordResetTokenService.createPasswordResetToken(customer, token);
		final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		final SimpleMailMessage email = passwordResetTokenService.constructResetTokenEmail(appUrl, token, customer);
		mailSender.send(email);
		redirect.addFlashAttribute("recover", true);
		return new ModelAndView("redirect:/login");
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView showChangePasswordPage(@RequestParam("id") final long id,
			@RequestParam("token") final String token, final RedirectAttributes redirect) {
		final PasswordResetToken passToken = passwordResetTokenService.findByToken(token);
		if (passToken == null) {
			redirect.addFlashAttribute("errorMessage", true);
			return new ModelAndView("redirect:/login");
		}
		final Customer customer = passToken.getCustomer();
		if (customer.getId() != id) {
			redirect.addFlashAttribute("errorMessage1", true);
			return new ModelAndView("redirect:/login");
		}
		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			redirect.addFlashAttribute("errorMessage2", true);
			return new ModelAndView("redirect:/login");
		}
		final Authentication auth = new UsernamePasswordAuthenticationToken(customer, null,
				customerDetailsService.loadUserByUsername(customer.getUsername()).getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

		return new ModelAndView("redirect:/newpassword");
	}

	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView savePassword(@RequestParam("password") final String password,
			@RequestParam("confirmPassword") final String passwordConfirmation, final RedirectAttributes redirect) {
		if (password.equals("") || passwordConfirmation.equals("")) {
			return new ModelAndView("newpass", "errorpass", true);
		}
		if (!password.equals(passwordConfirmation)) {
			return new ModelAndView("newpass", "errorpassword", true);
		}
		final Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		passwordResetTokenService.changePassword(customer, password);
		redirect.addFlashAttribute("resetmessage", true);
		return new ModelAndView("redirect:/login");
	}
}
