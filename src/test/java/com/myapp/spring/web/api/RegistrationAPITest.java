package com.myapp.spring.web.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.LoginInfo;
import com.myapp.spring.repository.AccessRepository;
import com.myapp.spring.service.impl.RegistrationServiceImpl;


@SpringBootTest
@AutoConfigureMockMvc

public class RegistrationAPITest {

	@MockBean
	private AccessRepository repository;
	

	@MockBean
	private RegistrationServiceImpl service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Test Add New User")
	public void testAddNewUser() throws Exception {
		
		//Prepare Mock Product		
		LoginInfo newUser =new LoginInfo(1,"tanya@gmail.com","tanya@123","Tanya","Arora");
		LoginInfo mockUser =new LoginInfo(1,"tanya@gmail.com","tanya@123","Tanya","Arora");
	
		// Prepare Mock Service Method
		
		doReturn(mockUser).when(service).registerU(ArgumentMatchers.any());
		
		// Perform POST Request
		
		mockMvc.perform(post("/register")
		// Validate Status should be 200 ok and json response received
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(newUser)))
		
		//Validate Response body
		
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
		LoginInfo mockUser =new LoginInfo(1,"tanisha@gmail.com","tanisha@123","Tanisha","Tripathi");
		
		// Prepare Mock Service Method
		
		doReturn(mockUser).when(service).registerA(ArgumentMatchers.any());
		
		// Perform POST Request
		
		mockMvc.perform(post("/access")
		// Validate Status should be 200 ok and json response received
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(newUser)))
		
		//Validate Response body
		
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.email",is("tanisha@gmail.com")))
		.andExpect(jsonPath("$.password",is("tanisha@123")))
		.andExpect(jsonPath("$.firstName",is("Tanisha")))
		.andExpect(jsonPath("$.lastName",is("Tripathi")));
		
	}
	}