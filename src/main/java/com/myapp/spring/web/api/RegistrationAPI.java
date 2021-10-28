package com.myapp.spring.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.model.LoginInfo;
import com.myapp.spring.service.impl.RegistrationServiceImpl;

@RestController
public class RegistrationAPI {
	
	
	
	@Autowired
	private RegistrationServiceImpl service;
	
	@PostMapping("/access")
	public ResponseEntity<LoginInfo> adminRegistration(@RequestBody LoginInfo loginInfo){
		
		return new ResponseEntity<>(service.registerA(loginInfo),HttpStatus.CREATED);
	}
	@PostMapping("/register")
    
	public ResponseEntity<LoginInfo> userRegistration(@RequestBody LoginInfo loginInfo){
		
		return new ResponseEntity<>(service.registerU(loginInfo),HttpStatus.CREATED);
		}
}
