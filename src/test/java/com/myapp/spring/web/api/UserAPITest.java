package com.myapp.spring.web.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Access;
import com.myapp.spring.repository.AccessRepository;

@SpringBootTest
@AutoConfigureMockMvc 
public class UserAPITest {

	@MockBean
	private AccessRepository repository;
	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@DisplayName("Test Update User")
	@WithMockUser(username="himalaya001",password="password",roles="USER")
	public void testUpdateUsers() throws Exception {

		// Prepare Mock User
		Access newUser =new Access(5,"himalayaprakash001@gmail.com","$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm","ADMIN",true,"Himalaya","Prakash");
		newUser.setId(5);
		Access mockUser =new Access(5,"himalayaprakash001@gmail.com","$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm","USER",true,"Himalaya","Prakash");
		mockUser.setId(5);

		// Prepare Mock Service Method
		
		doReturn(Optional.of(mockUser)).when(repository).findById(5);
		doReturn(mockUser).when(repository).save(ArgumentMatchers.any());
		// Perform put Request

		mockMvc.perform(put("/updateUserDetails/{id}",5)
				// Validate Status should be 200 ok and json response recived
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(newUser)))

				// Validate Response body

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.email", is("himalayaprakash001@gmail.com"))).andExpect(jsonPath("$.firstName", is("Himalaya")))
				.andExpect(jsonPath("$.lastName", is("Prakash")));

	}

	
}
