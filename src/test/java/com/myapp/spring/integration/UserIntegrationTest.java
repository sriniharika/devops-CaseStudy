package com.myapp.spring.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Access;
import com.myapp.spring.model.LoginInfo;
import com.myapp.spring.repository.AccessRepository;


@SpringBootTest
@AutoConfigureMockMvc

public class UserIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AccessRepository repository;
		
private static File DATA_JSON= Paths.get("src","test","resources","Access.json").toFile();
	
	@BeforeEach
	public void setup() throws JsonParseException, JsonMappingException, IOException {
	
	Access access[]=new ObjectMapper().readValue(DATA_JSON,Access[].class);
	
	Arrays.stream(access).forEach(repository::save);
	}

	@AfterEach
	public void cleanUp() {
		repository.deleteAll();
	}
	
	@Test
	@DisplayName("Test Add New User")
	public void testAddNewUser() throws Exception {
	
	
		LoginInfo newUser =new LoginInfo(1,"tanya@gmail.com","tanya@123","Tanya","Arora");
		
		
	
		mockMvc.perform(post("/register")
		
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(newUser)))
		
		
		
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.email",is("tanya@gmail.com")))
		.andExpect(jsonPath("$.password",is("tanya@123")))
		.andExpect(jsonPath("$.firstName",is("Tanya")))
		.andExpect(jsonPath("$.lastName",is("Arora")));
		
	}
	
	@Test
	@DisplayName("Test Add New Admin")
	public void testAddNewAdmin() throws Exception {
		
		//Prepare Mock Product
	
		LoginInfo newUser =new LoginInfo(1,"tanisha@gmail.com","tanisha@123","Tanisha","Tripathi");
		
		
		
		mockMvc.perform(post("/access")
		
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(newUser)))
		
	
		
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.email",is("tanisha@gmail.com")))
		.andExpect(jsonPath("$.password",is("tanisha@123")))
		.andExpect(jsonPath("$.firstName",is("Tanisha")))
		.andExpect(jsonPath("$.lastName",is("Tripathi")));
		
	}
	
	
	
	
	}

