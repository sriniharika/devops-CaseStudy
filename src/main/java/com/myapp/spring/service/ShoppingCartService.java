package com.myapp.spring.service;


import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myapp.spring.exception.NotEnoughProductsInStockException;
import com.myapp.spring.model.Product;

@Service
public interface ShoppingCartService {

	Map<Product, Integer> addProduct(Product product,Integer quantity);

	Map<Product, Integer> removeProduct(Product product,Integer quantity);

    Map<Product, Integer> getProductsInCart();

    Map<Product, Integer> checkout() throws NotEnoughProductsInStockException;

    Map<Product, Integer> checkoutremove(Product product);
    
    BigDecimal getTotal();
    
}
