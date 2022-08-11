package com.skillstorm.lit.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.lit.models.GalleryImage;

@Repository
public interface GalleryImageRepository extends CrudRepository<GalleryImage, UUID> {

	List<GalleryImage> findByListingDetail();
	
}
