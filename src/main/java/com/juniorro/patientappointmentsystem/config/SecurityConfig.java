package com.juniorro.patientappointmentsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	private static final String[] PUBLIC_ANT_MATCHERS = { "/assets/**", "/fonts/**", "/img/**", "/js/**", "/login",
			"/home", "/about", "/register", "/confirm", "/contact", "/recover/**", "/changePassword/**", "/reset/**",
			"/resetPassword" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(PUBLIC_ANT_MATCHERS).permitAll().anyRequest().authenticated().and()
				.exceptionHandling().accessDeniedPage("/accessDenied");

		http.csrf().disable().cors().disable().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/welcome")
				.loginProcessingUrl("/processLogin").failureUrl("/login?error");

		http.sessionManagement().maximumSessions(5).maxSessionsPreventsLogin(false).expiredUrl("/login")
				.sessionRegistry(sessionRegistry());

		/*
		 * .logout().logoutUrl("/logout").permitAll()
		 * .logoutSuccessUrl("/login?logout") .invalidateHttpSession(true)
		 * .deleteCookies("remember-me").and().rememberMe()
		 */
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

}
