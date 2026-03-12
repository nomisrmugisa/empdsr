package org.pdsr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.pdsr.security.DHIS2AuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DHIS2AuthenticationProvider dhis2AuthProvider;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// http.requiresChannel(channel -> channel.anyRequest().requiresSecure());// ssl
		// code

		http.authorizeRequests(requests -> requests
				.antMatchers("/login", "/select-facility", "/webjars/**", "/css/**", "/js/**").permitAll()
				.anyRequest().authenticated())
				.formLogin(login -> login
						.loginPage("/login")
						.defaultSuccessUrl("/select-facility", true)
						.failureUrl("/login?error")
						.permitAll())
				.logout(logout -> logout.permitAll());

		http.csrf().disable();// for h2-console
		http.headers().frameOptions().disable();
		return http.build();

	}

	// for h2 console
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> {
			web.ignoring().antMatchers("/h2-console/**", "/img/**", "/webjars/**", "/api/**");
		};
	}

	// @Bean
	// public AuthenticationManager customAuthenticationManager() throws Exception {
	// return authenticationManager();
	// }

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsServiceImpl userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService)
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
	}

	// @Bean
	// @Override
	// public UserDetailsService userDetailsService() {
	// UserDetails olinc =
	// User.withDefaultPasswordEncoder().username("user").password("pass").roles("USER").build();

	// InMemoryUserDetailsManager userDetailsManager = new
	// InMemoryUserDetailsManager();

	// userDetailsManager.createUser(olinc);

	// return userDetailsManager;
	// }

}