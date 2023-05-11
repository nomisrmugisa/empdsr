package org.pdsr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

//        .requiresChannel().anyRequest().requiresSecure()//ssl
//        .and()//ssl
//        .csrf().disable()//ssl
        
                .authorizeRequests(requests -> requests
                        //.antMatchers( "/img/**", "/webjars/**").permitAll()
                        .antMatchers("/controls/**").hasRole("SETUP")//can only setup controls
                        .antMatchers("/registry/**").hasRole("ENTRY")//can only enter data
                        .antMatchers("/auditing").hasAnyRole("AUDIT", "TASKS")//can view audits
                        .antMatchers("/auditing/cstatus/**").hasRole("TASKS")//changing of status
                        .antMatchers("/auditing/**").hasRole("AUDIT")//can audit and change status
                        .anyRequest().authenticated()).formLogin(login -> login
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()).logout(logout -> logout.permitAll());

        http.csrf().disable();//for h2-console
        http.headers().frameOptions().disable();

    }

    //for h2 console
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "/img/**", "/webjars/**");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    // @Bean
    // @Override
    // public UserDetailsService userDetailsService() {
    //     UserDetails olinc = User.withDefaultPasswordEncoder().username("user").password("pass").roles("USER").build();

    //     InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    //     userDetailsManager.createUser(olinc);

    //     return userDetailsManager;
    // }

}