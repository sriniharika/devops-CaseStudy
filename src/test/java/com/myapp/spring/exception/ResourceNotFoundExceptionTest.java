package com.myapp.spring.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResourceNotFoundExceptionTest {

	@Test
	public void checkDefaultException()
	{
		try
		{
			throw new ResourceNotFoundException("Requested resource not found");
		}
		catch(Exception e)
		{
			Assertions.assertEquals(e.getMessage(),new String("Requested resource not found"));
		}
	}
}