package com.skillstorm.lit.services;

import java.util.List;
import java.util.UUID;

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

public interface GalleryImageService {

	List<GalleryImage> findAll();
	
	List<GalleryImage> findByListingDetailsId(UUID id);
	
	GalleryImage findById(UUID id);
	
	GalleryImage create(GalleryImage image);

	GalleryImage update(GalleryImage galleryImage);

	void deleteById(UUID id);

	void deleteAllFromDetails(ListingDetails listingDetail);

	void deleteAll();
}
