package org.pdsr;

import org.pdsr.security.DHIS2AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DHIS2AuthenticationProvider dhis2AuthProvider;


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home", "/login", "/select-facility", "/webjars/**", "/css/**", "/js/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/select-facility", true)
				.failureUrl("/login?error")
				.permitAll()
				.and()
			.logout()
				.permitAll()
				.and()
			.rememberMe()
				.rememberMeParameter("remember-me")
				.key("pdsr-secret-key")
				.tokenValiditySeconds(2592000); // 30 days

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**", "/img/**", "/webjars/**", "/api/**");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Use local DB authentication
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
		// Use DHIS2 authentication
		auth.authenticationProvider(dhis2AuthProvider);
	}
}