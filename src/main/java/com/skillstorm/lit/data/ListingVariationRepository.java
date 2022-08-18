package com.skillstorm.lit.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skillstorm.lit.models.ListingVariation;

@Repository
public interface ListingVariationRepository extends JpaRepository<ListingVariation, UUID> {
	
	@Query(value = "Select * from Listing_Variations Where LISTING_ID = ?1", nativeQuery = true)
	public List<ListingVariation> findByListing(UUID  listingId);
	
	@Query(value = "Select * from Listing_Variations Where LISTING_ID = ?1 And LISTING_VARIATION_ID = ?2", nativeQuery = true)
	public ListingVariation findByListingAndId (UUID listingId, UUID variantId);
}