package com.skillstorm.lit.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.services.GalleryImageService;

@RestController
@RequestMapping("/gallery/v1")
public class GalleryImageV1Controller {
	
	@Autowired
	GalleryImageService service;
	
	@GetMapping
	public List<GalleryImage> getAllGalleryImages() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public GalleryImage getGalleryImage(@PathVariable UUID id) {
		return service.findById(id);
	}
	
	@GetMapping("/listing-details/{id}")
	public List<GalleryImage> getGalleryImagesForDetailsListing(UUID id) {
		return service.findByListingDetailsId(id);
	}
	
	@PostMapping
	public GalleryImage create(@RequestBody GalleryImage image) {
		return service.create(image);
	}
	
}
