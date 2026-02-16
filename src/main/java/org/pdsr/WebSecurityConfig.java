package org.pdsr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// http.requiresChannel(channel -> channel.anyRequest().requiresSecure());// ssl
		// code

		http.authorizeRequests(requests -> requests
				.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login").failureUrl("/login?error").permitAll())
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