package com.skillstorm.lit.services;

import java.util.List;
import java.util.UUID;

import com.skillstorm.lit.models.GalleryImage;

public interface GalleryImageService {
	
	List<GalleryImage> findAll();
	
	List<GalleryImage> findByListingDetailsId(UUID id);
	
	GalleryImage findById(UUID id);
	
	GalleryImage create(GalleryImage image);

}
