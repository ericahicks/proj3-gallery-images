package com.skillstorm.lit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.ListingDetailsRepository;
import com.skillstorm.lit.models.ListingDetails;

@Service
@Transactional
public class ListingDetailsServiceImpl implements ListingDetailsService {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ListingDetailsRepository repository;
	
	public List<ListingDetails> findAll() {
		return (List<ListingDetails>) repository.findAll();
	}
	
	public ListingDetails findById(UUID id) {
		Optional<ListingDetails> details = repository.findById(id);
		return details.isPresent() ? details.get() : null;
	}
	
	public ListingDetails save(ListingDetails detail) {
		return repository.save(detail);
	}

	@Override
	public ListingDetails update(ListingDetails detail, UUID id) {
		LOG.info("===================================================\n" + 
	              "===================================================\n" +
				  "updating ListingDetails entity with id = " + id +
				  "===================================================");
		detail.setId(id);
		if (repository.findById(id).isPresent()) {
			return repository.save(detail);
		} else {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public void delete(UUID id) {
		if (repository.findById(id).isPresent()) {
			repository.deleteById(id);
		} else {
			throw new EntityNotFoundException();
		}
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

}
