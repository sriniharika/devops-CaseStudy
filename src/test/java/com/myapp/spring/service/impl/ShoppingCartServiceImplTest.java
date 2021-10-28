package com.myapp.spring.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myapp.spring.exception.NotEnoughProductsInStockException;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

@SpringBootTest
public class ShoppingCartServiceImplTest {

	@Autowired
	private ShoppingCartServiceImpl service;
	
	@Autowired
	private ProductRepository repository;
	
	@AfterEach
	public void cleanUp() {
		repository.deleteAll();
	}
	
	@Test
	public void testGetProductsInCart()
	{
		Product product1 = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product1.setProductId(1L);
		Product product2 = new Product("laptop", "Lenovo", "Ideapad", 120000.00, 40);
		product2.setProductId(2L);
		Map<Product,Integer> addedProducts=new HashMap<Product,Integer>();
		addedProducts.put(product1, 2);
		addedProducts.put(product2, 20);
		
		service.addProduct(product1, 2);
		service.addProduct(product2, 20);
		
		Map<Product,Integer> returnedProducts=new HashMap<Product,Integer>(service.getProductsInCart());
		
		Assertions.assertEquals(addedProducts, returnedProducts,"Cart verified successfully");
	}
	
	@Test
	public void testAddProductsInCart()
	{
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		Map<Product,Integer> addedProduct=new HashMap<Product,Integer>();
		addedProduct.put(product, 2);
		
		Map<Product,Integer> returnedProduct=new HashMap<Product,Integer>(service.addProduct(product,2));
		
		Assertions.assertEquals(addedProduct, returnedProduct,"Product added successfully");
		
		addedProduct.put(product, 5);
		
		
		Map<Product,Integer> returnedExistingProduct=new HashMap<Product,Integer>(service.addProduct(product,3));
		
		Assertions.assertEquals(addedProduct, returnedExistingProduct,"Product added successfully");
		
	}
	
	@Test
	public void testRemoveProductsFromCart()
	{
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		Map<Product,Integer> removedProduct=new HashMap<Product,Integer>();
		removedProduct.put(product, 1);
		service.addProduct(product, 3);
		Map<Product,Integer> returnedProduct=new HashMap<Product,Integer>(service.removeProduct(product,2));
		
		Assertions.assertEquals(removedProduct, returnedProduct,"Product removed successfully");
		
		removedProduct.put(product, 0);
		Map<Product,Integer> newReturnedProduct=new HashMap<Product,Integer>(service.removeProduct(product,1));
		
		Assertions.assertEquals(removedProduct, newReturnedProduct,"Product removed successfully");
		
		
	}
	
	@Test
	public void testGetTotal()
	{
		Product product1 = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product1.setProductId(1L);
		Product product2 = new Product("laptop", "Lenovo", "Ideapad", 120000.00, 40);
		product2.setProductId(2L);
		
		
		service.addProduct(product1, 2);
		service.addProduct(product2, 20);
		
		BigDecimal total=new BigDecimal("2520000.00");
		
		BigDecimal returnedTotal=service.getTotal();
		
		Assertions.assertEquals(total,returnedTotal,"Cart total verified successfully");
	}
	
	@Test
	public void testCheckout()
	{
		Product product1 = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product1.setProductId(10L);
		Product product2 = new Product("laptop", "Lenovo", "Ideapad", 120000.00, 40);
		product2.setProductId(12L);
		repository.save(product1);
		repository.save(product2);
		
		Map<Product,Integer> expectedProducts=new HashMap<Product,Integer>();
		
		service.addProduct(product1, 2);
		service.addProduct(product2, 20);
		
		Map<Product, Integer> returnedProducts;
		try {
			returnedProducts = new HashMap<Product,Integer>(service.checkout());
			Assertions.assertEquals(expectedProducts, returnedProducts,"Checkout done successfully");
		} catch (NotEnoughProductsInStockException e) {
						
			
		}
		
		
	}
	
	
}