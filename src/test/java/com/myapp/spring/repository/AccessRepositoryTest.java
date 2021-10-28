package com.myapp.spring.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Access;

@SpringBootTest
public class AccessRepositoryTest {

	@Autowired
	private AccessRepository repository;
	
	

	//private static File DATA_JSON = Paths.get("src","test","resources","users.json").toFile();
	
private static File DATA_JSON = Paths.get("src","test","resources","Access.json").toFile();
	
	@BeforeEach
	public void setUp() throws JsonEOFException,JsonMappingException,IOException{
		
	Access accesss[]= new ObjectMapper().readValue(DATA_JSON, Access[].class);
	
	Arrays.stream(accesss).forEach(repository::save);
	
	}
	
	@AfterEach
	public void cleanUp() {
		repository.deleteAll();
	}
	
	@Test
	
	@DisplayName("Test user not found for a non existing id")
	public void testUserNotFoundForNonExistingId() {
		
		
		Access user =repository.findById(100).orElseGet(()-> new Access());
		Assertions.assertEquals(user.getId(),0,"User with id 100 not exist");
		
	}
	
	@Test
	
	@DisplayName("Test user saved sucessfully")
	public void testUserSavedSucessfully() {
		
		Access user =new Access(5,"tanisha001@gmail.com","$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm","USER",true,"Tanisha","tripathi");
		//user.setId(5L);
		
		Access savedUser =repository.save(user);
		Assertions.assertNotNull(savedUser," New User should be saved");
		
		Assertions.assertNotNull(savedUser.getId()," New User should have id");
		
		
		Assertions.assertEquals(user.getFirstName(), savedUser.getFirstName());
		
		Assertions.assertEquals(user.getEmail(), savedUser.getEmail());
		
	}
	
	
	@Test
	@DisplayName("Test User updated sucessfully")
	public void testUserUpdatedSucessfully() {
		
		Access user =new Access(6,"tanya001@gmail.com","$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.","ADMIN",true,"tanya","arora");
		user.setRole("USER");
		
		Access updatedUser =repository.save(user);
		
		Assertions.assertEquals(user.getRole(), updatedUser.getRole(),"Role updated successfully");
		
		Assertions.assertEquals(user.getPassword(), updatedUser.getPassword(),"Password Updated successfully");
		
	}
	
	

}
