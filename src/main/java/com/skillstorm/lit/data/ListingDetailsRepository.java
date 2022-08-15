package com.skillstorm.lit.data;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.lit.models.ListingDetails;

@Repository
public interface ListingDetailsRepository extends CrudRepository <ListingDetails, UUID>{	
	
}
