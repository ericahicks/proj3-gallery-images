package com.skillstorm.lit.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

public interface GalleryImageService {
	
	List<GalleryImage> findAll();
	
	List<GalleryImage> findAllGalleryImage();
	
	List<GalleryImage> findByListingDetailsId(UUID id);
	
	List<ListingDetails> findAllListingDetailsD();
	
	GalleryImage findById(String id);
	
	GalleryImage create(GalleryImage image);

}
