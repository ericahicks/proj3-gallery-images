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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.lit.models.ListingVariation;
import com.skillstorm.lit.services.ListingVariationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/listings/v1/{UUID}/variants")
public class ListingVariationController {
	
	@Autowired
	private ListingVariationService service;
	
    //	Get a particular variant
	@GetMapping("/variant/{variantId}")
	public ResponseEntity<ListingVariation> findById(@PathVariable UUID listingId, @PathVariable UUID variantId){
        return new ResponseEntity<ListingVariation>(service.findByListingAndId(listingId, variantId),HttpStatus.OK);
    }
	
	//	Get all variants
	@GetMapping
	public ResponseEntity<List<ListingVariation>> findAllVariants(@PathVariable (value = "UUID") UUID listingId) {
		return new ResponseEntity<List<ListingVariation>>(service.findAllVariants(listingId), HttpStatus.OK);
	}
	
	// Create new variant
	@PostMapping
	public ResponseEntity<ListingVariation> save(@RequestBody ListingVariation variation) {
		return new ResponseEntity<>(service.save(variation), HttpStatus.CREATED);
	}
	
	// Update a variant
	@PutMapping
	public ResponseEntity<ListingVariation> update(@RequestBody ListingVariation variation) {
		return new ResponseEntity<>(service.update(variation), HttpStatus.NO_CONTENT);
	}
	
	// Delete particular variant
	@DeleteMapping
	public void delete(@PathVariable (value = "UUID") UUID id) {
		service.delete(id);
	}
	
	// Delete all variants
	@DeleteMapping("/deleteAll")
	public void deleteAll(@PathVariable (value = "UUID")UUID id) {
		List<ListingVariation> temp = service.findAllVariants(id);
		for (ListingVariation variations : temp) {
			service.delete(variations.getId());
		}
		
	}
	
	
}