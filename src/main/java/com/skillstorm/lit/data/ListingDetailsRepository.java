package com.skillstorm.lit.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.skillstorm.lit.models.ListingDetails;

public interface ListingDetailsRepository extends CrudRepository <ListingDetails, UUID>{

	@Query(value = "SELECT * FROM listing_details "
			+ "WHERE hex(substring(listing_details_id, 1, 16)) = "
			+ "REPLACE('?1', '-', '') ", nativeQuery = true)
	Optional<ListingDetails> findByIdString(String id);
	
	
}
