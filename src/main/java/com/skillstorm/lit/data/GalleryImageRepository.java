package com.skillstorm.lit.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

@Repository
public interface GalleryImageRepository extends CrudRepository<GalleryImage, UUID> {

	@Query("SELECT g FROM GalleryImage g WHERE g.listingDetail.id = ?1")
	List<GalleryImage> findByListingDetail(UUID id);

	List<GalleryImage> findByListingDetail(ListingDetails listingDetail);

	void deleteByListingDetail(ListingDetails listingDetail);

}

