package com.myapp.spring.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.exception.ResourceNotFoundException;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

//This is a class which exposes rest api's
@RestController
public class ProductAPI {
	
	//Dependency Injection
	@Autowired
	private ProductRepository repository;
	
	
	//checking
	//http://localhost:8080/products
	@GetMapping("/products")
	public ResponseEntity<List<Product>> findAll(){
	
	
	return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}

	//http://localhost:8080/admin/saveNewProducts
	@PostMapping("/admin/saveNewProduct")
	public ResponseEntity<Product> saveNewProuct(@RequestBody Product product){
	

	return new ResponseEntity<>(repository.save(product),HttpStatus.CREATED);
	}
	
	
	//http://localhost:8080/products/1
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") Long id ){
		Product product=null;
		if(repository.findById(id).isPresent()) {
			product=repository.findById(id).orElseGet(Product::new);
		}

	return new ResponseEntity<>(product,HttpStatus.OK);
	}

	
	//http://localhost:8080/admin/1
	@PutMapping("/admin/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id")  Long id ,
			@RequestBody Product product) throws ResourceNotFoundException{
		Product existingProduct =repository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                ("Product not found for this id :: " + id));
        
			
		existingProduct.setCategory(product.getCategory());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setProductName(product.getProductName());
		existingProduct.setQuantity(product.getQuantity());	

		return new ResponseEntity<>(repository.save(existingProduct),HttpStatus.OK);
	}

	
	//http://localhost:8080/admin/id
	@DeleteMapping("/admin/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable(value = "id") Long id)
      throws ResourceNotFoundException {
		
        Product product = repository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException
                   ("Product not found for this id :: " + id));
        repository.delete(product);
         Map<String, Boolean> response = new HashMap<>();
         response.put("deleted", Boolean.TRUE);
     
         
         return new ResponseEntity<>(response,HttpStatus.OK);
    }
	//http://localhost:8080/api/v1/products/findByPrice/40000	
	@GetMapping("/products/findByPrice/{price}")
	public ResponseEntity<List<Product>> 
	findProductsByPrice(@PathVariable Double price){

		return new ResponseEntity<>
		(repository.findByPriceGreaterThanEqual(price).get(),HttpStatus.OK);
		
	}

	
	//
	//http://localhost:8080/api/v1/products/findByName
    @GetMapping("/products/findByName")
    public ResponseEntity<List<Product>> findProductsByName
    (
            @RequestParam("productName") Optional<String> productName){
        
   
    	
    	 return new ResponseEntity<>
    	    (repository.findByProductNameLike(productName.orElse("")).get(), HttpStatus.OK);
    	    }
    
      @GetMapping("/products/findByCategory") 
	  public ResponseEntity<List<Product>>findByCategoryLike (
	  
	  @RequestParam("category") Optional<String> category){
	 
	  return new ResponseEntity<>(repository.findByCategoryLike(category.orElse("")).get(), HttpStatus.OK); }
}



	 








