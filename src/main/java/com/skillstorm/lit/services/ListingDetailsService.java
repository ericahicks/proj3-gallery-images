package com.skillstorm.lit.services;

import java.util.List;
import java.util.UUID;

import com.skillstorm.lit.models.ListingDetails;

public interface ListingDetailsService {
	
	List<ListingDetails> findAll();
	
	ListingDetails findById(UUID id);
	
	ListingDetails save(ListingDetails detail);
	
	ListingDetails update(ListingDetails detail, UUID id);
	
	void delete(UUID id);
}
