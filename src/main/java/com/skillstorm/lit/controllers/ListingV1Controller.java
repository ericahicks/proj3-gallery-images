package com.skillstorm.lit.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.lit.models.Listing;
import com.skillstorm.lit.services.ListingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/listings/v1")
@Tag(name = "Listings")
public class ListingV1Controller {

	private static final Logger log = LoggerFactory.getLogger(ListingV1Controller.class);
	
	@Autowired
	private ListingService listingService;

	/**
	 * Locale is determined by the Accept-Language header have a valid value: such
	 * as "en-US"
	 * 
	 * 
	 * @param locale
	 * @return
	 */ 
	
	@GetMapping
	@Operation(summary = "Find all Listings (List)", description = "Return a List of all Listings")
	public ResponseEntity<List<Listing>> findAll(){
		log.info("Find all listings");
		List<Listing> listings = this.listingService.findAll();
		return ResponseEntity.ok(listings);
	}

    
  /**
	 * Locale is determined by the Accept-Language header have a valid value: such
	 * as "en-US"
	 * 
	 * @param locale
	 * 
	 * Represents listing request object (modeled in Listing.java in models package)
	 * 
	 * @param listing
	 * 
	 * Returns successfully, but only persists data to mySQL (doesn't persist properly to h2)
	 * 
	 * @return
	 */
	
	@PostMapping
	@Operation(summary = "Create and save a new Listing", description = "Return the newly created Listing passed in")
	public ResponseEntity<Listing> save(@RequestBody Listing listing) {
		return new ResponseEntity<Listing>(this.listingService.add(listing), HttpStatus.CREATED);
	}

	/**
     * Locale is determined by the Accept-Language header having a valid value: such
     * as "en-US". If the listing found with the price is within the locale, then we return it.
     *
     * @param id
     * @param locale
     * @return
     */
	@GetMapping(path = "/id/{id}")
    public ResponseEntity<Listing> findListingById(@PathVariable UUID id){
		log.debug("================================================");
        log.debug("GET REQUEST FOR SPECIFIC LISTING WITH ID: " + id);
        Listing listing = this.listingService.findById(id);
        log.debug("RETURNING: " + listing);
        return ResponseEntity.ok(listing);
    }

}