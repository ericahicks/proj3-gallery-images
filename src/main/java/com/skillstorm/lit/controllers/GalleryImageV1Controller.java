package com.skillstorm.lit.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.GalleryImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/gallery/v1")
@Tag(name = "Gallery Image API", description = "A place to look up the urls for product images")
public class GalleryImageV1Controller {
	private static final Logger LOG = LoggerFactory.getLogger(GalleryImageV1Controller.class);
	
	@Autowired
	GalleryImageService service;
	
	@GetMapping
	@Operation(summary = "Find all gallery images for all products.", description = "An API endpoint to find all gallery images saved for all products. Does not support pagination at this time.")
	public List<GalleryImage> getAllGalleryImages() {
		LOG.trace("\nGetting all Gallery Images");
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Find gallery image by id.", description = "An API endpoint to find a gallery image given its uuid.")	
	public GalleryImage getGalleryImage(@PathVariable UUID id) {
		return service.findById(id);
	}
	
	@GetMapping("/listing-details/{id}")
	@Operation(summary = "Find all gallery images for a given product.", description = "An API endpoint to find all gallery images saved for a given product. There will be at most 5 images per product.")
	public List<GalleryImage> getGalleryImagesForDetailsListing(@PathVariable UUID id) {
		return service.findByListingDetailsId(id);
	}
	
	@PostMapping
	@Operation(summary = "Save gallery image.", description = "An API enpoint to create a gallery image mapping of the product id to the gallery image relative filepath.")
	public ResponseEntity<GalleryImage> create(@RequestBody GalleryImage image) {
		LOG.info("===========================================================\n");
		LOG.info("Creating a gallery image with " + image);
		try {
			image = service.create(image);
			System.out.println("    is image null?" + (image == null));
		} catch (Exception e) {
			LOG.info("======================================================\n");
			LOG.info("Create Gallery Image threw error " + e.getClass() + ":" + e.getMessage() + "\nCaused by: "+ e.getCause());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(image, image == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public GalleryImage update(@RequestBody GalleryImage galleryImage, @PathVariable UUID id) {
		galleryImage.setId(id);
		return service.update(galleryImage);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		service.deleteById(id);
	}
	
	@DeleteMapping
	public void deleteAllFromDetails(@RequestBody ListingDetails listingDetail) {
		service.deleteAllFromDetails(listingDetail);
	}
	
	@DeleteMapping("/listing-details/{id}")
	public void deleteByDetailId(@PathVariable UUID id) {
		ListingDetails detail = new ListingDetails();
		detail.setId(id);
		service.deleteAllFromDetails(detail);
	}
	
}
