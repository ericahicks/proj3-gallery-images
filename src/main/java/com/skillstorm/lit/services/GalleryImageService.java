package com.skillstorm.lit.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

public interface GalleryImageService {
	
	@Query("SELECT g.id, g.imageSrc, g.listingDetails FROM GalleryImage")
	List<GalleryImage> findAll();
	
	List<GalleryImage> findByListingDetailsId(UUID id);
	
	GalleryImage findById(String id);
	
	GalleryImage create(GalleryImage image);

}
