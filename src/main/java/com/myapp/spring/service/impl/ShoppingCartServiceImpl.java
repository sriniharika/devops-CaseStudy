package com.myapp.spring.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.myapp.spring.exception.NotEnoughProductsInStockException;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;
import com.myapp.spring.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
    private final ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * If product is in the map just increment quantity by user's entered quantity.
     * If product is not in the map with, add it with user's entered quantity
     *
     * @param product
     */
    @Override
    public Map<Product, Integer> addProduct(Product product,Integer quantity) {
    
    	Map<Product, Integer> addedEntity=new HashMap<>();
         
        if (products.containsKey(product)) {
        	addedEntity.put(product,products.get(product) +quantity);
            products.replace(product, products.get(product) + quantity);
            
        } else {
            products.put(product, quantity);
            addedEntity.put(product,quantity);
        }
       
        return addedEntity;
    }

    /**
     * If product is in the map with quantity > entered quantity, just decrement quantity by entered quantity.
     * If product is in the map with quantity =entered quantity, remove it from map
     *
     * @param product
     */
    @Override
    public Map<Product, Integer> removeProduct(Product product,Integer quantity) {
    	 Map<Product, Integer> removedEntity=new HashMap<>();
         
        if (products.containsKey(product)) {
            if (products.get(product) > quantity) {
            	removedEntity.put(product,products.get(product) -quantity);
                products.replace(product, products.get(product) - quantity);
                
            }
            else if (products.get(product) == quantity) {
                products.remove(product);
                removedEntity.put(product,0);
            }
           
        }
       
        return removedEntity;
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public  Map<Product, Integer> checkout() throws NotEnoughProductsInStockException {
       Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            product =productRepository.findById(entry.getKey().getProductId()).get();
            if (product.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
        }
        productRepository.saveAll(products.keySet());
        productRepository.flush();
        products.clear();
        return getProductsInCart();
    }

    @Override
    public BigDecimal getTotal() {
    	BigDecimal sum=new BigDecimal("0.00");
    	for (Map.Entry<Product, Integer> entry : products.entrySet()) {
           sum=sum.add(BigDecimal.valueOf((entry.getKey().getPrice()*entry.getValue())));
        }
    	return sum;
       
    }

	@Override
	public Map<Product, Integer> checkoutremove(Product product) {
		
		return null;
	}
	
}