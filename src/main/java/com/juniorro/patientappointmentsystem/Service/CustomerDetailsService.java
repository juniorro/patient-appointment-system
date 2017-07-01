package com.juniorro.patientappointmentsystem.Service;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.juniorro.patientappointmentsystem.model.Customer;

@Service
public class CustomerDetailsService implements UserDetailsService {
	
	//private static final String ROLE_USER = "ROLE_USER";
	
	@Autowired
	private CustomerService customerService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Customer customer = customerService.findByUsername(username);
		if (customer == null){
			throw new UsernameNotFoundException ("No user was found with username " + username);
		}
		return new User(customer.getUsername(), customer.getPassword(), customer.isEnabled(), true, true, true, getAuthoriries(customer.getAuthorities().toString()));
	}

	private Collection<? extends GrantedAuthority> getAuthoriries(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

}
