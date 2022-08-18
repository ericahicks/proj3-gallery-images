package com.skillstorm.lit.controllers;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.GalleryImageService;
import com.skillstorm.lit.services.ListingDetailsService;

@RestController
@RequestMapping("/listing-details/v1")
public class ListingDetailsV1Controller {
	
	@Autowired
	ListingDetailsService service;

	@Autowired
	GalleryImageService imageService;
	
	@GetMapping
	public List<ListingDetails> getAllListingDetails() {
		return service.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ListingDetails> getById(@PathVariable UUID id) {
		ListingDetails details = service.findById(id);
		return new ResponseEntity<>(details, details == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ListingDetails create(@RequestBody ListingDetails detail) {
		return service.save(detail);
	}
	
	@PutMapping("/{id}")
	public ListingDetails update(@RequestBody ListingDetails detail, @PathVariable UUID id) {
		return service.update(detail, id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		// remove all images related to this listing details
		ListingDetails detail = new ListingDetails();
		detail.setId(id); // given id but need detail obj so creating one
		imageService.deleteAllFromDetails(detail);
		// remove the listing details itself
	}

}