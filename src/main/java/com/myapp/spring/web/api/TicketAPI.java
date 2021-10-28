package com.myapp.spring.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.exception.ResourceNotFoundException;
import com.myapp.spring.model.Ticket;
import com.myapp.spring.repository.TicketRepository;

//This is a class which exposes rest api's
@RestController
@RequestMapping("/admin/tickets")
public class TicketAPI {
	
	//Dependency Injection
	@Autowired
	private TicketRepository repository;
	
	//url : http:localhost:8888/admin/tickets/
	@GetMapping
	public ResponseEntity<List<Ticket>> findAll(){
	
	
		return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
	}
	
	
	
	@PostMapping
	public ResponseEntity<Ticket> saveNewTicket(@RequestBody Ticket ticket){
	
	
		return new ResponseEntity<>(repository.save(ticket),HttpStatus.CREATED);
	}
	

	
	//url : http:localhost:8888/admin/tickets?id=1
	@GetMapping("/{id}")
	public ResponseEntity<Ticket> findById(@PathVariable("id") String id ){
	

		return new ResponseEntity<>(repository.findById(id).get(),HttpStatus.OK);
	}

	
	
	//url : http:localhost:8888/admin/tickets/1
	@PutMapping("/{id}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable("id")  String id ,
			@RequestBody Ticket ticket){
	Ticket existingProduct =repository.findById(id).get();
	BeanUtils.copyProperties(ticket, existingProduct);
	
	
	return new ResponseEntity<>(repository.save(existingProduct),HttpStatus.CREATED);
	}

	
	
	//url : http:localhost:8888/admin/tickets/1
	@DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") String id)
      throws ResourceNotFoundException {
        Ticket product = repository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException
                   ("Product not found for this id :: " + id));
        repository.delete(product);
         Map<String, Boolean> response = new HashMap<>();
         response.put("deleted", Boolean.TRUE);
         return response;
    }
	
}

