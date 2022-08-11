package com.skillstorm.lit.services;

import java.util.List;

import com.skillstorm.lit.models.GalleryImage;

public interface GalleryImageService {
	
	List<GalleryImage> findAll();
	
	List<GalleryImage> findByListingDetailsId();
	
	GalleryImage findByUUID();

}
