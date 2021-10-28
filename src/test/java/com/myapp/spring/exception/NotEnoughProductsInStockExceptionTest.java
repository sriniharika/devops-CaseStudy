package com.myapp.spring.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import org.springframework.boot.test.context.SpringBootTest;

import com.myapp.spring.model.Product;

@SpringBootTest
public class NotEnoughProductsInStockExceptionTest {
	
	@Test
	public void checkDefaultException()
	{
		try
		{
			throw new NotEnoughProductsInStockException();
		}
		catch(Exception e)
		{
			Assertions.assertEquals(e.getMessage(),new String("Not enough products in stock"));
		}
	}
	@Test
	public void checkParameterizedException()
	{
		Product newproduct =new Product("mobile","Redmi","Note9",20000.00,15);
		try
		{
			throw new NotEnoughProductsInStockException(newproduct);
		}
		catch(Exception e)
		{
			Assertions.assertEquals(e.getMessage(),String.format("Not enough %s products in stock. Only %d left", newproduct.getProductName(), newproduct.getQuantity()));
		}
	}

}