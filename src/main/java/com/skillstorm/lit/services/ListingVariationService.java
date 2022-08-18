package com.skillstorm.lit.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.ListingVariationRepository;
import com.skillstorm.lit.models.ListingVariation;

@Service
public class ListingVariationService {
	
	@Autowired
	private ListingVariationRepository repository;
	
	public List<ListingVariation> findAll() {
		return repository.findAll();
	}
	
	public List<ListingVariation> findAllVariants(UUID listingId) {
		return repository.findByListing(listingId);
	}
	
	public ListingVariation findByListingAndId(UUID listingId, UUID variantId){
        return repository.findByListingAndId(listingId, variantId);
    }
	
	public ListingVariation save(ListingVariation variation) {
		return repository.save(variation);
	}
	
	public ListingVariation update(ListingVariation variation) {
		return repository.save(variation);
	}
	
	public void delete(UUID id) {
		 repository.deleteById(id);
	}
}
