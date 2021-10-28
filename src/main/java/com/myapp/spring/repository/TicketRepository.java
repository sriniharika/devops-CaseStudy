package com.myapp.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.Ticket;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>{


	
	
    
}