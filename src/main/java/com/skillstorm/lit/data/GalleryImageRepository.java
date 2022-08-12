package com.skillstorm.lit.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

@Repository
public interface GalleryImageRepository extends CrudRepository<GalleryImage, String> {

	@Query("SELECT d FROM GalleryImage g JOIN g.listingDetail d")
	List<ListingDetails> findAllListingDetailsD();
	
	@Query("SELECT g FROM GalleryImage g JOIN g.listingDetail d")
	List<GalleryImage> findAllGalleryImage();
	
	@Query("SELECT g.listingDetail FROM GalleryImage g")
	List<ListingDetails> findAllListingDetails();
	

	List<GalleryImage> findByListingDetail(UUID id);
	
}
