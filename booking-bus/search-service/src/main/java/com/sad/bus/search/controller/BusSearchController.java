package com.sad.bus.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sad.bus.search.entity.BookingTicket;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class BusSearchController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/")
	public String home() {
		// This is useful for debugging
		// When having multiple instance of gallery service running at different ports.
		// We load balance among them, and display which instance received the request.
		return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
	}
  
	@RequestMapping("/{id}")
	public BookingTicket getGallery(@PathVariable final int id) {
		// create gallery object
		BookingTicket ticket = new BookingTicket();
		ticket.setId(id);
		
		// get list of available images 
		List<Object> images = restTemplate.getForObject("http://futa/futa-ticket/", List.class);
		ticket.setTickets(images);
	
		return ticket;
	}
	
	//goi toi vexere
	@RequestMapping("/vexere/{id}")
	public BookingTicket getTicketVexere(@PathVariable final int id) {
		// create gallery object
		BookingTicket ticket = new BookingTicket();
		ticket.setId(id);
		
		// get list of available images 
		List<Object> images = restTemplate.getForObject("http://vexere/vexere-ticket/", List.class);
		ticket.setTickets(images);
	
		return ticket;
	}
	
	//goi all ve cua futa va vexere
	@RequestMapping("/bus/{id}")
	public BookingTicket getTicket(@PathVariable final int id) {
		// create gallery object
		BookingTicket ticket = new BookingTicket();
		ticket.setId(id);
		
		// get list of available images 
		List<Object> ticketFuta = restTemplate.getForObject("http://futa/futa-ticket/", List.class);
		List<Object> ticketVexere = restTemplate.getForObject("http://vexere/vexere-ticket/", List.class);
		
		List<Object> ticketBus = new ArrayList<>();
		ticketBus.addAll(ticketVexere);
		ticketBus.addAll(ticketFuta);
		
		ticket.setTickets(ticketBus);
	
		return ticket;
	}
	
	// -------- Admin Area --------
	// This method should only be accessed by users with role of 'admin'
	// We'll add the logic of role based auth later
	@RequestMapping("/admin")
	public String homeAdmin() {
		return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
	}
}