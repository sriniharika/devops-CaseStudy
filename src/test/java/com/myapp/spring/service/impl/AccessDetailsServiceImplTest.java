package com.myapp.spring.service.impl;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.myapp.spring.model.Access;
import com.myapp.spring.model.AccessDetails;
import com.myapp.spring.repository.AccessRepository;

@SpringBootTest
public class AccessDetailsServiceImplTest {
	@Autowired
	private AccessDetailsServiceImpl service;
	
	@Autowired
	private AccessRepository repository;

	@Test
	public void findUserByUsername()
	{
		//Access newUser =new Access(5,"himalayaprakash001@gmail.com","$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm","ADMIN",true,"Himalaya","Prakash");
		try
		{
			UserDetails userDetails=service.loadUserByUsername("hello@gmail.com");
			Assertions.assertNull(userDetails);
		}
		catch(Exception e)
		{
			Assertions.assertEquals(e.getMessage(),"No value present");
		}
	}
	
	@Test
	public void checkAccessDetails()
	{
		Access user =new Access(5,"tanisha001@gmail.com","$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm","USER",true,"Tanisha","tripathi");
		//user.setId(5L);
		AccessDetails access=new AccessDetails(user);
		Assertions.assertEquals(access.getPassword(),"$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm");
		
		Assertions.assertEquals(access.getUsername(),"tanisha001@gmail.com");
		
		ArrayList<String> accessRole=new ArrayList<String>();
		
		accessRole.add(user.getRole());
		
		Assertions.assertEquals(access.getAuthorities().toString(),accessRole.toString());
		
		Assertions.assertEquals(access.isAccountNonExpired(),true);
		
		Assertions.assertEquals(access.isAccountNonLocked(),true);
		
		Assertions.assertEquals(access.isCredentialsNonExpired(),true);
		
		Assertions.assertEquals(access.isEnabled(),true);
		
		
	}
}