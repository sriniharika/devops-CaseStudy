package com.myapp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket")

public class Ticket {
	
	@Id
	@Column(name="ticket_id",nullable=false) 
	private String ticketId;
	
	
	@Column(name="ticket_status",nullable=false)
	private String ticketStatus;
	
	@Column(name="ticket_description",nullable=false)	
	private String ticketDescription;
	
	
	public Ticket() {
	}

	public Ticket(String ticketId,  String ticketDescription, String ticketStatus) {
		this.ticketId = ticketId;
		this.ticketStatus = ticketStatus;
		this.ticketDescription = ticketDescription;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	
	

}