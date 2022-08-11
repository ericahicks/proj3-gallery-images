package com.skillstorm.lit.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.ListingDetailsService;


@RestController
@RequestMapping("/listing-details/v1")
public class ListingDetailsController {
	
	@Autowired
	ListingDetailsService service;
	
	@GetMapping
	public List<ListingDetails> getAllListingDetails() {
		return service.findAll();
	}
	
	@GetMapping("{id}")
	public ListingDetails getById(@PathVariable String id) {
		return service.findById(id);
	}

}
