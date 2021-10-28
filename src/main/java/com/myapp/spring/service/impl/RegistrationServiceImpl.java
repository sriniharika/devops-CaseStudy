package com.myapp.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.spring.model.Access;
import com.myapp.spring.model.LoginInfo;
import com.myapp.spring.repository.AccessRepository;

@Service
public class RegistrationServiceImpl {

	@Autowired
	private AccessRepository repository;
	
	
	
	public LoginInfo registerA(LoginInfo admin) {
		
		String email;
		email =admin.getEmail();
		String password;
		password= admin.getPassword();
		String role ="ROLE_ADMIN";
		boolean activity=true;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(password);
		Access access =new Access(admin.getId(),email,encodedPassword,role,activity,admin.getFirstName(),admin.getLastName());
		repository.save(access);
	    
		return admin;
		
	}
	
public LoginInfo registerU(LoginInfo user) {
		
		String email;
		email =user.getEmail();
		String password;
		password= user.getPassword();
		String role ="ROLE_USER";
		boolean activity=true;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(password);
		Access access =new Access(user.getId(),email,encodedPassword,role,activity,user.getFirstName(),user.getLastName());
		repository.save(access);
	    
		return user;
		
	}
	
}
