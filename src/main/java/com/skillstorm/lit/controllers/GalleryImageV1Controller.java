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

@RestController
@RequestMapping("/gallery/v1")
public class GalleryImageV1Controller {
	private static final Logger LOG = LoggerFactory.getLogger(GalleryImageV1Controller.class);
	
	@Autowired
	GalleryImageService service;
	
	@GetMapping
	public List<GalleryImage> getAllGalleryImages() {
		LOG.trace("\nGetting all Gallery Images");
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public GalleryImage getGalleryImage(@PathVariable UUID id) {
		return service.findById(id);
	}
	
	@GetMapping("/listing-details/{id}")
	public List<GalleryImage> getGalleryImagesForDetailsListing(@PathVariable UUID id) {
		return service.findByListingDetailsId(id);
	}
	
	@PostMapping
	public ResponseEntity<GalleryImage> create(@RequestBody GalleryImage image) {
		image = service.create(image);
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
