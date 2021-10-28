package com.myapp.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	
	@Autowired
	UserDetailsService userDetailsService;

	@Bean
    public PasswordEncoder getPasswordEncoder() {
        
        return new BCryptPasswordEncoder();
    }
     
	@Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
    	auth.authenticationProvider(authProvider());
        
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	http
        	.authorizeRequests()
        	.antMatchers("/admin/**").hasRole("ADMIN")
        	.antMatchers("/shoppingCart/**","/updateUserDetails/**").hasAnyRole("ADMIN","USER")
        	//.antMatchers("/updateUserDetails/**").hasAnyRole("ADMIN", "USER")
        	.antMatchers("/**").permitAll()
        	.and()
    		.formLogin().permitAll();

        		     
    }
}