package com.skillstorm.lit.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/all")
	public List<GalleryImage> findAllGalleryImage() {
		return service.findAllGalleryImage();
	}
	
	@GetMapping("/{id}")
	public GalleryImage getGalleryImage(@PathVariable String id) {
		return service.findById(id);
	}
	
	@GetMapping("/listing-details/{id}")
	public List<GalleryImage> getGalleryImagesForDetailsListing(UUID id) {
		return service.findByListingDetailsId(id);
	}
	
	@GetMapping("/listing-details")
	public List<ListingDetails> getGalleryImagesListingDetails() {
		return service.findAllListingDetailsD();
	}
	
	@PostMapping
	public GalleryImage create(@RequestBody GalleryImage image) {
		return service.create(image);
	}
	
}
