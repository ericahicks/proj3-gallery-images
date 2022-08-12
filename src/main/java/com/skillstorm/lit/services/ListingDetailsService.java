package com.skillstorm.lit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.ListingDetailsRepository;
import com.skillstorm.lit.models.ListingDetails;

@Service
public class ListingDetailsService {
	
	@Autowired
	ListingDetailsRepository repository;
	
	public List<ListingDetails> findAll() {
		return (List<ListingDetails>) repository.findAll();
	}
	
	public ListingDetails findById(UUID id) {
		Optional<ListingDetails> details = repository.findById(id);
		return details.isPresent() ? details.get() : null;
	}
	
	public ListingDetails findByIdString(String id) {
		Optional<ListingDetails> details = repository.findByIdString(id);
		return details.isPresent() ? details.get() : null;
	}
	
	public ListingDetails save(ListingDetails detail) {
		return repository.save(detail);
	}
	
}
