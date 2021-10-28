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
import com.myapp.spring.model.Product;

@SpringBootTest
public class ProductRepositoryTeest {

	@Autowired
	private ProductRepository repository;

	private static File DATA_JSON = Paths.get("src","test","resources","products.json").toFile();
	
	@BeforeEach
	public void setUp() throws JsonEOFException,JsonMappingException,IOException{
		
	Product products[]= new ObjectMapper().readValue(DATA_JSON, Product[].class);
	
	Arrays.stream(products).forEach(repository::save);
	
	}
	
	@AfterEach
	public void cleanUp() {
		repository.deleteAll();
	}
	
	@Test
	
	@DisplayName("Test product not found for a non existing id")
	public void testProductNotFoundForNonExistingId() {
		
		
		Product product =repository.findById(100L).orElseGet(()-> new Product());
		Assertions.assertEquals(product.getProductId(),0,"Product with id 100 not exist");
		
	}
	
	@Test
	
	@DisplayName("Test product saved sucessfully")
	public void testProductSavedSucessfully() {
		
		Product product =new Product("mobile","Vivo","Vivo12pro",37545.0,30);
		product.setProductId(5L);
		
		Product savedProduct =repository.save(product);
		Assertions.assertNotNull(savedProduct," New Product should be saved");
		
		Assertions.assertNotNull(savedProduct.getProductId()," New Product should have id");
		
		Assertions.assertEquals(product.getProductName(), savedProduct.getProductName());
		
	}
	
	
	@Test
	@DisplayName("Test product updated sucessfully")
	public void testProductUpdatedSucessfully() {
		
		Product product =new Product("mobile","Oneplus","OnePlus9Pro",70000.00,4);
		product.setProductId(1L);
		
		Product updatedProduct =repository.save(product);
		
		Assertions.assertEquals(product.getPrice(), updatedProduct.getPrice());
		
	}
	
	

}
