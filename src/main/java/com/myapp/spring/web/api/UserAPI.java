package com.myapp.spring.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.exception.ResourceNotFoundException;
import com.myapp.spring.model.Access;
import com.myapp.spring.repository.AccessRepository;

@RestController
public class UserAPI {
	@Autowired
	private AccessRepository repository;
	
	@PutMapping("/updateUserDetails/{id}")
	public ResponseEntity<Access> updateUserDetails(@PathVariable("id") int id ,
			@RequestBody Access user) throws ResourceNotFoundException{

		Access existingUser =repository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                ("User not found for this id :: " + id));
        
		
		
		existingUser.setEmail(user.getEmail());
		existingUser.setActive(true);
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setPassword(user.getPassword());
		existingUser.setRole(user.getRole());
		existingUser.setId(user.getId());
		
		return new ResponseEntity<>(repository.save(existingUser),HttpStatus.OK);

	}

}
